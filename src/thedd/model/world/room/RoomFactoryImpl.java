package thedd.model.world.room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import thedd.model.character.BasicCharacter;
import thedd.model.character.CharacterFactory;
import thedd.model.character.types.EnemyCharacterType;
import model.combat.logic.DefaultCombatLogic;
import model.room_event.CombatEvent;
import model.room_event.RoomEvent;
import model.room_event.RoomEvents;
import thedd.model.world.Difficulty;
import thedd.model.world.floor.FloorDetails;
import thedd.model.world.floor.FloorImpl;
import thedd.utils.RandomUtils;

/**
 * Implementation of {@link thedd.model.world.RoomFactory}.
 * 
 */
public class RoomFactoryImpl implements RoomFactory {

    /**
     * Mininum interactable actions in a room.
     */
    public static final int MIN_INTERACTABLE_ACTION_PER_ROOM = 0;

    /**
     * Maximum interactable actions in a room.
     */
    public static final int MAX_INTERACTABLE_ACTIONS_PER_ROOM = 3;

    /**
     * Maximim enemies in a room.
     */
    public static final int MAX_ENEMIES_PER_ROOM = 1;

    /**
     * Minimum enemies in a room.
     */
    public static final int MIN_ENEMIES_PER_ROOM = 0;

    private static final String ERROR_NOMOREROOMS = "Rooms are over";

    private final EnumMap<RoomContent, Integer> remainingContent;
    private final FloorDetails floorDetails;
    private int roomIndex;

    /**
     * RoomFactoryImpl constructor.
     * 
     * @param floorDetails  that describe the floor
     * @throws NullPointerException if floorDetails is null
     */
    public RoomFactoryImpl(final FloorDetails floorDetails) {
        Objects.requireNonNull(floorDetails);
        this.floorDetails = floorDetails;
        this.remainingContent = new EnumMap<RoomContent, Integer>(RoomContent.class);
        this.setRemainingContent();
        this.roomIndex = FloorImpl.NONE_ROOMS;
    }

