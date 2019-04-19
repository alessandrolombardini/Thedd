package thedd.controller.information;

import java.util.List;
import java.util.Optional;

import thedd.model.combat.action.Action;
import thedd.model.item.Item;

/**
 * This class represent an informations wrapper for the player character. This
 * class contains informations about the player character and inventory
 * view-controller will ask from this class all the required informations.
 */
public interface PlayerInformation {

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

    /**
     * This method save the current used item before applying its effects.
     * 
     * @param item an Item.
     */
    void setUsedItem(Item item);

    /**
     * This method get the current used item before applying its effects.
     * 
     * @return an Optional<Item>.
     */
    Optional<Item> getUsedItem();

    /**
     * This method resets the current used item before applying its effecs.
     */
    void resetUsedItem();

    /**
     * This method returns a list of player's available actions.
     * 
     * @return a List<Action>
     */
    List<Action> getPlayerActions();
}
