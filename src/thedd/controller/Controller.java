package thedd.controller;

import thedd.controller.informations.InventoryInformations;
import thedd.controller.informations.StatisticsInformations;
import thedd.model.combat.action.Action;
import thedd.model.combat.actor.ActionActor;
import thedd.model.item.Item;

/**
 * Interface describing the controller of the pattern MVC of this application.
 */
public interface Controller {

    /**
     * Start a new game session from a given informations.
     * 
     * @param playerName     Name of the player
     * @param numberOfRooms  Number of rooms of each floor
     * @param numberOfFloors Number of floor of game world
     * @return true if values are valid to build a new world
     */
    boolean newGame(String playerName, String numberOfRooms, String numberOfFloors);

    /**
     * Close application.
     */
    void closeApplication();

    /**
     * This method returns true if the combat is active, otherwise false.
     * 
     * @return a boolean
     */
    boolean isCombatActive();

    /**
     * This method delete from player's Inventory the specified item.
     * 
     * @param item the item specified.
     */
    void deleteItem(Item item);

    /**
     * This method use the specified item and delete it from the inventory.
     * 
     * @param item the item specified.
     */
    void useItem(Item item);

    /**
     * This method equip the specified item.
     * 
     * @param item the specified item.
     */
    void equipItem(Item item);

    /**
     * This method unequip the specified item.
     * 
     * @param item the specified item.
     */
    void unequipItem(Item item);

    /**
     * The method returns the Inventory Information's wrapper.
     * 
     * @return an InventoryInformation class.
     */
    InventoryInformations getInventoryInformations();

    /**
     * The method returns the Statistics Information's wrapper.
     * 
     * @return an StatisticsInformation class.
     */
    StatisticsInformations getStatisticsInformations();

    /**
     * Resets the player's selected action and prompts the view
     * to let the player choose another action.
     */
    void undoActionSelection();

    /**
     * Sets the provided actor as the target of the player's
     * current action and, if the round is ready, evaluates
     * that action.
     * @param target the target to assign to the current action
     */
    void targetSelected(ActionActor target);

    /**
     * Sets the {@link ActionExecutor} as a {@link OutOfCombatActionExecutor}
     * and starts it passing the provided action.
     * @param action the action to execute
     */
    void executeSingleAction(Action action);

    /**
     * Execute the last evaluated action by the {@link ActionExecutor}.
     */
    void executeCurrentAction();

    /**
     * Updates the execution status and the game according to it.
     */
    void evaluateExecutionState();

    /**
     * Sets the provided action as the player's selected action and prompts
     * the view to let the player choose a target.
     * @param action the selected action
     */
    void selectAction(Action action);
}
