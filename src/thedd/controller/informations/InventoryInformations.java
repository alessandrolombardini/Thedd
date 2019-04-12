package thedd.controller.informations;

import java.util.List;
import thedd.model.item.Item;

/**
 * This class represent an informations wrapper for the player character. This
 * class contains informations about the player character and inventory
 * view-controller will ask from this class all the required informations.
 */
public interface InventoryInformations {

    /**
     * Returns the quantity of the specified item on the player's inventory.
     * 
     * @param item the specified item
     * @return the quantity
     */
    String getInventoryItemQuantity(Item item);

    /**
     * Returns a list of all the Items in player's inventory.
     * 
     * @return a list of Item
     */
    List<Item> getAllItemsList();

    /**
     * This method returns true if the item is equipped, otherwise false.
     * 
     * @param item the item searched into equipped list.
     * @return a boolean value.
     */
    boolean isEquipped(Item item);
}
