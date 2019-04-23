package thedd.model.character.statistics;

/**
 * Implementation of {@link thedd.model.character.statistics.StatValues}.
 */
public final class StatValuesImpl implements StatValues {

    private int actual;
    private int max;
    /**
     * Value used to not set the maximum field.
     */
    public static final int NO_MAX = -1;

    /**
     * StatValues constructor.
     * 
     * @param actualValue specifies the actual value of this Statistic.
     * @param maxValue    specifies the max value of this statistic.
     * @throws IllegalArgumentException if values are negative.
     */
    public StatValuesImpl(final int actualValue, final int maxValue) {
        if (maxValue != NO_MAX && (maxValue < 1 || actualValue < 1)) {
            throw new IllegalArgumentException();
        }
        this.max = maxValue;
        this.actual = actualValue;
    }

    @Override
    public void updateActual(final int value) {
        if (actual + value <= 0) {
            actual = 0;
        } else {
            if (max != NO_MAX && actual + value > max) {
                actual = max;
            } else {
                actual = actual + value;
            }
        }
    }

    @Override
    public void updateMax(final int value) {
        if (max != NO_MAX) {
            final int oldMax = max;
            max = max + value;
            actual = (int) Math.round(actual * (((double) max) / ((double) oldMax)));
        }
    }

    @Override
    public int getActual() {
        return actual;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public String toString() {
        if (max == NO_MAX) {
            return String.valueOf(this.actual);
        }
        return actual + "/" + max;
    }
}
