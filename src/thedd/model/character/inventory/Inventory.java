package thedd.model.character.inventory;

import java.util.List;

import model.item.Item;

/**
 * Interface that manage a collection of items.
 */
public interface Inventory {

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
     * Returns a list of character inventory's items.
     * 
     * @return a List of items.
     */
    List<Item> getAll();

    /**
     * Return the quantity of the specified Item.
     * 
     * @param item the Item
     * @return the quantity in the inventory of this Item
     */
    int getQuantity(Item item);
}
