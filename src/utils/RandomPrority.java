package utils;

/**
 * Basic and easy-to-use default values for weights used in
 * a {@link utils.randomcollection.RandomCollection}.
 */
public enum RandomPrority {
    VERY_HIGH(90),
    HIGH(75),
    DEFAULT(50),
    LOW(25),
    VERY_LOW(10);

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
