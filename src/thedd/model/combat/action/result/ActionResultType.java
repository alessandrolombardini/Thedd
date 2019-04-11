package thedd.model.combat.action.result;

/**
 * Defines whether an {@Action} has hit, missed or it has been
 * parried by its target.
 */
public enum ActionResultType {

    /**
     * Target hit.
     */
    HIT,
    /**
     * Target missed.
     */
    MISSED,
    /**
     * Action has been parried.
     */
    PARRIED

}