    private void setRemainingContent() {
        this.remainingContent.put(RoomContent.ENEMY, this.floorDetails.getNumberOfEnemies());
        this.remainingContent.put(RoomContent.CONTRAPTION, this.floorDetails.getNumberOfContraptions());
        this.remainingContent.put(RoomContent.TREASURE, this.floorDetails.getNumberOfTreasures());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Room createRoom() {
        if (this.roomIndex >= (this.floorDetails.getNumberOfRooms() - 1)) {
            throw new IllegalStateException(ERROR_NOMOREROOMS);
        }
        return this.changeRoom();
    }

    private Room changeRoom() {
        this.roomIndex++;
        if (this.floorDetails.isBossFloor() && this.isLastRoom()) {
            return this.createBossRoom();
        } else if (this.isLastRoom()) {
            return this.createStairsRoom();
        } 
        return this.createBaseRoom();
    }

    private boolean isLastRoom() {
        return this.roomIndex == (this.floorDetails.getNumberOfRooms() - 1);
    }

    private Room createBossRoom() {
        final BasicCharacter boss = CharacterFactory.createFinalBoss(this.getEnemiesMultiplier());
        final CombatEvent event = RoomEvents.getCombat();
        event.getHostileEncounter().addNPC(boss);
        event.getHostileEncounter().setCombatLogic(new DefaultCombatLogic());
        return new RoomImpl(Arrays.asList(event));
    }

    private Room createStairsRoom() {
        return new RoomImpl(Arrays.asList(RoomEvents.getStairs()));
    }

    private Room createBaseRoom() {
        final List<RoomEvent> events = new ArrayList<>();
        final CombatEvent combatEvent = RoomEvents.getCombat();
        IntStream.range(0, this.getQuantityOfEnemies())
                 .boxed()
                 .map(i -> CharacterFactory.createEnemy(EnemyCharacterType.getRandom(), this.getEnemiesMultiplier()))
                 .forEach(c -> combatEvent.getHostileEncounter().addNPC(c));
        combatEvent.getHostileEncounter().setCombatLogic(new DefaultCombatLogic());
        events.add(combatEvent);
        events.addAll(IntStream.range(0, getQuantityOfInteractableAction().get(RoomContent.CONTRAPTION))
                               .boxed()
                               .map(b -> RoomEvents.getContraption())
                               .collect(Collectors.toList()));
        events.addAll(IntStream.range(0, getQuantityOfInteractableAction().get(RoomContent.TREASURE))
                               .boxed()
                               .map(b -> RoomEvents.getTreasureChest())
                               .collect(Collectors.toList()));
        return new RoomImpl(events);
    }


    private int getQuantityOfEnemies() {
        if (this.remainingContent.get(RoomContent.ENEMY) >= this.getRemainingBaseRoomsToSet()
                || RandomUtils.getRandomBoolean()) {
            return MAX_ENEMIES_PER_ROOM;
        }
        return MIN_ENEMIES_PER_ROOM;
    }

    private EnumMap<RoomContent, Integer> getQuantityOfInteractableAction() {
        final EnumMap<RoomContent, Integer> content = new EnumMap<RoomContent, Integer>(RoomContent.class);
        content.put(RoomContent.CONTRAPTION, 0);
        content.put(RoomContent.TREASURE, 0);
        final int maxInteractableSettableAfterNextRoom = MAX_INTERACTABLE_ACTIONS_PER_ROOM * (this.getRemainingBaseRoomsToSet() - 1);
        /* If the number of remaining interactable is high maybe it has to set in this room many of them to be sure to set all*/
        if (this.getRamainingInteractableToSet() > maxInteractableSettableAfterNextRoom || RandomUtils.getRandomBoolean()) {
            int minInteractable = 0;
            if (this.getRamainingInteractableToSet() > maxInteractableSettableAfterNextRoom) {
                minInteractable = this.getRamainingInteractableToSet() - maxInteractableSettableAfterNextRoom;
            }
            final int maxInteractable = Integer.min(MAX_INTERACTABLE_ACTIONS_PER_ROOM, this.getRamainingInteractableToSet());
            final int numberOfInteractable =  RandomUtils.getRandomIntegerBetweenIntegers(minInteractable, maxInteractable);
            IntStream.range(0, numberOfInteractable)
                     .boxed()
                     .map(i -> getAvailableRandomInteractableAction().get())
                     .forEach(roomContent -> content.compute(roomContent, (k, v) -> v + 1));
        }
        this.remainingContent.compute(RoomContent.CONTRAPTION, (k, v) -> v - content.get(RoomContent.CONTRAPTION));
        this.remainingContent.compute(RoomContent.TREASURE, (k, v) -> v - content.get(RoomContent.TREASURE));
        return content;
    }

    private Optional<RoomContent> getAvailableRandomInteractableAction() {
        Optional<RoomContent> contentType = Optional.empty();
        if (this.remainingContent.get(RoomContent.CONTRAPTION) > 0 && RandomUtils.getRandomBoolean()) {
            contentType = Optional.of(RoomContent.CONTRAPTION);
        } else if (this.remainingContent.get(RoomContent.TREASURE) > 0 && !contentType.isPresent()) {
            contentType = Optional.of(RoomContent.TREASURE);
        }
        return contentType;
    }

    private Difficulty getEnemiesMultiplier() {
        return this.floorDetails.getDifficult();
    }

    private int getRamainingInteractableToSet() {
        return this.remainingContent.get(RoomContent.TREASURE) + this.remainingContent.get(RoomContent.CONTRAPTION);
    }

    private int getRemainingBaseRoomsToSet() {
        return this.floorDetails.getNumberOfRooms() - (this.roomIndex + 1) - 1;
    }

}
