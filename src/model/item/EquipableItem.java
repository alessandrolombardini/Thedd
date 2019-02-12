package model.item;

import java.util.Map;

import model.characters.Statistic;

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
}
