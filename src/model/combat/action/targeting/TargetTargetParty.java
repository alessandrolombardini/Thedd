package model.combat.action.targeting;

import java.util.List;

import model.combat.actor.ActionActor;

/**
 * Targeting component of an {@link Action} which targets
 * every {@ActionActor} available.
 */
public class TargetTargetParty extends DefaultTargeting {

    /**
     * Return as valid targets all the actors in the provided
     * availableTargets collection.
     */
    @Override
    public List<ActionActor> getTargets(final ActionActor target, final List<ActionActor> availableTargets) {
        return availableTargets;
    }
}
