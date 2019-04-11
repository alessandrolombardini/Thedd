package thedd.model.character;

import java.util.EnumMap;
import java.util.List;

import thedd.model.character.inventory.Inventory;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.Statistic;
import thedd.model.combat.actor.automatic.AutomaticActionActor;
import thedd.model.item.Item;

/**
 * Interface that define the characters.
 */
public interface BasicCharacter extends AutomaticActionActor {

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
     * @return a StatValues that contains the actual/max values of the specified.
     *         statistic.
     * @throws NullPointerException if stat parameter is null.
     */
    StatValues getStat(Statistic stat);

    /**
     * This method returns all the character's status.
     * 
     * @return an EnumMap that contains the values of the statistic.
     */
    EnumMap<Statistic, StatValues> getAllStat();

    /**
     * This method return the character's inventory.
     * 
     * @return the character's inventory
     */
    Inventory getInventory();

    /**
     * This method equip the specified item.
     * 
     * @param item the item to be equipped.
     * @return true if the item is successfully equipped, otherwise false.
     * @throws NullPointerException if item parameter is null.
     */
    boolean equipItem(Item item);

    /**
     * This method remove the item from the equipped ones.
     * 
     * @param item the item to be removed.
     * @return true if the item is successfully unequipped, otherwise false.
     * @throws NullPointerException if item parameter is null.
     */
    boolean unequipItem(Item item);

    /**
     * This method returns a list with all the equipped items.
     * 
     * @return a list of items
     */
    List<? extends Item> getEquippedItems();
}
