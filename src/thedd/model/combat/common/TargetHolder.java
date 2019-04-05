package thedd.model.combat.common;

import java.util.Optional;

import thedd.model.combat.actor.ActionActor;

/**
 * An entity that can hold an {@link ActionActor} as 
 * a target (e.g. an Action, an Effect...)
 */
public interface TargetHolder {

    /**
     * Gets the target of this entity.
     * @return the target actor
     */
    Optional<ActionActor> getTarget();

}
