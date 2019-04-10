package thedd.model.item.usableitem;

import java.util.Map;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;

/**
 * Specialized interface of {@link thedd.model.item.Item} of usable items.
 * 
 *
 */
public interface UsableItem extends Item {
    /**
     * Return the {@link thedd.model.combat.action.Action} of the item.
     * The target is the user of the item.
     * @return the action to apply to a target
     */
    Action getAction();

    /**
     * Adds a new {@link thedd.model.combat.action..effectActionEffect}
     * to the {@link thedd.model.combat.action.Action} of the item.
     * @param effect
     *  the effect to add to the item action
     */
    void addActionEffect(ActionEffect effect);

    /**
     * Returns the map of pair key-value where the key is a value of {@link thedd.model.item.ItemRarity}
     * and the value is a numeric multiplier applied to the value of 
     * the {@link thedd.model.combat.action.effect.ActionEffect}s of 
     * the {@link thedd.model.combat.action.Action}.
     * 
     * @return
     *  the map of the effect multipliers based on the rarity
     */
    Map<ItemRarity, Integer> getEffectsMultiplier();

    /**
     * Return whether the item can be used during a combat.
     * @return
     *  whether the item can be used in a combat
     */
    boolean isUsableInCombat();

    /**
     * Returns whether the item can be used out of combat.
     * @return
     *  whether the item can be used out of combat
     */
    boolean isUsableOutOfCombat();

}
