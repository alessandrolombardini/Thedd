package thedd.model.combat.modifier;

import thedd.model.combat.action.Action;
import thedd.model.combat.tag.ActionTag;

/**
 * A modifier that modifies an {@link Action}'s hit chance. 
 */
public class HitChanceModifier extends AbstractValueModifier<Action> {

    /**
     * @param value the value that will be applied to the modifiable
     * @param isPercentage true if the value shall be treated as a percentage
     * @param type declares whether this modifier should be applied on attack, defense or every time
     */
    public HitChanceModifier(final double value, final boolean isPercentage, final ModifierActivation type) {
        super(value, isPercentage, type);
    }

    /**
     * Modifies the hit chances of the target Action as follows:<p>
     * modifier = isPercentage ? baseHitChance * value : value;<br>
     * action.addToCurrentHitChance(modifier);
     */
    @Override
    public void modify(final Action modifiable) {
        final Action action = (Action) modifiable;
        final double modifier = isPercentage() ? action.getBaseHitChance() * getValue() : getValue();
        action.addToCurrentHitChance(modifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(final Action action) {
        return super.accept(action) && !action.getTags().contains(ActionTag.IGNORES_HITCHANCE_MOD);
    }

    @Override
    public final String toString() {
        return "Hit chance is modified by " + this.getValue() * 100 + "% when" + (this.getModifierActivation() == ModifierActivation.ACTIVE_ON_ATTACK ? "attacking" : " defending");
    }
}
