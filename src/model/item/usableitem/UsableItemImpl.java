package model.item.usableitem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import model.combat.action.Action;
import model.combat.action.ActionImpl;
import model.combat.action.TargetType;
import model.combat.action.effect.ActionEffect;
import model.item.AbstractItem;
import model.item.ItemRarity;
import model.item.ItemRarityImpl;

/**
 * Implementation of {@link model.item.usableitem.UsableItem}.
 *
 */
public class UsableItemImpl extends AbstractItem implements UsableItem {

    private static final Map<ItemRarity, Integer> EFFECT_MULTIPLIERS;
    private final Action action;

    static {
        EFFECT_MULTIPLIERS = new HashMap<>();
        EFFECT_MULTIPLIERS.put(ItemRarityImpl.COMMON, 1);
        EFFECT_MULTIPLIERS.put(ItemRarityImpl.UNCOMMON, 2);
        EFFECT_MULTIPLIERS.put(ItemRarityImpl.RARE, 4);
    }

    /**
     * 
     * @param id
     *  id of the object
     * @param name
     *  name of the item
     * @param rarity
     *  rarity of the item
     * @param description
     *  description of the Item
     */
    public UsableItemImpl(final int id, final String name, final ItemRarity rarity, final String description) {
        super(id, name, rarity, description);
        action = new ActionImpl(null, this.getName(), new ArrayList<>(), 1, TargetType.EVERYONE, this.getDescription(), " used " + this.getName());
    }

    @Override
    public final Action getAction() {
        return action;
    }

    @Override
    public final String toString() {
        return this.getName() + ": " + this.getEffectDescription() + " | " + this.getDescription();
    }

    @Override
    public final void addActionEffect(final ActionEffect effect) {
        action.addEffect(effect);
    }

    @Override
    public final Map<ItemRarity, Integer> getEffectsMultiplier() {
        return Collections.unmodifiableMap(EFFECT_MULTIPLIERS);
    }

    @Override
    public final String getEffectDescription() {
        return action.getEffects().stream().map(e -> e.getDescription()).collect(Collectors.joining("\n"));
    }

}
