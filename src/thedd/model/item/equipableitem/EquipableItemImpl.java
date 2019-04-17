package thedd.model.item.equipableitem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.action.effect.RemovableEffect;
import thedd.model.combat.actor.ActionActor;
import thedd.model.item.AbstractItem;
import thedd.model.item.ItemRarity;
import thedd.model.item.ItemRarityImpl;

/**
 * Implementation of {@link thedd.model.item.equipableitem.EquipableItem}.
 * This implementation has 1 innate {@link thedd.model.combat.modifier.Modifier}.
 */
public class EquipableItemImpl extends AbstractItem implements EquipableItem {

    private static final Map<ItemRarity, Pair<Integer, Integer>> RARITY_MODIFIERS;
    private static final int NUM_OF_INNATE_MODIFIERS = 1;

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
     * Create a new {@link thedd.model.item.equipableitem.EquipableItem} with the given rarity.
     * Any innate modifier is added after the construction.
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
        return this.getName() 
               + "(" + this.type + ")" 
               + "{" + this.getRarity() + "}" + ": " 
               + providedEffects.stream().map(e -> e.getDescription()).collect(Collectors.joining(", ", "[", "]")) + " | " 
               + (additionalActions.isEmpty() ? "" : "Provides " + additionalActions.stream().map(a -> a.getName()).collect(Collectors.joining("; "))) + " | "
               + this.getDescription();
    }

    @Override
    public final void onEquip(final ActionActor equipper) {
        Objects.requireNonNull(equipper);
        additionalActions.forEach(equipper::addActionToAvailable);
        providedEffects.forEach(e -> e.apply(equipper));
    }

    @Override
    public final void onUnequip(final ActionActor equipper) {
        Objects.requireNonNull(equipper);
        additionalActions.forEach(equipper::removeActionFromAvailable);
        providedEffects.stream().filter(e -> e instanceof RemovableEffect).forEach(e -> ((RemovableEffect) e).remove());
    }

    @Override
    public final Map<ItemRarity, Pair<Integer, Integer>> getRarityModifiers() {
        return Collections.unmodifiableMap(RARITY_MODIFIERS);
    }

    @Override
    public final String getEffectDescription() {
        return providedEffects.stream().map(e -> e.getDescription()).collect(Collectors.joining("\n")) 
               + additionalActions.stream().map(a -> "Provide action " + a.getName()).collect(Collectors.joining("\n"));
    }

    @Override
    public final void addActionEffect(final ActionEffect newEffect) {
        final int maxNumOfEffects = this.getRarityModifiers().get(this.getRarity()).getLeft() + NUM_OF_INNATE_MODIFIERS;
        if (providedEffects.size() < maxNumOfEffects) {
            providedEffects.add(Objects.requireNonNull(newEffect));
        } else {
            throw new IllegalStateException("There is no more room for additional effects");
        }
    }

    @Override
    public final void addAdditionalAction(final Action newAction) {
        if (additionalActions.size() < this.getRarityModifiers().get(this.getRarity()).getRight()) {
            additionalActions.add(Objects.requireNonNull(newAction));
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

    @Override
    public final int hashCode() {
        return Objects.hash(super.hashCode(), additionalActions, providedEffects, type);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        final EquipableItemImpl other = (EquipableItemImpl) obj;
        if (this.type != other.type) {
            return false;
        }
        return this.additionalActions.containsAll(other.additionalActions)
               && this.providedEffects.containsAll(other.providedEffects);
    }

    @Override
    public final int getNumOfInnateModifiers() {
        return NUM_OF_INNATE_MODIFIERS;
    }

}
