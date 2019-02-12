package model.item;

import java.util.Map;
import java.util.stream.Collectors;

import model.characters.Statistic;

/**
 * Abstract class that defines the methods which tell whether a item is of a
 * type or not.
 *
 */
public abstract class AbstractItem implements Item {

    private final int id;
    private final String name;
    private Map<Statistic, Integer> effects;

    /**
     * 
     * @param id
     *          id of the object, used only for comparison and hashing purpose
     * @param name
     *          name of the object
     */
    public AbstractItem(final int id, final String name, final Map<Statistic, Integer> effects) {
        this.id = id;
        this.name = name;
        this.effects = effects; 
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final boolean isEquipable() {
        return this instanceof EquipableItem;
    }

    @Override
    public final boolean isUsable() {
        return this instanceof UsableItem;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractItem other = (AbstractItem) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    /**
     * @return
     *          the String representation of this object
     */
    public final String toString() {
        return this.name + ": " + effects.entrySet().stream().map(e -> e.getKey() + " -> " + e.getValue()).collect(Collectors.joining(", ", "[", "]"));
    }

}
