package model.item.equipableitem;

import model.combat.actor.ActionActor;
import model.item.Item;

/**
 * Specialization of Item which can be equipped but cannot be used.
 *
 */
public interface EquipableItem extends Item {

    /**
     * 
     * @return
     *  the type of the item
     */
    EquipableItemType getType();

    /**
     *  This method has to be called every time one equip an item, as the item will update the character statistics.
     * @param equipper
     *  the character who has equipped the item.
     */
    void onEquip(ActionActor equipper);

    /**
     * This method has to be called every time one unequip an item, as the item will update the character statistics.
     * @param equipper
     *  the character who has unequipped the item.
     */
    void onUnequip(ActionActor equipper);
}
