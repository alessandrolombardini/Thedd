package thedd.utils.randomcollections;

/**
 * Basic and easy-to-use default values for weights used in
 * a {@link thedd.utils.randomcollections.RandomCollection}.
 */
public enum RandomPrority {

    /**
     * Very high priority.
     */
    VERY_HIGH(95),
    /**
     * High priority.
     */
    HIGH(75),
    /**
     * Default priority.
     */
    DEFAULT(50),
    /**
     * Low priority.
     */
    LOW(25),
    /**
     * Very low priority.
     */
    VERY_LOW(5);

    private final double weight;

    RandomPrority(final double value) {
        weight = value;
    }

    /**
     * Gets the double value assigned to the enum value.
     * @return the weight associated with the selected value
     */
    public double getWeight() {
        return weight;
    }
}
