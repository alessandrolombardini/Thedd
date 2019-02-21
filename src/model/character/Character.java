package model.character;

import java.util.List;
import model.item.Item;

/**
 * Interface that define the characters.
 */
public interface Character {

    /**
     * This method modify the actual status of the current character.
     * 
     * @param stat  the statistic to update.
     * @param value the value updated to the specified statistic.
     */
    void updateStat(Statistic stat, int value);

    /**
     * This method allows to know if the character is alive.
     * 
     * @return true if current character is alive, otherwise false.
     */
    boolean isAlive();

    /**
     * This method returns the character's status of a specified statistic.
     * 
     * @param stat specifies the statistic.
     * 
     * @return a StatValues that contains the actual/max values of the specified
     *         statistic.
     */
    StatValues getStat(Statistic stat);

    /**
     * This method return the character's inventory.
     * 
     * @return the character's inventory
     */
    Inventory getInventory();

    /**
     * This method equip the specified item, updating character's statistics.
     * 
     * @param itemid the id of the item to be equipped.
     */
    void equipItem(int itemid);

    /**
     * This method remove the specified item to the equipped ones and automatically
     * update character's statistics.
     * 
     * @param itemId the id of the item to be removed.
     */
    void removeItem(int itemId);

    /**
     * This method returns a list with all the equipped items.
     * 
     * @return a list of items
     */
    List<? extends Item> getEquippedItems();
}
