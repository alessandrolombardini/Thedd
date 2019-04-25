package thedd.model.item;

import java.util.Objects;

import thedd.model.item.equipableitem.EquipableItem;
import thedd.model.item.usableitem.UsableItem;

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
        return this.rarity.getLiteral() + " " + this.name;
    }

    @Override
    public final String getBaseName() {
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
    public abstract String toString();

    @Override
    public abstract String getEffectDescription();

    /**
     * Override should call this method before checking anything else. 
     */
    @Override
    public int hashCode() {
        return Objects.hash(description, id, name, rarity);
    }

    /**
     * Overrides should call this method before checking anything else.
     * @param obj
     *          the object to check
     * @return
     *          whether the object is an equal AbstractItem
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractItem)) {
            return false;
        }
        final AbstractItem other = (AbstractItem) obj;
        return Objects.equals(description, other.description) && id == other.id && Objects.equals(name, other.name)
                && Objects.equals(rarity, other.rarity);
    }


}
