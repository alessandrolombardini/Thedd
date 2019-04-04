package model.combat.action;

/**
 * Defines the possible target types for an Action. 
 */
public enum TargetType {

    /**
     * The associated {@link Action} can only target {@link ActionActor}
     * present in the actor's own party.
     */
    ALLY,
    /**
     * The associated {@link Action} can only target {@link ActionActor}
     * present in the actor's opposing party.
     */
    FOE,
    /**
     * The associated {@link Action} can target every {@link ActionActor}.
     */
    EVERYONE,
    /**
     * The associated {@link Action} can only target it's source.
     */
    SELF;

}
