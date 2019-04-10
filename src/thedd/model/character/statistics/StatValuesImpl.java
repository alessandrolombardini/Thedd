package thedd.model.character.statistics;

/**
 * Implementation of {@link thedd.model.character.statistics.StatValues}.
 */
public final class StatValuesImpl implements StatValues {

    private int actual;
    private int max;

    /**
     * StatValues constructor.
     * 
     * @param value specifies the max value of this statistic. The actual value is
     *              automatically set.
     */
    public StatValuesImpl(final int value) {
        this.max = value;
        this.actual = value;
    }

    @Override
    public void updateActual(final int value) {
        if (this.actual + value > this.max) {
            this.actual = this.max;
        } else {
            this.actual = this.actual + value;
        }
    }

    @Override
    public void updateMax(final int value) {
        final int oldMax = this.max;
        this.max = this.max + value;
        updateActual((int) (this.actual * this.max) / oldMax);
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
        return this.actual + "/" + this.max;
    }
}
