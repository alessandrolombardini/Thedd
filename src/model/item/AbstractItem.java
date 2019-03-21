package model.item;

import java.util.Objects;

import model.item.equipableitem.EquipableItem;
import model.item.usableitem.UsableItem;

/**
 * Abstract class that defines the methods which tell whether a item is of a
 * type or not.
 *
 */
public abstract class AbstractItem implements Item {

    private final int id;
    private final String name;
    private final ItemRarity rarity;
    private final String description;

    /**
     * 
     * @param id
     *          id of the object, used only for comparison and hashing purpose
     * @param name
     *          name of the object
     * @param rarity
     *          rarity of the item
     * @param description
     *          description of the Item
     */
    public AbstractItem(final int id, final String name, final ItemRarity rarity, final String description) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.rarity = Objects.requireNonNull(rarity);
        this.description = Objects.requireNonNull(description);
    }

    /**
     * 
     * @return
     *          the item id
     */
    public final int getId() {
        return this.id;
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
    public final ItemRarity getRarity() {
        return rarity;
    }

    @Override
    public final String getDescription() {
        return this.description;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((rarity == null) ? 0 : rarity.hashCode());
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
        AbstractItem other = (AbstractItem) obj;
        if (id != other.id) {
            return false;
        }
        if (rarity == null) {
            if (other.rarity != null) {
                return false;
            }
        } else if (!rarity.equals(other.rarity)) {
            return false;
        }
        return true;
    }

    @Override
    public abstract String toString();

    @Override
    public abstract String getEffectDescription();

}
