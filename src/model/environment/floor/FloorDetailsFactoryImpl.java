package model.environment.floor;

import java.util.EnumMap;
import java.util.Objects;
import java.util.Optional;

import model.environment.enums.Difficulty;
import model.environment.environment.EnvironmentImpl;

/**
 * This class represents all details of the floor.
 *
 */
public final class FloorDetailsFactoryImpl implements FloorDetailsFactory {

    private static final double EASY_MODE_MULTIPLIER = 0.25;
    private static final double NORMAL_MODE_MULTIPLIER = 0.5;
    private static final double HARD_MODE_MULTIPLIER = 1;
    private static final EnumMap<Difficulty, Double> MULTIPLIER;
    static {
        MULTIPLIER = new EnumMap<Difficulty, Double>(Difficulty.class);
        MULTIPLIER.put(Difficulty.EASY, EASY_MODE_MULTIPLIER);
        MULTIPLIER.put(Difficulty.NORMAL, NORMAL_MODE_MULTIPLIER);
        MULTIPLIER.put(Difficulty.HARD, HARD_MODE_MULTIPLIER);
    }

    private Optional<Integer> effectiveNumberOfRooms;
    private Optional<Difficulty> difficulty;

    /**
     * FloorDetailsFactory constructor.
     */
    public FloorDetailsFactoryImpl() {
        this.effectiveNumberOfRooms = Optional.empty();
        this.difficulty = Optional.empty();
    }

    @Override
    public FloorDetails createFloorDetails(final Difficulty difficulty, final int numberOfRooms, final boolean lastFloor) {
        Objects.requireNonNull(difficulty);
        if (numberOfRooms < EnvironmentImpl.MIN_NUMBER_OF_ROOMS) {
            throw new IllegalArgumentException("The number of rooms is not valid");
        }
        this.effectiveNumberOfRooms = Optional.of(numberOfRooms - 1);
        this.difficulty = Optional.of(difficulty);
        return new FloorDetails.Builder().difficulty(this.difficulty.get())
                                         .enemies(this.getRandomNumberOfEnemies())
                                         .contraptions(this.getRandomNumberOfContraptions())
                                         .treasures(this.getRandomNumberOfTreasure())
                                         .build();
    }

    private int getRandomNumberOfEnemies() {
        return (int) Math.round(this.effectiveNumberOfRooms.get() * MULTIPLIER.get(this.difficulty.get())) 
                + RandomUtils.getRandomInteger((int) Math.round(this.effectiveNumberOfRooms.get() * MULTIPLIER.get(Difficulty.EASY)));
    }

    private int getRandomNumberOfTreasure() {
        return (int) Math.round(RandomUtils.getRandomGaussianNumber(this.getFloorMultiplierValue(), this.getFloorMultiplierValue())
                * MULTIPLIER.get(this.difficulty.get()));
    }

    private int getRandomNumberOfContraptions() {
        return (int) Math.round(RandomUtils.getRandomGaussianNumber(this.getFloorMultiplierValue(), this.getFloorMultiplierValue())
                        * MULTIPLIER.get(this.difficulty.get()));
    }

    private int getFloorMultiplierValue() {
        return (int) Math.round(this.effectiveNumberOfRooms.get() * MULTIPLIER.get(Difficulty.NORMAL));
    }

}
