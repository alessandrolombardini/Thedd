package thedd.model.character.inventory;

import java.util.List;

import thedd.model.item.Item;

/**
 * Interface that manage an inventory of items.
 */
public interface Inventory {

    /**
     * This method add the passed item in the Inventory.
     * 
     * @param item the item to add.
     * @throws NullPointerException if item is null.
     */
    void addItem(Item item);

    /**
     * This method remove the specified item from the Inventory.
     * 
     * @param item theItem that is going to be removed.
     * @throws NullPointerException if item is null.
     * @throws IllegalStateException if item isn't in the inventory.
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
     * @return the quantity in the inventory of this Item. if the item isn't in the
     *         inventory then the method returns 0.
     * @throws NullPointerException if item is null.
     */
    int getQuantity(Item item);
}
