package model.item;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import model.character.Statistic;

/**
 * Implementation of {@link model.item.EquipableItem}.
 *
 */
public class EquipableItemImpl extends AbstractItem implements EquipableItem {

    private final EquipableItemType type;

    /**
     * 
     * @param id
     *          id of the item
     * @param name
     *          name of the item
     * @param t
     *          type of the object
     * @param effects
     *          map of modifiers of the equipable item
     * @param description
     *          description of the Item
     */
    public EquipableItemImpl(final int id, final String name, final EquipableItemType t, final Map<Statistic, Integer> effects, final String description) {
        super(id, name, effects, description);
        this.type = Objects.requireNonNull(t);
    }

    @Override
    public final Map<Statistic, Integer> getModifiers() {
        return Collections.unmodifiableMap(this.getEffects());
    }

    @Override
    public final EquipableItemType getType() {
        return this.type;
    }

    @Override
    public final Item copy() {
        return new EquipableItemImpl(this.getId(), this.getName(), this.type, this.getEffects(), this.getDescription());
    }

    @Override
    public final String toString() {
        return this.getName() + "(" + this.type + ")" + ": " + this.getEffects().entrySet().stream().map(e -> e.getKey() + " -> " + e.getValue()).collect(Collectors.joining(", ", "[", "]")) + " | " + this.getDescription();
    }

}
