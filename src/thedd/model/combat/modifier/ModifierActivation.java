package thedd.model.combat.modifier;

/**
 *  Defines when a {@link Modifier} has to be
 *  activated.
 */
public enum ModifierActivation {

    /**
     * The modifier should be tried to be applied when
     * the {@link ActionActor} is attacking.
     */
    ACTIVE_ON_ATTACK,
    /**
     * The modifier should be tried to be applied when
     * the {@link ActionActor} is defending.
     */
    ACTIVE_ON_DEFENCE,
    /**
     * The modifier is tested when retrieving the action
     * from the actor, regardless of the role of the actor.
     */
    RETRIEVING_ACTION;
}
