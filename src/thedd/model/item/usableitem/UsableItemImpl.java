package thedd.model.item.usableitem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.item.AbstractItem;
import thedd.model.item.ItemRarity;
import thedd.model.item.ItemRarityImpl;

/**
 * Implementation of {@link thedd.model.item.usableitem.UsableItem}.
 *
 */
public class UsableItemImpl extends AbstractItem implements UsableItem {

    private static final Map<ItemRarity, Integer> EFFECT_MULTIPLIERS;
    private final boolean usableInCombat;
    private final boolean usableOutOfCombat;
    private Optional<Action> action = Optional.empty();

    static {
        EFFECT_MULTIPLIERS = new HashMap<>();
        EFFECT_MULTIPLIERS.put(ItemRarityImpl.COMMON, 1);
        EFFECT_MULTIPLIERS.put(ItemRarityImpl.UNCOMMON, 2);
        EFFECT_MULTIPLIERS.put(ItemRarityImpl.RARE, 4);
    }

    /**
     * Create a new UsableItem
     * with the given {@link thedd.model.item.ItemRarity}.
     * On creation the action is empty, effects must be added after
     * the construction and with multipliers already applied.
     * @param id
     *  id of the object
     * @param name
     *  name of the item
     * @param rarity
     *  rarity of the item
     * @param description
     *  description of the Item
     * @param usableInCombat
     *  whether the item is usable in a combat 
     * @param usableOutOfCombat
     *  whether the item is usable outside a combat
     */
    public UsableItemImpl(final int id, final String name,
                          final ItemRarity rarity, final String description,
                          final boolean usableInCombat, final boolean usableOutOfCombat) {
        super(id, name, rarity, description);
        this.usableInCombat = usableInCombat;
        this.usableOutOfCombat = usableOutOfCombat;
    }

    @Override
    public final Action getAction() {
        if (!action.isPresent()) {
            action = Optional.of(buildAction());
        }
        return action.get().getCopy();
    }

    @Override
    public final String toString() {
        return this.getName() + ": " + this.getEffectDescription() + " | " + this.getDescription();
    }

    @Override
    public final void addActionEffect(final ActionEffect effect) {
        if (!action.isPresent()) {
            action = Optional.of(buildAction());
        }
        action.get().addEffect(Objects.requireNonNull(effect));
    }

    @Override
    public final Map<ItemRarity, Integer> getEffectsMultiplier() {
        return Collections.unmodifiableMap(EFFECT_MULTIPLIERS);
    }

    @Override
    public final String getEffectDescription() {
        if (!action.isPresent()) {
            action = Optional.of(buildAction());
        }
        return action.get().getEffects().stream().map(e -> e.getDescription()).collect(Collectors.joining("\n"));
    }

    @Override
    public final boolean equals(final Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof UsableItem)) {
            return false;
        }
        final UsableItem other = (UsableItem) obj;
        return other.getAction().equals(getAction());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(super.hashCode(), action);
    }

    @Override
    public final boolean isUsableInCombat() {
        return usableInCombat;
    }

    @Override
    public final boolean isUsableOutOfCombat() {
        return usableOutOfCombat;
    }

    /**
     * Builds the action executed when using the item.
     * @return the action linked to the item
     */
    protected Action buildAction() {
        return new ActionBuilder().setName("[Action uninitialized]")
                                  .setDescription("[Action uninitialized]")
                                  .build();
    }

}
