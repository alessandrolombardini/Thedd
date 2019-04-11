package thedd.model.combat.modifier;

import java.util.List;

import thedd.model.character.BasicCharacter;
import thedd.model.character.statistics.Statistic;
import thedd.model.combat.common.Modifiable;
import thedd.model.combat.requirements.Requirement;

/**
 * Decorator for a {@link ValueModifier}.<br>
 * Implements a value modifier that, given a ratio, scales
 * with a given {@link Statistic} of a target {@link BasicCharacter}.<p>
 * 
 * When the modify method is called, the modifiable will be modified
 * with a value = modifierValue * statisticValue * multiplier.
 * @param <T> the type of the modifiable
 */
public class StatBasedModifier<T extends Modifiable> implements ValueModifier<T> {

    private final ValueModifier<T> modifier;
    private final Statistic statistic;
    private final double multiplier;
    private final BasicCharacter target;

    /**
     * Public constructor of the modifier.
     * @param targetStat the {@link Statistic} to monitor
     * @param targetCharacter the {@link BasicCharater to monitor}
     * @param multiplier a multiplier for the statistic value
     * @param modifier the decorated modifier
     */
    public StatBasedModifier(final Statistic targetStat, final BasicCharacter targetCharacter, final double multiplier, final ValueModifier<T> modifier) {
        this.modifier = modifier;
        statistic = targetStat;
        this.multiplier = multiplier;
        target = targetCharacter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(final double value) {
        modifier.setValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsPercentage(final boolean isPercentage) {
        modifier.setIsPercentage(isPercentage);
    }

    /**
     * {@inheritDoc}
     * The value is already updated accordingly to the character's
     * statistic.
     */
    @Override
    public double getValue() {
        return getUpdatedValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPercentage() {
        return modifier.isPercentage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModifierActivation getModifierActivation() {
        return modifier.getModifierActivation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModifierActivation(final ModifierActivation type) {
        modifier.setModifierActivation(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(final T modifiable) {
        return modifier.accept(modifiable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modify(final T modifiable) {
        final double baseValue = modifier.getValue();
        final double newValue = getUpdatedValue();
        modifier.setValue(newValue);
        modifier.modify(modifiable);
        modifier.setValue(baseValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRequirement(final Requirement<T> requirement) {
        modifier.addRequirement(requirement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Requirement<T>> getRequirements() {
        return modifier.getRequirements();
    }

    private double getUpdatedValue() {
        final double mul = target.getStat(statistic).getActual() * multiplier;
        return modifier.getValue() * mul;
    }

}
