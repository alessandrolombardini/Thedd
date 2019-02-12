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
     * @param effects
     *          map of modifiers of the equipable item
     */
    public AbstractEquipableItem(final int id, final String name, final Map<Statistic, Integer> effects) {
        super(id, name, effects);
        modifiers = new HashMap<>();
    }

    @Override
    public final Map<Statistic, Integer> getModifiers() {
        return new HashMap<>(modifiers);
    }

}
