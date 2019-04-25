package thedd.model.character.statistics;

/**
 * This class represents statistics' pair of current and max value.
 */
public interface StatValues {

    /**
     * This method update the actual field.
     * 
     * @param value is the value added to the actual field.
     */
    void updateActual(int value);

    /**
     * This method update the max field. The actual field is updated proportionally
     * to the max one automatically.
     * 
     * @param value is the value added to the max field.
     */
    void updateMax(int value);

    /**
     * Getter for actual value.
     * 
     * @return the actual value.
     */
    int getActual();

    /**
     * Getter for max value.
     * 
     * @return the max value.
     */
    int getMax();

}
