package model.environment.room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import model.character.BasicCharacter;
import model.character.CharacterFactory;
import model.character.EnemyCharacterType;
import model.combat.common.HostileEncounter;
import model.environment.enums.RoomContent;
import model.environment.environment.EnvironmentImpl;
import model.environment.floor.FloorDetails;
import model.environment.floor.RandomUtils;
import model.room_event.CombatEvent;
import model.room_event.RoomEvent;
import model.room_event.RoomEvents;

/**
 * Implementation of {@link model.environment.RoomFactory}.
 * 
 */
public class RoomFactoryImpl implements RoomFactory {

    private static final int MAX_INTERACTABLE_ACTIONS_PER_ROOM = 3;
    private static final int PROBABILITY_OF_CONTRAPTION = 30;
    private static final int PROBABILITY_OF_TREASURE = 70;
    private static final int NONE_ROOM_INDEX = -1;

    private final EnumMap<RoomContent, Integer> remainingContent;
    private final FloorDetails floorDetails;
    private final int numberOfRooms;
    private final boolean isLastFloor;
    private int roomIndex;

    /**
     * RoomFactoryImpl constructor.
     * 
     * @param floorDetails  that describe the floor
     * @param numberOfRooms is the number of rooms
     * @param isLastFloor   is true if the current floor is the last
     * @throws NullPointerException if floorDetails is null
     * @throws IllegalArgumentException if the number of the rooms is less than the minimum
     */
    public RoomFactoryImpl(final FloorDetails floorDetails, final int numberOfRooms, final boolean isLastFloor) {
        Objects.requireNonNull(floorDetails);
        if (numberOfRooms <= EnvironmentImpl.MIN_NUMBER_OF_ROOMS) {
            throw new IllegalArgumentException();
        }
        this.floorDetails = floorDetails;
        this.numberOfRooms = numberOfRooms;
        this.isLastFloor = isLastFloor;
        this.remainingContent = new EnumMap<RoomContent, Integer>(RoomContent.class);
        this.setRemainingContent();
        this.roomIndex = NONE_ROOM_INDEX;
    }

    private void setRemainingContent() {
        this.remainingContent.put(RoomContent.ENEMY, this.floorDetails.getNumberOfEnemies());
        this.remainingContent.put(RoomContent.CONTRAPTION, this.floorDetails.getNumberOfContraptions());
        this.remainingContent.put(RoomContent.TREASURE, this.floorDetails.getNumberOfTreasures());
    }

    @Override
    public final Room createRoom() {
        if (this.roomIndex >= (this.numberOfRooms - 1)) {
            throw new IllegalStateException();
        }
        return this.changeRoom();
    }

    private Room changeRoom() {
        this.roomIndex++;
        if (this.isLastFloor && this.isLastRoom()) {
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
        // TODO: check if the change is up to date
        final CombatEvent combatEvent = RoomEvents.getCombat();
        final HostileEncounter hostileEncounter = combatEvent.getHostileEncounter();
        hostileEncounter.addNPC(CharacterFactory.createFinalBoss(this.getEnemiesMultiplier()));
        return new RoomImpl(Arrays.asList(combatEvent));
    }

    private Room createStairsRoom() {
        return new RoomImpl(Arrays.asList(RoomEvents.getStairs()));
    }

    private Room createBaseRoom() {
        final List<RoomEvent> events = new ArrayList<>();
        events.addAll(IntStream.range(0, this.getRandomQuantityOfInteractableAction())
                               .boxed()
                               .map(i -> RandomUtils.getRandomWeightedBoolean(PROBABILITY_OF_TREASURE, PROBABILITY_OF_CONTRAPTION))
                               .map(b -> b ? RoomEvents.getTreasureChest() : RoomEvents.getTreasureChest())
                               .collect(Collectors.toList()));
        final List<BasicCharacter> characters = IntStream.range(0, this.getRandomQuantityOfEnemies())
                                                         .boxed()
                                                         .map(i -> CharacterFactory.createEnemy(EnemyCharacterType.getRandom(), this.getEnemiesMultiplier()))
                                                         .collect(Collectors.toList());
        final CombatEvent combatEvent = RoomEvents.getCombat();
        final HostileEncounter hostileEncounter = combatEvent.getHostileEncounter();
        characters.forEach(c -> hostileEncounter.addNPC(c));
        events.add(combatEvent);
        return new RoomImpl(events);
    }

    private int getRamainingInteractableActionAvailable() {
        return this.remainingContent.get(RoomContent.TREASURE) + this.remainingContent.get(RoomContent.CONTRAPTION);
    }

    private int getRemainingBaseRooms() {
        return this.numberOfRooms - (this.roomIndex + 1) - 1;
    }

    private int getRandomQuantityOfEnemies() {
        if (this.remainingContent.get(RoomContent.ENEMY) >= this.getRemainingBaseRooms() || RandomUtils.getRandomBoolean()) {
            return 1;
        }
        return 0;
    }

    private int getRandomQuantityOfInteractableAction() {
        final int freePlacesAfterThisRoom = MAX_INTERACTABLE_ACTIONS_PER_ROOM * (this.getRemainingBaseRooms() - 1);
        if (this.getRamainingInteractableActionAvailable() > freePlacesAfterThisRoom || RandomUtils.getRandomBoolean()) {
            final int minContraption = this.getRamainingInteractableActionAvailable()
                    - (freePlacesAfterThisRoom * MAX_INTERACTABLE_ACTIONS_PER_ROOM);
            final int maxContraption = Integer.min(MAX_INTERACTABLE_ACTIONS_PER_ROOM, this.getRamainingInteractableActionAvailable());
            return RandomUtils.getRandomIntegerBetweenIntegers(minContraption, maxContraption);
        }
        return 0;
    }

    private int getEnemiesMultiplier() {
        return this.floorDetails.getDifficult().getLevelOfDifficulty();
    }

}
