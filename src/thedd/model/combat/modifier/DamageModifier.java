package thedd.model.combat.modifier;

import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.action.effect.DamageEffect;

/**
 * A modifier which modifies effects of type {@link DamageEffect}.
 */
public class DamageModifier extends AbstractValueModifier<ActionEffect> {

    private final boolean baseValue;

    /**
     * @param value the value to be added to the effect
     * @param isPercentage true if the value is a percentage, false otherwise
     * @param type the Activation type
     * @param baseValue true if the modifier, when set to percentage, should base itself on the base damageValue, false for the actual damage
     */
    public DamageModifier(final double value, final boolean baseValue, final boolean isPercentage, final ModifierActivation type) {
        super(value, isPercentage, type);
        this.baseValue = baseValue;
    }

    /**
     * Adds the value to the damage, such as:<br>
     * damageToBeAdded = isPercentage ? damage * getValue : getValue;
     * @throws ClassCastException
     */
    @Override
    public void modify(final ActionEffect effect) {
        final double startingValue = baseValue ? ((DamageEffect) effect).getBaseDamage() : ((DamageEffect) effect).getDamage();
        final double modifier = isPercentage() ? startingValue * getValue() : getValue();
        ((DamageEffect) effect).addToDamage(modifier);
    }

    /**
     * Checks whether the Modifiable is accepted as per {@link AbstractEffectModifier#accept(Modifiable)}
     * and it is an instance of {@link DamageEffect}.
     * @param effect the effect to be accepted
     */
    @Override
    public boolean accept(final ActionEffect effect) {
        return super.accept(effect) && (effect instanceof DamageEffect);
    }

}
