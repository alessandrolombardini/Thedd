package model.character;

import java.util.Optional;

import model.item.EquipableItem;
import model.item.Item;
import model.item.UsableItem;

/**
 * Interface that manage a collection of items.
 */
public interface Inventory {

    /**
     * This method returns the specified equipable item from the Inventory.
     * 
     * @param id the id of the object
     * @return the equipable item or null if is not present or is not equipable
     */
    Optional<EquipableItem> getEquipableItem(int id);

    /**
     * This method returns the specified usable item of the Inventory.
     * 
     * @param id the id of the object
     * @return the usable item or null if is not present or is not usable
     */
    Optional<UsableItem> getUsableItem(int id);

    /**
     * This method add the passed item in the Inventory.
     * 
     * @param item the item to add
     */
    void addItem(Item item);

    /**
     * This method remove the specified id item from the Inventory.
     * 
     * @param id the id of the Item that is going to be removed
     */
    void removeItem(int id);

     /**
     * This method remove the specified item from the Inventory.
     * 
     * @param item theItem that is going to be removed
     */
    void removeItem(Item item);
}
