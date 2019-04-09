package thedd.model.combat.modifier;

import thedd.model.combat.common.Modifiable;

public abstract class AbstractValueModifier<T extends Modifiable> extends AbstractModifier<T> implements ValueModifier<T> {
    
    private boolean percentage;
    private double value;

    /**
     * @param value the value that will be applied to the modifiable
     * @param isPercentage true if the value shall be treated as a percentage
     * @param type declares whether this modifier should be applied on attack, defense or everytime
     */
    protected AbstractValueModifier(final double value, final boolean isPercentage, final ModifierActivation type) {
        super(type);
        this.value = value;
        this.percentage = isPercentage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(final double value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsPercentage(final boolean isPercentage) {
        this.percentage = isPercentage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPercentage() {
        return percentage;
    }

}
