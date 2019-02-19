package model.character;

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
     * @return the specified item or null if is not present
     */
    Optional<Item> getItem(int id);

    /**
     * This method add the passed item in the Inventory.
     * @param item the item to add
     */
    void addItem(Item item);

}
