package model.item;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import model.characters.Statistic;

/**
 * 
 *
 */
public class EquipableItemImpl extends AbstractItem implements EquipableItem {

    //private final Map<Statistic, Integer> modifiers;

    /**
     * 
     * @param id
     *          id of the item
     * @param name
     *          name of the item
     * @param effects
     *          map of modifiers of the equipable item
     */
    public EquipableItemImpl(final int id, final String name, final Map<Statistic, Integer> effects) {
        super(id, name, effects);
    }

    @Override
    public final Map<Statistic, Integer> getModifiers() {
        return new HashMap<>(this.getEffects());
    }

    @Override
    public final Item copy() {
        return new EquipableItemImpl(this.getId(), this.getName(), this.getEffects()) { };
    }

    @Override
    public final String toString() {
        return this.getName() + ": " + this.getEffects().entrySet().stream().map(e -> e.getKey() + " -> " + e.getValue()).collect(Collectors.joining(", ", "[", "]"));
    }

}
