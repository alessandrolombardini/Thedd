package model.environment.floor;

import java.util.EnumMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import model.environment.floor.FloorDetails.Builder;
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

    private final Random random;
    private Optional<Integer> effectiveRooms;
    private Optional<Difficulty> difficulty;
    private Optional<Builder> builder;

    /**
     * FloorDetailsFactory constructor.
     */
    public FloorDetailsFactoryImpl() {
        random = new Random(System.currentTimeMillis());
        this.effectiveRooms = Optional.empty();
        this.difficulty = Optional.empty();
        this.builder = Optional.empty();
    }

    @Override
    public FloorDetails createFloorDetails(final Difficulty difficulty, final int numberOfRooms, final boolean lastFloor) {
        Objects.requireNonNull(difficulty);
        if (numberOfRooms < EnvironmentImpl.MIN_NUMBER_OF_ROOMS) {
            throw new IllegalArgumentException();
        }
        this.effectiveRooms = Optional.of(numberOfRooms - 1);
        this.difficulty = Optional.of(difficulty);
        this.builder = Optional.of(new FloorDetails.Builder());
        this.setValues();
        return builder.get().build();
    }

    private void setValues() {
        this.builder.get().difficulty(this.difficulty.get());
        this.builder.get().enemies(this.getRandomNumberOfEnemies());
        this.builder.get().contraptions(this.getRandomNumberOfContraptions());
        this.builder.get().treasures(this.getRandomNumberOfTreasure());
    }

    private int getRandomNumberOfEnemies() {
        return (int) Math.round(this.effectiveRooms.get() * MULTIPLIER.get(this.difficulty.get()))
                + this.getRandomRoundingNumber();
    }

    private int getRandomNumberOfTreasure() {
        return (int) Math.round(this.getGaussianRoundingNumber() * MULTIPLIER.get(this.difficulty.get()));
    }

    private int getRandomNumberOfContraptions() {
        return (int) Math.round(this.getGaussianRoundingNumber() * MULTIPLIER.get(this.difficulty.get()));
    }

    private int getRandomRoundingNumber() {
        return (int) this.random
                .nextInt((int) Math.round(this.effectiveRooms.get() * MULTIPLIER.get(Difficulty.EASY)) + 1);
    }

    private int getGaussianRoundingNumber() {
        return (int) Math.round(
                random.nextGaussian() * Math.sqrt(this.getRoundingNumberConstant()) + this.getRoundingNumberConstant());
    }

    private int getRoundingNumberConstant() {
        return (int) Math.round(this.effectiveRooms.get() * MULTIPLIER.get(Difficulty.NORMAL));
    }

}
