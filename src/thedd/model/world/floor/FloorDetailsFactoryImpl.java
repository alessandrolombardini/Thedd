package thedd.model.world.floor;

import java.util.Objects;
import thedd.model.world.Difficulty;
import thedd.model.world.room.RoomFactoryImpl;
import thedd.utils.RandomUtils;

/**
 * Implementation of {@link thedd.model.world.floor.FloorDetailsFactory}.
 */
public final class FloorDetailsFactoryImpl implements FloorDetailsFactory {

    private static final String ERROR_NONVALIDROOMS = "Number of rooms is not valid";
    private static final int MIN_NUMBER_OF_ROOMS = 0;

    /**
     * FloorDetailsFactory constructor.
     */
    public FloorDetailsFactoryImpl() { }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloorDetails createFloorDetails(final Difficulty difficulty, final int numberOfRooms,
                                           final boolean lastFloor) {
        Objects.requireNonNull(difficulty);
        if (numberOfRooms <= MIN_NUMBER_OF_ROOMS) {
            throw new IllegalArgumentException(ERROR_NONVALIDROOMS);
        }
        final int numberOfIteragibleSetted = 0;
        final int numberOfContraptions = this.getRandomNumberOfContraptions(numberOfRooms - 1, difficulty,
                                                                            numberOfIteragibleSetted);
        final int numberOfTreasures = this.getRandomNumberOfTreasure(numberOfRooms - 1, difficulty,
                                                                     numberOfContraptions);
        return new FloorDetails.Builder().difficulty(difficulty)
                                         .rooms(numberOfRooms).boss(lastFloor)
                                         .enemies(this.getRandomNumberOfEnemies(numberOfRooms - 1, difficulty))
                                         .contraptions(numberOfContraptions)
                                         .treasures(numberOfTreasures).build();
    }

    private int getRandomNumberOfEnemies(final int effectiveNumberOfRooms, final Difficulty difficulty) {
        final int baseNumber = (int) Math.round(effectiveNumberOfRooms * difficulty.getMultiplier());
        final int maxRandRoundIntNum = (int) Math.round(effectiveNumberOfRooms * Difficulty.EASY.getMultiplier());
        final int roundNumber = RandomUtils.getRandomInteger(maxRandRoundIntNum);
        int result = baseNumber + roundNumber;
        result = result < RoomFactoryImpl.MIN_ENEMIES_PER_ROOM 
                        ? RoomFactoryImpl.MIN_ENEMIES_PER_ROOM 
                        : result;
        result = result > effectiveNumberOfRooms * RoomFactoryImpl.MAX_ENEMIES_PER_ROOM
                        ? effectiveNumberOfRooms * RoomFactoryImpl.MAX_ENEMIES_PER_ROOM
                        : result;
        return result;
    }

    private int getRandomNumberOfTreasure(final int effectiveNumOfRooms, final Difficulty difficulty,
                                          final int interagibleSetted) {
        final int baseValue = (int) Math.round(effectiveNumOfRooms * Difficulty.NORMAL.getMultiplier());
        final int baseNumber = RandomUtils.getRandomIntegerGaussianNumber(baseValue, baseValue);
        int result = (int) Math.round(baseNumber * difficulty.getMultiplier());
        result = result < RoomFactoryImpl.MIN_INTERACTABLE_ACTION_PER_ROOM
                        ? RoomFactoryImpl.MIN_INTERACTABLE_ACTION_PER_ROOM
                        : result;
        result = result > (effectiveNumOfRooms * RoomFactoryImpl.MAX_INTERACTABLE_ACTIONS_PER_ROOM) - interagibleSetted
                        ? (effectiveNumOfRooms * RoomFactoryImpl.MAX_INTERACTABLE_ACTIONS_PER_ROOM) - interagibleSetted
                        : result;
        return result;
    }

    private int getRandomNumberOfContraptions(final int effectiveNumberOfRooms, final Difficulty difficulty,
                                              final int interagibleSetted) {
        return this.getRandomNumberOfTreasure(effectiveNumberOfRooms, difficulty, interagibleSetted);
    }

}
