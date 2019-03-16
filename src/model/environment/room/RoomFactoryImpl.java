package model.environment.room;

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
import model.combat.common.HostileEncounter;
import model.room_event.CombatEvent;
import model.room_event.RoomEvent;
import model.room_event.RoomEvents;

/**
 * Implementation of {@link model.environment.RoomFactory}.
 * 
 */
public class RoomFactoryImpl implements RoomFactory {

    private static final int MAX_CONTRAPTION_PER_ROOM = 3; 
    private static final int NONE_ROOM = -1;

    private final EnumMap<RoomContent, Integer> remainingContent;
    private final Random random;
    private final FloorDetails floorDetails;
    private final int numberOfRooms;
    private int roomIndex;

    /**
     * RoomFactoryImpl constructor.
     * 
     * @param floorDetails that describe the floor
     * @param numberOfRooms is the number of rooms
     */
    public RoomFactoryImpl(final FloorDetails floorDetails, final int numberOfRooms) {
        this.floorDetails = floorDetails;
        this.numberOfRooms = numberOfRooms;
        this.random = new Random(System.currentTimeMillis());
        this.remainingContent = new EnumMap<RoomContent, Integer>(RoomContent.class);
        this.remainingContent.put(RoomContent.ENEMY, this.floorDetails.getNumberOfEnemies());
        this.remainingContent.put(RoomContent.CONTRAPTION, this.floorDetails.getNumberOfContraptions());
        this.remainingContent.put(RoomContent.TREASURE, this.floorDetails.getNumberOfTreasures());
        this.roomIndex = NONE_ROOM;
    }

    @Override
    public final Room createRoom() {
        if (this.roomIndex >= this.numberOfRooms) {
            throw new IllegalStateException();
        }
        this.roomIndex++;
        return this.changeRoom();
    }

    private Room changeRoom() {
        if (this.floorDetails.isLastFloor() && this.isLastRoom()) {
            return this.createBossRoom();
        } else if (this.isLastRoom()) {
            return this.createStairsRoom();
        } else {
            return this.createBaseRoom();
        }
    }

    private boolean isLastRoom() {
        return this.roomIndex == (this.numberOfRooms - 1);
    }

    private Room createBossRoom() {
        final CombatEvent combatEvent = RoomEvents.getCombat();
        final HostileEncounter hostileEncounter = combatEvent.getHostileEncounter();
        hostileEncounter.addNPC(CharacterFactory.createFinalBoss(this.getEnemiesMultiplier()));
        return new RoomImpl(new ArrayList<>(Arrays.asList(combatEvent)));
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
                                                         .map(i -> CharacterFactory.createEnemy(EnemyCharacterType.getRandom(), this.getEnemiesMultiplier()))
                                                         .collect(Collectors.toList());
        final CombatEvent combatEvent = RoomEvents.getCombat();
        final HostileEncounter hostileEncounter = combatEvent.getHostileEncounter();
        hostileEncounter.addAll(characters);
        events.add(combatEvent);
        return new RoomImpl(events);
    }

    private int getRamainingInteractableAction() {
        return this.remainingContent.get(RoomContent.TREASURE) + this.remainingContent.get(RoomContent.CONTRAPTION);
    }

    private int getRemainingBaseRooms() {
        return this.numberOfRooms - (this.roomIndex + 1) - 1;
    }

    private int getRandomQuantityOfEnemies() {
        if (this.remainingContent.get(RoomContent.ENEMY) >= this.getRemainingBaseRooms() || this.getRandomChoice()) {
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
            return this.getRandomIntegerBetweenIntegers(minContraption, maxContraption);
        }
        return 0;
    }

    private int getEnemiesMultiplier() {
        return this.floorDetails.getDifficult().getLevelOfDifficulty();
    }

    private boolean getRandomChoice() {
        return this.random.nextBoolean();
    }

    private int getRandomIntegerBetweenIntegers(final int min, final int max) {
        return this.random.nextInt(max - min + 1) + min;
    }
}
