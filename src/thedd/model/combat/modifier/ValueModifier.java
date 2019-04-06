package thedd.model.combat.modifier;

import thedd.model.combat.common.Modifiable;

public interface ValueModifier<T extends Modifiable> extends Modifier<T> {
    /**
     * Sets the value of this modifier.
     * @param value the value of the modifier
     */
    void setValue(double value);

    /**
     * Sets if the value of the modifier should be treated as a percentage.
     * @param isPercentage true if the value is a percentage, false otherwise
     */
    void setIsPercentage(boolean isPercentage);

    /**
     * Gets the value of the modifier.
     * @return the value of the modifier
     */
    double getValue();

    /**
     * Gets whether or not the value of the modifier is treated as a percentage.
     * @return true if the value is a percentage, false otherwise
     */
    boolean isPercentage();
}
