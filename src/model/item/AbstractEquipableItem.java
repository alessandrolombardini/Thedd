package model.item;

import java.util.HashMap;
import java.util.Map;

import model.characters.Statistic;

/**
 * 
 *
 */
public abstract class AbstractEquipableItem extends AbstractItem implements EquipableItem {

    private final Map<Statistic, Integer> modifiers;

    /**
     * 
     * @param id
     *          id of the item
     * @param name
     *          name of the item
     */
    public AbstractEquipableItem(final int id, final String name) {
        super(id, name);
        modifiers = new HashMap<>();
    }

    @Override
    public final Map<Statistic, Integer> getModifiers() {
        return new HashMap<>(modifiers);
    }
    /**
     * 
     * @param stat
     *          Statistic modified
     * @param value
     *          Magnitude of the modifier
     * @return
     *          self
     */
    protected final EquipableItem addModifier(final Statistic stat, final int value) {
        if (modifiers.containsKey(stat)) {
            throw new IllegalArgumentException("A modifier affecting this Statistic already exists");
        } else {
            modifiers.put(stat, value);
            return this;
        }
    }

}
