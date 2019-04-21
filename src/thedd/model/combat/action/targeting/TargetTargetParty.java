package thedd.model.combat.action.targeting;

import java.util.List;
import java.util.stream.Collectors;

import thedd.model.combat.actor.ActionActor;

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
        return availableTargets.stream()
                               .filter(t -> t.isInPlayerParty() == target.isInPlayerParty())
                               .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionTargeting getCopy() {
        return new TargetTargetParty();
    }
}
