package thedd.model.character.inventory;

import java.util.List;
import java.util.Optional;

import model.item.Item;

/**
 * Interface that manage a collection of items.
 */
public interface Inventory {

    /**
     * This method returns the specified item of the Inventory.
     * 
     * @param id the id of the object
     * @return the item or null if is not present
     */
    Optional<Item> getItem(int id);

    /**
     * This method add the passed item in the Inventory.
     * 
     * @param item the item to add
     */
    void addItem(Item item);

    /**
     * This method remove the specified item from the Inventory.
     * 
     * @param item theItem that is going to be removed
     */
    void removeItem(Item item);

    /**
     * Returns an unmodifiable list of all the items of character's inventory.
     * 
     * @return a List of items.
     */
    List<Item> getAll();

    /**
     * Return the quantity of the specified Item.
     * 
     * @param item the item
     * @return the quantity of this Item
     */
    int getQuantity(Item item);
}
