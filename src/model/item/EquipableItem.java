package model.item;

import java.util.Map;

import model.character.Statistic;

/**
 * 
 *
 */
public interface EquipableItem extends Item {
    /**
     * 
     * @return
     *  the map containing the statistics and the modifier granted by the item
     */
    Map<Statistic, Integer> getModifiers();

    /**
     * 
     * @return
     *  the type of the item
     */
    EquipableItemType getType();
}
