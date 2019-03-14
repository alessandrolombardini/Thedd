package model.combat.common;

import java.util.List;

import model.combat.actor.ActionActor;

/**
 * Holds information about the current combat.
 */
public interface CombatInstance {

    /**
     * Returns the current round number.
     * @return the round number
     */
    int getRoundNumber(); 

    /**
     * Increases the current round number.
     */
    void increaseRoundNumber();

    /**
     * Resets the instance, clearing all lists, resetting 
     * the round number to 0 and setting the combat status to NOT_STARTED.
     */
    void resetInstance();

    /**
     * Adds Actors to the party opposed to the player's one.
     * @param hostileNPCs the list of hostile Actors
     */
    void addNPCsPartyMembers(List<ActionActor> hostileNPCs);

    /**
     * Adds Actors to the player's party.
     * @param alliedPCs the list of the player's party members
     */
    void addPlayerPartyMembers(List<ActionActor> alliedPCs);

    /**
     * Adds an actor to the party opposed to the player's one.
     * @param hostileNPC an hostile Actor
     */
    void addNPCsPartyMember(ActionActor hostileNPC);

    /**
     * Adds an actor to the player's party.
     * @param alliedPC a friendly Actor
     */
    void addPlayerPartyMember(ActionActor alliedPC);

    /**
     * Returns the list of Actors present in the party opposed
     * to theplayer's one.
     * @return the list of hostile actors
     */
    List<ActionActor> getNPCsParty();

    /**
     * Returns the list of Actors present in the player's party.
     * @return the list of player's actors
     */
    List<ActionActor> getPlayerParty();

    /**
     * Sets the current combat status.
     * @param newStatus the new status
     */
    void setCombatStatus(CombatStatus newStatus);

    /**
     * Returns the current combat status.
     * @return the current status
     */
    CombatStatus getCombatStatus();

    /**
     * Returns a list containing all the Actors involved in the combat.
     * @return a list of all the actors
     */
    List<ActionActor> getAllParties();

    /**
     * Returns a copy of the instance.
     * @return a copy of the instance
     */
    CombatInstance getCopy();

}
