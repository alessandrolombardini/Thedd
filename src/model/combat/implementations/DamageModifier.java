package model.combat.implementations;

import model.combat.enums.ModifierActivation;
import model.combat.interfaces.Modifiable;

/**
 * A modifier which modifies effects of type {@link DamageEffect}.
 */
public class DamageModifier extends AbstractEffectModifier {

    /**
     * @param value the value to be added to the effect
     * @param isPercentage true if the value is a percentage, false otherwise
     * @param type the Activation type
     */
    public DamageModifier(final double value, final boolean isPercentage, final ModifierActivation type) {
        super(value, isPercentage, type);
    }

    /**
     * Adds the value to the damage, such as:
     * damageToBeAdded = isPercentage ? DamageEffect.getDamage() * getValue : getValue;
     * @throws ClassCastException
     */
    @Override
    public void modify(final Modifiable effect) {
            //rendere DamageEffect un'interfaccia
            //modifier = isPercentage ? ((DamageEffect)effect).getDamage() * getValue : getValue;
            //(DamageEffect)effect).setDamage(getDamage + modifier)
            //TODELETE
        ((DamageEffect) effect).addToDamage(getValue());
    }

    /**
     * Checks whether the Modifiable is accepted as per {@link AbstractEffectModifier#accept(Modifiable)}
     * and it is an instance of {@link DamageEffect}.
     * @param effect the effect to be accepted
     */
    @Override
    public boolean accept(final Modifiable effect) {
        return super.accept(effect) && (effect instanceof DamageEffect);
    }

}
