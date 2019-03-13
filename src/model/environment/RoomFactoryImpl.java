package model.environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import model.character.BasicCharacter;
import model.character.CharacterFactory;
import model.character.EnemyCharacterType;
import model.room_event.RoomEvent;
import model.room_event.RoomEvents;

/**
 * Implementation of {@link model.environment.RoomFactory}.
 * 
 */
public class RoomFactoryImpl implements RoomFactory {

    private static final int MAX_CONTRAPTION_PER_ROOM = 3; 

    private final FloorDetails floorDetails;
    private final EnumMap<Content, Integer> remainingContent;
    private int roomIndex;

    /**
     * RoomFactoryImpl constructor.
     * 
     * @param floorChoice that describe the floor
     */
    public RoomFactoryImpl(final FloorDetails floorChoice) {
        this.floorDetails = floorChoice;
        this.remainingContent = new EnumMap<Content, Integer>(Content.class);
        this.remainingContent.put(Content.ENEMY, this.floorDetails.getNumberOfEnemies());
        this.remainingContent.put(Content.CONTRAPTION, this.floorDetails.getNumberOfContraptions());
        this.remainingContent.put(Content.TREASURE, this.floorDetails.getNumberOfTreasures());
        this.roomIndex = -1;
    }

    @Override
    public final Room createRoom() {
        if (this.roomIndex >= this.floorDetails.getNumberOfRooms()) {
            throw new IllegalStateException();
        }
        this.roomIndex++;
        return this.selectRoom();
    }

    private Room selectRoom() {
        if (this.floorDetails.isLastFloor() && this.isLastRoom()) {
            return this.createBossRoom();
        } else if (this.isLastRoom()) {
            return this.createStairsRoom();
        } else {
            return this.createBaseRoom();
        }
    }

    private boolean isLastRoom() {
        return this.roomIndex == (this.floorDetails.getNumberOfRooms() - 1);
    }

    private Room createBossRoom() {
        final BasicCharacter character = CharacterFactory.createEnemy(EnemyCharacterType.DARK_DESTRUCTOR, 1); 
        return new RoomImpl(new ArrayList<>(Arrays.asList(RoomEvents.getCombat())));
    }

    private Room createStairsRoom() {
        return new RoomImpl(new ArrayList<>(Arrays.asList(RoomEvents.getStairs())));
    }

    private Room createBaseRoom() {
        final List<RoomEvent> events = new ArrayList<>();
        events.addAll(IntStream.range(0, this.getRandomQuantityOfInteractableAction())
                               .boxed()
                               .map(i -> this.getRandomChoice())
                               .map(b -> b ? RoomEvents.getTreasureChest() : RoomEvents.getTreasureChest())
                               .collect(Collectors.toList()));
        final List<BasicCharacter> characters = IntStream.range(0, this.getRandomQuantityOfEnemies())
                                                         .boxed()
                                                         .map(i -> CharacterFactory.createEnemy(EnemyCharacterType.GOBLIN, this.getEnemiesMultiplier()))
                                                         .collect(Collectors.toList());
        // TODO: creare l'evento combat ed aggiungere i nemici; poi aggiungierlo alla lista
        return new RoomImpl(events);
    }

    private int getRamainingInteractableAction() {
        return this.remainingContent.get(Content.TREASURE) + this.remainingContent.get(Content.CONTRAPTION);
    }

    private int getRemainingBaseRooms() {
        return this.floorDetails.getNumberOfRooms() - (this.roomIndex + 1) - 1;
    }

    private int getRandomQuantityOfEnemies() {
        if (this.remainingContent.get(Content.ENEMY) >= this.getRemainingBaseRooms() || this.getRandomChoice()) {
            return 1;
        }
        return 0;
    }

    private int getRandomQuantityOfInteractableAction() {
        final int freePlacesAfterThisRoom = MAX_CONTRAPTION_PER_ROOM * (this.getRemainingBaseRooms() - 1);
        if (this.getRamainingInteractableAction() > freePlacesAfterThisRoom || this.getRandomChoice()) {
            final int minContraption = this.getRamainingInteractableAction()
                    - (freePlacesAfterThisRoom * MAX_CONTRAPTION_PER_ROOM);
            final int maxContraption = Integer.min(MAX_CONTRAPTION_PER_ROOM, this.getRamainingInteractableAction());
            return this.getRandomIntegerBetween(minContraption, maxContraption);
        }
        return 0;
    }
    
    private int getEnemiesMultiplier() {
        return this.floorDetails.getDifficult().getLevelOfDifficulty();
    }

    private boolean getRandomChoice() {
        return new Random().nextBoolean();
    }

    private int getRandomIntegerBetween(final int min, final int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}
