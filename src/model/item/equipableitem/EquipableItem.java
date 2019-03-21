package model.item.equipableitem;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import model.combat.action.Action;
import model.combat.action.effect.ActionEffect;
import model.combat.actor.ActionActor;
import model.item.Item;
import model.item.ItemRarity;

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
     * This method has to be called every time one unequip an item,
     * as the item will update the character statistics.
     * @param equipper
     *  the character who has unequipped the item.
     */
    void onUnequip(ActionActor equipper);

    /**
     * Returns the map of pair key-value where the key is a value of {@link model.item.ItemRarity} and
     * the value is represented by a {@link org.apache.commons.lang3.tuple.Pair} in which
     * the left value is the number of {@link model.combat.modifier.Modifier}s granted by the item when equipped and the right value is the number
     * of additional {@link model.combat.action.Action}s granted by the item when equipped.
     * 
     * @return
     *          the map rarity of every equipable item
     */
    Map<ItemRarity, Pair<Integer, Integer>> getRarityModifiers();

    /**
     * Add a new {@link model.combat.action.effect.ActionEffect} to the item.
     * @throws IllegalStateException whether the number of modifiers would exceed 
     *          the maximum value defined by the map got from {@link #getRarityModifiers()} for the rarity of the object 
     * 
     * @param newEffect
     *          the new effect to add to the item provided effects.
     */
    void addActionEffect(ActionEffect newEffect);

    /**
     * Add a new {@link model.combat.action.Action} to the item.
     * @throws IllegalStateException if the number of additional action would exceed
     * @param newAction
     *          the new action to add to the item
     */
    void addAdditionalAction(Action newAction);

    /**
     * Return the list of the {@link model.combat.action.effect.ActionEffect}s granted by the item.
     * @return the list of {@link model.combat.action.effect.ActionEffect}s
     */
    List<ActionEffect> getActionEffects();

    /**
     * Return the list of additional {@link model.combat.action.Action}s granted by this item.
     * @return the list of additional {@link model.combat.action.Action}s
     */
    List<Action> getAdditionalActions();
}
