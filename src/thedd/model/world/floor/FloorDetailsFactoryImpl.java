package thedd.model.world.floor;

import java.util.EnumMap;
import java.util.Objects;

import thedd.model.world.enums.Difficulty;
import thedd.model.world.room.RoomFactoryImpl;

/**
 * This class is a factory of random details of a new floor based on value that the constructor recive.
 *
 */
public final class FloorDetailsFactoryImpl implements FloorDetailsFactory {

    private static final EnumMap<Difficulty, Double> MULTIPLIER = new EnumMap<Difficulty, Double>(Difficulty.class);
    private static final double EASY_MODE_MULTIPLIER = 0.25;
    private static final double NORMAL_MODE_MULTIPLIER = 0.5;
    private static final double HARD_MODE_MULTIPLIER = 1;

    private static final String ERROR_NONVALIDROOMS = "Number of rooms is not valid";

    static {
        MULTIPLIER.put(Difficulty.EASY, EASY_MODE_MULTIPLIER);
        MULTIPLIER.put(Difficulty.NORMAL, NORMAL_MODE_MULTIPLIER);
        MULTIPLIER.put(Difficulty.HARD, HARD_MODE_MULTIPLIER);
    }

    /**
     * FloorDetailsFactory constructor.
     */
    public FloorDetailsFactoryImpl() { }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloorDetails createFloorDetails(final Difficulty difficulty, final int numberOfRooms, final boolean lastFloor) {
        Objects.requireNonNull(difficulty);
        if (numberOfRooms <= 0) {
            throw new IllegalArgumentException(ERROR_NONVALIDROOMS);
        }
        final int numberOfContraptions = this.getRandomNumberOfContraptions(numberOfRooms - 1, difficulty, 0);
        final int numberOfTreasures = this.getRandomNumberOfTreasure(numberOfRooms - 1, difficulty, numberOfContraptions);
        return new FloorDetails.Builder().difficulty(difficulty)
                                         .rooms(numberOfRooms)
                                         .boss(lastFloor)
                                         .enemies(this.getRandomNumberOfEnemies(numberOfRooms - 1, difficulty))
                                         .contraptions(numberOfContraptions)
                                         .treasures(numberOfTreasures)
                                         .build();
    }

    private int getRandomNumberOfEnemies(final int effectiveNumberOfRooms, final Difficulty difficulty) {
        final int baseNumber = (int) Math.round(effectiveNumberOfRooms * MULTIPLIER.get(difficulty));
        final int maxRandomRoundIntegerNumber = (int) Math.round(effectiveNumberOfRooms * MULTIPLIER.get(Difficulty.EASY));
        final int roundNumber =  RandomUtils.getRandomInteger(maxRandomRoundIntegerNumber);
        int result = baseNumber + roundNumber;
        result = result < RoomFactoryImpl.MIN_ENEMIES_PER_ROOM 
                ? RoomFactoryImpl.MIN_ENEMIES_PER_ROOM 
                : result;
        result = result > effectiveNumberOfRooms * RoomFactoryImpl.MAX_ENEMIES_PER_ROOM
                ? effectiveNumberOfRooms * RoomFactoryImpl.MAX_ENEMIES_PER_ROOM
                : result;
        return result;
    }

    private int getRandomNumberOfTreasure(final int effectiveNumberOfRooms, final Difficulty difficulty, final int interagibleSetted) {
        final int baseValue = (int) Math.round(effectiveNumberOfRooms * MULTIPLIER.get(Difficulty.NORMAL));
        final int baseNumber = RandomUtils.getRandomIntegerGaussianNumber(baseValue, baseValue);
        int result = (int) Math.round(baseNumber * MULTIPLIER.get(difficulty));
        result = result < RoomFactoryImpl.MIN_INTERACTABLE_ACTION_PER_ROOM 
                ? RoomFactoryImpl.MIN_INTERACTABLE_ACTION_PER_ROOM 
                : result;
        result = result > (effectiveNumberOfRooms * RoomFactoryImpl.MAX_INTERACTABLE_ACTIONS_PER_ROOM) - interagibleSetted
                ? (effectiveNumberOfRooms * RoomFactoryImpl.MAX_INTERACTABLE_ACTIONS_PER_ROOM) - interagibleSetted
                : result;
        return result;
    }

    private int getRandomNumberOfContraptions(final int effectiveNumberOfRooms, final Difficulty difficulty, final int interagibleSetted) {
        return this.getRandomNumberOfTreasure(effectiveNumberOfRooms, difficulty, interagibleSetted);
    }

}
