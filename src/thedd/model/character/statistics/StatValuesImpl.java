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
        if (this.actual + value <= 0) {
            this.actual = 0;
        } else {
            if (this.max != NO_MAX) {
                if (this.actual + value > this.max) {
                    this.actual = this.max;
                }
            } else {
                this.actual = this.actual + value;
            }
        }
    }

    @Override
    public void updateMax(final int value) {
        if (this.max != NO_MAX) {
            final int oldMax = this.max;
            this.max = this.max + value;
            updateActual((int) (this.actual * this.max) / oldMax);
        }
    }

    @Override
    public int getActual() {
        return this.actual;
    }

    @Override
    public int getMax() {
        return this.max;
    }

    @Override
    public String toString() {
        if (this.max == NO_MAX) {
            return String.valueOf(this.actual);
        }
        return this.actual + "/" + this.max;
    }
}
