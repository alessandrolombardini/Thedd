package model.item;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import model.combat.interfaces.ActionEffect;

/**
 * Abstract class that defines the methods which tell whether a item is of a
 * type or not.
 *
 */
public abstract class AbstractItem implements Item {

    private final int id;
    private final String name;
    //status da applicare al pesonaggio
    private final List<ActionEffect> effects;

    private final String description;

    /**
     * 
     * @param id
     *          id of the object, used only for comparison and hashing purpose
     * @param name
     *          name of the object
     * @param effects
     *          effects of the object
     * @param description
     *          description of the Item
     */
    public AbstractItem(final int id, final String name, final List<ActionEffect> effects, final String description) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.effects = Objects.requireNonNull(effects);
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
    /**
     * 
     * @return
     *          the map of the effects of the item
     */
    protected final List<ActionEffect> getEffects() {
        return Collections.unmodifiableList(effects);
    }

    /**
     * @param newEffect
     *  the effect to be added to the list of effect of the item.
     */
    protected final void addActionEffect(final ActionEffect newEffect) {
        this.effects.add(newEffect);
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
        return id == other.id;
    }

    @Override
    public abstract Item copy();

    @Override
    public abstract String toString();

}
