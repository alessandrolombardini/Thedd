package model.combat.modifier;

import model.combat.actor.ActionActor;

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
     * The modifier is always active and his accept method
     * should be always called.
     */
    ALWAYS_ACTIVE;
}
