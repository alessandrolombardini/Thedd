package thedd.model.world.floor;

import java.util.Objects;

import org.apache.commons.lang3.RandomUtils;

import edu.princeton.cs.algs4.StdRandom;
import thedd.model.world.Difficulty;
import thedd.model.world.environment.EnvironmentImpl;
import thedd.model.world.floor.details.FloorDetails;
import thedd.model.world.floor.details.FloorDetailsBuilderImpl;
import thedd.model.world.room.RoomFactoryImpl;

/**
 * Implementation of {@link thedd.model.world.floor.FloorDetailsFactory}.
 */
public final class FloorDetailsFactoryImpl implements FloorDetailsFactory {

    private static final String ERROR_NONVALIDROOMS = "Number of rooms is not valid";

    /**
     * {@inheritDoc}
     */
    @Override
    public FloorDetails createFloorDetails(final Difficulty difficulty, final int numberOfRooms,
                                           final boolean lastFloor) {
        Objects.requireNonNull(difficulty);
        if (numberOfRooms < EnvironmentImpl.MIN_NUMBER_OF_ROOMS) {
            throw new IllegalArgumentException(ERROR_NONVALIDROOMS);
        }
        final int numberOfIteragibleSetted = 0;
        final int numberOfContraptions = this.getRandomNumberOfContraptions(numberOfRooms - 1, difficulty,
                                                                            numberOfIteragibleSetted);
        final int numberOfTreasures = this.getRandomNumberOfTreasures(numberOfRooms - 1, difficulty,
                                                                     numberOfContraptions);
        final int numberOfEnemies = this.getRandomNumberOfEnemies(numberOfRooms - 1, difficulty);
        return new FloorDetailsBuilderImpl().setDifficulty(difficulty)
                                            .setNumberOfRooms(numberOfRooms)
                                            .setIsLastFloor(lastFloor)
                                            .setNumberOfEnemies(numberOfEnemies)
                                            .setNumberOfContraptions(numberOfContraptions)
                                            .setNumberOfTreasures(numberOfTreasures)
                                            .build();
    }

    private int getRandomNumberOfEnemies(final int effectiveNumberOfRooms, final Difficulty difficulty) {
        final int baseNumber = (int) Math.round(effectiveNumberOfRooms * difficulty.getMultiplier());
        final int maxRandRoundIntNum = (int) Math.round(effectiveNumberOfRooms * Difficulty.EASY.getMultiplier());
        final int roundNumber = RandomUtils.nextInt(0, maxRandRoundIntNum + 1);
        final int result = baseNumber + roundNumber;
        return this.roundNumber(result, RoomFactoryImpl.MIN_ENEMIES_PER_ROOM,
                                effectiveNumberOfRooms * RoomFactoryImpl.MAX_ENEMIES_PER_ROOM);
    }

    private int getRandomNumberOfTreasures(final int effectiveNumOfRooms, final Difficulty difficulty,
                                          final int interagibleSetted) {
        final int baseValue = (int) Math.round(effectiveNumOfRooms * Difficulty.NORMAL.getMultiplier());
        final int baseNumber = getGaussian(baseValue, baseValue);
        final int result = (int) Math.round(baseNumber * difficulty.getMultiplier());
        return this.roundNumber(result, RoomFactoryImpl.MIN_INTERACTABLE_ACTION_PER_ROOM, 
                               (effectiveNumOfRooms * RoomFactoryImpl.MAX_INTERACTABLE_ACTIONS_PER_ROOM) 
                               - interagibleSetted);
    }

    private int getRandomNumberOfContraptions(final int effectiveNumberOfRooms, final Difficulty difficulty,
                                              final int interagibleSetted) {
        return this.getRandomNumberOfTreasures(effectiveNumberOfRooms, difficulty, interagibleSetted);
    }

    private int getGaussian(final int mediumVal, final int var) {
        final int val = (int) Math.round(StdRandom.gaussian(mediumVal, var));
        return this.roundNumber(val, mediumVal - var, mediumVal + var);
    }

    private int roundNumber(final int number, final int lowerBound, final int upperBound) {
        int num = number;
        num = num < lowerBound ? lowerBound : num;
        num = num > upperBound ? upperBound : num;
        return num;
    }

}
