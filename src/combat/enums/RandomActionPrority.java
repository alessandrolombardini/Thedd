package combat.enums;

public enum RandomActionPrority {
    VERY_HIGH(90),
    HIGH(75),
    DEFAULT(50),
    LOW(25),
    VERY_LOW(10);

    private final double weight;

    RandomActionPrority(final double value) {
        weight = value;
    }

    public double getWeight() {
        return weight;
    }
}
