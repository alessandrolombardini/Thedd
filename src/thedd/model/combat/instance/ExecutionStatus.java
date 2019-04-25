package thedd.model.combat.instance;

/**
 * Defines the various states of a combat/action execution
 * instance.
 */
public enum ExecutionStatus {

    /**
     * Player has won the combat.
     */
    PLAYER_WON,
    /**
     * Player has lost the combat.
     */
    PLAYER_LOST,
    /**
     * The combat has yet to start.
     */
    NOT_STARTED,
    /**
     * The combat has started but the first action
     * has yet to be executed.
     */
    STARTED,
    /**
     * The round is ongoing.
     */
    ROUND_IN_PROGRESS,
    /**
     * The round is paused but not ended.<br>
     * This can happen when a combatant successfully
     * interrupts another combatants' turn and must
     * then select his reaction, thus breaking the
     * flow of the round.
     */
    ROUND_PAUSED,
    /**
     * The round has ended and a new one has to be
     * started.
     */
    ROUND_ENDED,
    /**
     * The combat has ended with neither side winning
     * or losing.<br>
     * In practice, this happens with out of combat 
     * action executions which do not result in the player
     * being defeated.
     */
    COMBAT_ENDED

}
