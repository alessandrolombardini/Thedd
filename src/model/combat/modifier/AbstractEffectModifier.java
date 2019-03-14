package model.combat.modifier;

import java.util.Collections;
import java.util.Set;

import model.combat.action.effect.ActionEffect;
import model.combat.common.Modifiable;
import model.combat.tag.Tag;

/**
 * Abstract implementation of Effect modifiers' common behavior.
 */
public abstract class AbstractEffectModifier extends AbstractModifier {

    /**
     * @see model.combat.modifier.AbstractModifier#AbstractModifier(double, boolean, ModifierActivation)
     */
    protected AbstractEffectModifier(final double value,final boolean isPercentage,final ModifierActivation type) {
        super(value, isPercentage, type);
    }

    @Override
    public abstract void modify(Modifiable effect);

    /**
     * It checks whether the passed argument is null, is an instance of
     * {@link ActionEffect} and if the tags provided by the getTags() method match.
     */
    @Override
    public boolean accept(final Modifiable effect) {
        if (effect == null) {
            return false;
        }
        if (!(effect instanceof ActionEffect)) {
            return false;
        }
        ActionEffect e = ((ActionEffect) effect);
        final Set<Tag> effectTags = e.getTags();
        final Set<Tag> targetTags = e.getTargetTags();
        final Set<Tag> sourceTags = e.getSourceTags();
        return Collections.disjoint(effectTags, getModifiableTargetTags(false))
                && Collections.disjoint(targetTags, getTargetActorTargetTags(false))
                && Collections.disjoint(sourceTags, getSourceActorTargetTags(false))
                && effectTags.containsAll(getModifiableTargetTags(true))
                && targetTags.containsAll(getTargetActorTargetTags(true))
                && sourceTags.containsAll(getSourceActorTargetTags(true));
    }

}
