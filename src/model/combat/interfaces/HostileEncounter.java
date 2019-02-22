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
     * The logic of the combat that will start.
     * @return the combat logic
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
	
	//Note: might be better to directly provide a combat instance
	//or just the already set up Logic
	
	/*Note2: might be extend with additional methods: it's highly
	  probable that the encounter will need to also provide a
	  messageManager/Logic */
}
