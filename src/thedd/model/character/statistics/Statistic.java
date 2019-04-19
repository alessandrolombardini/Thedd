package thedd.model.character.statistics;

/**
 * Representation of characters' statistics.
 */
public enum Statistic {

    /** Represents the amount of damage a character can take before dying. */
    HEALTH_POINT(20, "Health Points"),

    /** Represents the character's physical toughness. */
    CONSTITUTION(5, "Constitution"),

    /** Represents the character's physical power. */
    STRENGTH(10, "Strength"),

    /** Represents a measure of how agile a character is. */
    AGILITY(5, "Agility");

    private final int basicValue;
    private final String representation;

    Statistic(final int value, final String representation) {
        this.basicValue = value;
        this.representation = representation;
    }

    /**
     * This method returns the basic value of the selected statistic.
     * 
     * @return a int value
     */
    public int getBasicValue() {
        return this.basicValue;
    }

    @Override
    public String toString() {
        return this.representation;
    }
}
