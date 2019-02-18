package model.character;

/**
 * This class represents statistics' pair of current and max value.
 */
public class StatValues {

    private int actual;
    private int max;

    /** 
     * StatValues constructor.
     * @param value specifies the max value of this statistic. 
     *          The actual value is automatically set.
     */
    public StatValues(final int value) {
        this.max = value;
        this.actual = value;
    }

    /**
     * This method update the actual field. 
     * @param value is the value added at the actual field.
     */
    public void updateActual(final int value) {
        this.actual = this.actual + value;
    }

    /**
     * This method update the max field.
     * The actual field is updated proportionally to the max one automatically.
     * @param value is the value added to the max field.
     */
    public void updateMax(final int value) {
        final int oldMax = this.max;

        this.max = this.max + value;
        this.actual = (int) (this.actual * this.max) / oldMax;
    }

    /**
     * Getter for actual value.
     * @return the actual value.
     */
    public int getActual() {
        return this.actual;
    }

    /**
     * Getter for max value.
     * @return the max value.
     */
    public int getMax() {
        return this.max;
    }
}
