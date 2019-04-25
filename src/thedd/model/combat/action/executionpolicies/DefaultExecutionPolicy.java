package thedd.model.combat.action.executionpolicies;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.actor.ActionActor;

/**
 * Default implementation of the {@link ExecutionPolicy} interface.<br>
 * It simply updates all the effects of the provided action and applies them to the target.
 */
public class DefaultExecutionPolicy implements ExecutionPolicy {

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyEffects(final Action action, final ActionActor target) {
        if (target != null) {
            action.getEffects().stream().forEach((e) -> {
                applyEffectModifiers(e, target, action.getSource().get());
                e.apply(target);
            });
        }
    }

    /**
     * Updates the effect according to the source and target's modifiers.
     * @param effect the effect to be updated
     * @param target the target of the effect
     * @param source the source of the effect
     */
    protected void applyEffectModifiers(final ActionEffect effect, final ActionActor target, final ActionActor source) {
        effect.updateEffectBySource(source);
        effect.updateEffectByTarget(target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutionPolicy getCopy() {
        return new DefaultExecutionPolicy();
    }

}
