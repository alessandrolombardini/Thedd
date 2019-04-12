package thedd.model.item.equipableitem;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.actor.ActionActor;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;

/**
 * Specialization of Item which can be equipped but cannot be used.
 * It can have innate modifiers which doesn't depend from the rarity of the item.
 */
public interface EquipableItem extends Item {

    /**
     * Return the {@link thedd.model.item.equipableitem.EquipableItemType}.
     * @return
     *  the type of the item
     */
    EquipableItemType getType();

    /**
     *  This method has to be called every time one equips this item, 
     *  as the item will apply all effects it has to the target.
     * @param equipper
     *  the character who has equipped the item.
     */
    void onEquip(ActionActor equipper);

    /**
     * This method has to be called every time one unequips this item,
     * as the item will remove all effects it had applied to the target.
     * @param equipper
     *  the character who has unequipped the item.
     */
    void onUnequip(ActionActor equipper);

    /**
     * Returns the map of pair key-value where the key is a value of {@link thedd.model.item.ItemRarity} and
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
     * If an item has an innate ActionEffect, it should add it after his construction.
     * @throws IllegalStateException whether the number of modifiers would exceed 
     *          the maximum value defined by the map got from {@link #getRarityModifiers()} for the rarity of the object 
     *          plus the number of innate modifiers
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

    /**
     * Return the number of innate {@link thedd.model.combat.modifier.Modifier}s.
     * @return
     *  the number of innate modifiers
     */
    int getNumOfInnateModifiers();
}
