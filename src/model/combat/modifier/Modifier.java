package model.combat.modifier;

import java.util.List;
import model.combat.common.Modifiable;
import model.combat.requirements.Requirement;

/**
 * An entity that can modify the attributes of another entity that
 * fulfills certain requirements.
 * @param <T> the type of the modifiable entity
 */
public interface Modifier<T extends Modifiable> {

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

    /**
     * Gets the modifier activation temporal condition.
     * @return the {@link ModifierActivation} of the modifier
     */
    ModifierActivation getModifierActivation();

    /**
     * Sets the {@link ModifierActivation} condition of the modifier.
     * @param type the modifier activation condition
     */
    void setModifierActivation(ModifierActivation type);

    /**
     * Checks whether the Modifiable is accepted by the modifier and can be modified.
     * @param modifiable the Modifiable to be accepted
     * @return true if the parameter can be modified by this modifier, false otherwise
     */
    boolean accept(T modifiable);

    /**
     * Modifies the specified Modifiable.<br>
     * The {@link #accept} method must be called beforehand to check if the modifiable
     * is valid.<br>
     * Doing otherwise may result in exceptions.
     * @param modifiable the modifiable to be modified
     */
    void modify(T modifiable);

    /**
     * Adds a {@link Requirement} to the modifier that must be satisfied for the modifier
     * to be applied.
     * @param requirement the requirement to be added
     */
    void addRequirement(Requirement<T> requirement);

    /**
     * Gets the collection of {@link Requirement}.
     * @return the requirements of this modifier
     */
    List<Requirement<T>> getRequirements();

}
