package model.combat.common;

import java.util.Optional;

import model.combat.actor.ActionActor;

/**
 * An entity that can hold an {@link ActionActor} as 
 * a source (e.g. an Action, an Effect...)
 */
public interface SourceHolder {

    /**
     * Gets the source of this entity.
     * @return the source actor
     */
    Optional<ActionActor> getSource();

}
