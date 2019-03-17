package model.item.usableitem;

import java.util.Map;

import model.combat.action.Action;
import model.combat.action.effect.ActionEffect;
import model.item.Item;
import model.item.ItemRarity;

/**
 * Specialized interface of {@link model.item.Item} of usable items.
 *
 */
public interface UsableItem extends Item {
    /**
     * 
     * @return the action to apply to a target
     */
    Action getAction();

    /**
     * 
     * @param effect
     *  the effect to add to the item action
     */
    void addActionEffect(ActionEffect effect);

    /**
     * 
     * @return
     *  the map of the effect multipliers based on the rarity
     */
    Map<ItemRarity, Integer> getEffectsMultiplier();

}
