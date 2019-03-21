package model.item.equipableitem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import model.combat.action.Action;
import model.combat.action.effect.ActionEffect;
import model.combat.actor.ActionActor;
import model.item.AbstractItem;
import model.item.ItemRarity;
import model.item.ItemRarityImpl;
import model.item.StatisticBonusEffect;

/**
 * Implementation of {@link model.item.equipableitem.EquipableItem}.
 *
 */
public class EquipableItemImpl extends AbstractItem implements EquipableItem {

    private static final Map<ItemRarity, Pair<Integer, Integer>> RARITY_MODIFIERS;

    static {
        RARITY_MODIFIERS = new HashMap<>();
        RARITY_MODIFIERS.put(ItemRarityImpl.COMMON, new ImmutablePair<Integer, Integer>(0, 0));
        RARITY_MODIFIERS.put(ItemRarityImpl.UNCOMMON, new ImmutablePair<Integer, Integer>(2, 0));
        RARITY_MODIFIERS.put(ItemRarityImpl.RARE, new ImmutablePair<Integer, Integer>(4, 1));
    }

    private final List<ActionEffect> providedEffects;
    private final List<Action> additionalActions;


    private final EquipableItemType type;

    /**
     * 
     * @param id
     *          id of the item
     * @param name
     *          name of the item
     * @param t
     *          type of the object
     * @param rarity
     *          rarity of the equipable item
     * @param description
     *          description of the Item
     */
    public EquipableItemImpl(final int id, final String name, final EquipableItemType t, final ItemRarity rarity, final String description) {
        super(id, name, rarity, description);
        this.type = Objects.requireNonNull(t);
        providedEffects = new ArrayList<>();
        additionalActions = new ArrayList<>();
    }

    @Override
    public final EquipableItemType getType() {
        return this.type;
    }

    @Override
    public final String toString() {
        return this.getName() + "(" + this.type + ")" + "{" + this.getRarity() + "}" + ": " + providedEffects.stream().map(e -> e.getDescription()).collect(Collectors.joining(", ", "[", "]")) + " | " + this.getDescription();
    }

    @Override
    public final void onEquip(final ActionActor equipper) {
        additionalActions.forEach(equipper::addAction);
        providedEffects.forEach(e -> e.apply(equipper));
    }

    @Override
    public final void onUnequip(final ActionActor equipper) {
        additionalActions.forEach(equipper::removeAction);
        providedEffects.stream().filter(e -> e instanceof StatisticBonusEffect).forEach(e -> ((StatisticBonusEffect) e).removeBonus());
    }

    @Override
    public final Map<ItemRarity, Pair<Integer, Integer>> getRarityModifiers() {
        return Collections.unmodifiableMap(RARITY_MODIFIERS);
    }

    @Override
    public final String getEffectDescription() {
        return null;
    }

    @Override
    public final void addActionEffect(final ActionEffect newEffect) {
        final int maxNumOfEffects = this.getRarityModifiers().get(this.getRarity()).getLeft()
                                    + (this.getType().isWeapon() ? 1 : 0);
        if (providedEffects.size() < maxNumOfEffects) {
            providedEffects.add(newEffect);
        } else {
            throw new IllegalStateException("There is no more room for additional effects");
        }
    }

    @Override
    public final void addAdditionalAction(final Action newAction) {
        if (additionalActions.size() < this.getRarityModifiers().get(this.getRarity()).getRight()) {
            additionalActions.add(newAction);
        } else {
            throw new IllegalStateException("There is no more room for additional actions");
        }
    }

    @Override
    public final List<ActionEffect> getActionEffects() {
        return Collections.unmodifiableList(providedEffects);
    }

    @Override
    public final List<Action> getAdditionalActions() {
        return Collections.unmodifiableList(additionalActions);
    }

}
