package thedd.model.combat.action.executionpolicies;

import java.util.ArrayList;
import thedd.model.combat.action.Action;
import thedd.model.combat.actor.ActionActor;

/**
 * Execution policy which adds an action to the queue of actions of
 * the source of the original action.<br>
 * Useful for modeling actions that have repercussions on its users.
 * (e.g the action "Divine intervention" which, after being used, applies "Weakness" status to the character
 * performing it).
 */
public class ExtraActionToSource extends DefaultExecutionPolicy {

    private final Action extraAction;

    /**
     * @param extraAction the extra action to assign to the source actor
     */
    public ExtraActionToSource(final Action extraAction) {
        this.extraAction = extraAction;
    }

    /**
     * {@inheritDoc}<br>
     * Adds and extra action to the queue of the source actor
     */
    @Override
    public void applyEffects(final Action action, final ActionActor target) {
        final ActionActor lastTarget = action.getTargets().get(action.getTargets().size() - 1);
        if (target.equals(lastTarget)) {
            final ActionActor source = action.getSource().get();
            extraAction.setSource(source);
            extraAction.setTargets(source, new ArrayList<>());
            source.addActionToQueue(extraAction, false);
        }
        super.applyEffects(action, target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutionPolicy getCopy() {
        return new ExtraActionToSource(extraAction);
    }

}
