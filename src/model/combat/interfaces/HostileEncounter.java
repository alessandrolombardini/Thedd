package model.combat.interfaces;

import java.util.List;

/**
 * A container holding information about the combat that has to start.
 * <p>
 * Used for generating combat encounters, it holds a combat logic entity,
 * and a collection of the enemies encountered in this combat by the player
 *
 */
public interface HostileEncounter {

    /**
     * Sets the combat logic of this encounter.
     * @param combatLogic the combat logic to be used
     */
    void setCombatLogic(CombatLogic combatLogic);

    /**
     * Gets the logic of the combat that will start.
     * If the logic is not set, a NoSuchElementException is thrown.
     * @return the combat logic
     * 
     * @throws NoSuchElementException
     */
    CombatLogic getCombatLogic();

    /**
     * Adds an actor to the party opposed to the player.
     * @param character the actor to be added
     */
    void addNPC(ActionActor character);

    /**
     * Adds one or more actors to the party oppose to the player.
     * @param characters the actors to be added
     */
    void addAll(List<ActionActor> characters);

    /*Note: might be extend with additional methods: it's highly
      probable that the encounter will need to also provide a
      messageManager/Logic */
}
