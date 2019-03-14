package model.combat.modifier;

import java.util.Collections;
import java.util.Set;

import model.combat.action.Action;
import model.combat.common.Modifiable;
import model.combat.tag.Tag;

/**
 * Abstract implementation of Action Modifiers' common behaviors.
 */
public abstract class AbstractActionModifier extends AbstractModifier {

    /**
     * @see model.combat.modifier.AbstractModifier#AbstractModifier(double, boolean, ModifierActivation)
     */
    public AbstractActionModifier(final double value, final boolean isPercentage, final ModifierActivation type) {
        super(value, isPercentage, type);
    }

    @Override
    public abstract void modify(Modifiable modifiable);

    /**
     * It checks whether the passed argument is null, is an instance of
     * {@link Action} and if the tags provided by the getTags() method match.
     */
    @Override
    public boolean accept(final Modifiable modifiable) {
        if (modifiable == null) {
            return false;
        }
        if (!(modifiable instanceof Action)) {
            return false;
        }
        Action e = ((Action) modifiable);
        final Set<Tag> effectTags = e.getTags();
        final Set<Tag> targetTags = e.getCurrentTarget().getTags();
        final Set<Tag> sourceTags = e.getSource().get().getTags();
        return Collections.disjoint(effectTags, getModifiableTargetTags(false))
                && Collections.disjoint(targetTags, getTargetActorTargetTags(false))
                && Collections.disjoint(sourceTags, getSourceActorTargetTags(false))
                && effectTags.containsAll(getModifiableTargetTags(true))
                && targetTags.containsAll(getTargetActorTargetTags(true))
                && sourceTags.containsAll(getSourceActorTargetTags(true));
    }

}
