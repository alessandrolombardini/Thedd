package model.character;

import java.util.EnumMap;
import java.util.List;

import model.combat.interfaces.AutomaticActionActor;
import model.item.Item;

/**
 * Interface that define the characters.
 */
public interface BasicCharacter extends AutomaticActionActor {

    /**
     * This method sets the default statistics of the character.
     * 
     * @param basicStat an EnumMap composed by the statistics' values.
     */
    void setBasicStat(EnumMap<Statistic, StatValues> basicStat);

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
     * This method equip the specified item.
     * 
     * @param itemid the id of the item to be equipped.
     * @return true if the item is correctly equipped, otherwise false.
     */
    boolean equipItem(int itemid);

    /**
     * This method remove the specified item to the equipped ones.
     * 
     * @param itemId the id of the item to be removed.
     */
    void unequipItem(int itemId);

    /**
     * This method returns a list with all the equipped items.
     * 
     * @return a list of items
     */
    List<? extends Item> getEquippedItems();

    /**
     * String representation of the Character.
     * 
     * @return the String that describes the character.
     */
    String getLog();
}
