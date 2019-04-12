package thedd.controller;

import thedd.controller.informations.InventoryInformations;
import thedd.controller.informations.StatisticsInformations;
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
}
