package thedd.model.character.statistics;

/**
 * Representation of characters' statistics.
 */
public enum Statistic {

    /** Represents the amount of damage a character can take before dying. */
    HEALTH_POINT(20),

    /** Represents the character's physical toughness. */
    CONSTITUTION(5),

    /** Represents the character's physical power. */
    STRENGTH(10),

    /** Represents a measure of how agile a character is. */
    AGILITY(5);
    
    private final int basicValue;
    
    private Statistic (final int value) {
        this.basicValue = value;
    }
    
    public int getBasicValue() {
        return this.basicValue;
    }

}
