package thedd.model.combat.action.effect;

import java.util.Objects;

import thedd.model.combat.action.Action;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.modifier.Modifier;

/**
 * Action effect which removes a modifier of actions
 * from it's source.
 */
public class ActionModifierRemoveEffect extends AbstractActionEffect {

    private final Modifier<Action> targetModifier;

    /**
     * @param modifier the modifier to remove
     */
    public ActionModifierRemoveEffect(final Modifier<Action> modifier) {
        targetModifier = Objects.requireNonNull(modifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(final ActionActor target) {
        target.removeActionModifier(targetModifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLogMessage() {
        return "No longer active: " + getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPreviewMessage() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionEffect getSpecializedCopy() {
        return new ActionModifierRemoveEffect(targetModifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return targetModifier.toString();
    }

}
