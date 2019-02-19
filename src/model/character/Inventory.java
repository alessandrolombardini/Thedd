package model.character;

import model.item.Item;

/**
 * Interface that manage a collection of items.
 */
public interface Inventory {

    /**
     * This method returns the specified item of the Inventory.
     * 
     * @param position the position in the inventory of the object
     * @return the specified item
     */
    Item getItem(int position);

    /**
     * This method add the passed item in the Inventory.
     * @param item the item to add
     */
    void addItem(Item item);

}
