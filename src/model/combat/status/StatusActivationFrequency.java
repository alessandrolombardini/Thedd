package model.combat.status;

/**
 *  Indicates the frequency with which the Status provides
 *  its actions. 
 */
public enum StatusActivationFrequency {

    /**
     * The status will provide its activation action only
     * the first time that {@link Status#update()} is
     * called.<br>
     * The expiration action is returned as normal.
     */
    ONE_TIME,
    /**
     * The status will provide its activation action every
     * time the {@link Status#update()} method is called.<br>
     * The expiration action is returned as normal.
     */
    OVER_TIME

}
