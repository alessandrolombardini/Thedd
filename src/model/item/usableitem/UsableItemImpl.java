package model.item.usableitem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
    private final List<ActionEffect> effects;

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
        effects = new ArrayList<>();
    }

    @Override
    public final Action getAction() {
        return new ActionImpl(null, this.getName(), effects, 1, TargetType.EVERYONE, this.getDescription(), " used " + this.getName());
    }

    @Override
    public final String toString() {
        return this.getName() + ": " + effects.stream().map(e -> e.getDescription()).collect(Collectors.joining(", ", "[", "]")) + " | " + this.getDescription();
    }

    @Override
    public final void addActionEffect(final ActionEffect effect) {
        effects.add(effect);
    }

    @Override
    public final Map<ItemRarity, Integer> getEffectsMultiplier() {
        return Collections.unmodifiableMap(EFFECT_MULTIPLIERS);
    }

}
