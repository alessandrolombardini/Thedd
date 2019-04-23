package thedd.model.character.statistics;

/**
 * Representation of characters' statistics.
 */
public enum Statistic {

    /** Represents the amount of damage a character can take before dying. */
    HEALTH_POINT("Health Points"),

    /** Represents the character's physical toughness. */
    CONSTITUTION("Constitution"),

    /** Represents the character's physical power. */
    STRENGTH("Strength"),

    /** Represents a measure of how agile a character is. */
    AGILITY("Agility");

    private final String representation;

    Statistic(final String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return this.representation;
    }
}
