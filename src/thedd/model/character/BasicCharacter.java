package thedd.model.character;

import java.util.EnumMap;
import java.util.List;

import thedd.model.character.inventory.Inventory;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.Statistic;
import thedd.model.combat.actor.automatic.AutomaticActionActor;
import thedd.model.item.Item;
import thedd.model.item.equipableitem.EquipableItem;

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
     * This method returns character's values of a specified statistic.
     * 
     * @param stat specifies the statistic.
     * @return a StatValues that contains the actual/max values of the specified.
     *         statistic.
     * @throws NullPointerException if parameter is null.
     */
    StatValues getStat(Statistic stat);

    /**
     * This method returns all character's statistics.
     * 
     * @return an EnumMap that contains character's statistics values.
     */
    EnumMap<Statistic, StatValues> getAllStat();

    /**
     * This method return character's inventory.
     * 
     * @return the character's inventory
     */
    Inventory getInventory();

    /**
     * This method equip the specified item.
     * 
     * @param item the item to be equipped.
     * @return true if the item is successfully equipped, otherwise false.
     * @throws NullPointerException if parameter is null.
     */
    boolean equipItem(Item item);

    /**
     * This method remove the item from equipments.
     * 
     * @param item the item to be removed.
     * @return true if the item is successfully removed, otherwise false.
     * @throws NullPointerException if parameter is null.
     */
    boolean unequipItem(Item item);

    /**
     * This method returns a the list of character's equipments.
     * 
     * @return a list of EquipableItem
     */
    List<EquipableItem> getEquippedItems();

    /**
     * This method returns true if the specified EquipableItem is equipable on
     * character's equipments.
     * 
     * @param item the specified item
     * @return true if is equipable, otherwise false.
     */
    boolean isItemEquipableOnEquipment(EquipableItem item);
}
