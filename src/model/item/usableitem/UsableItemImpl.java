package model.item.usableitem;

import java.util.List;
import java.util.stream.Collectors;

import model.combat.action.TargetType;
import model.combat.action.ActionImpl;
import model.combat.action.Action;
import model.combat.action.effect.ActionEffect;
import model.item.AbstractItem;
import model.item.Item;

/**
 * Implementation of {@link model.item.usableitem.UsableItem}.
 *
 */
public class UsableItemImpl extends AbstractItem implements UsableItem {

    /**
     * 
     * @param id
     *  id of the object
     * @param name
     *  name of the item
     * @param effects
     *  map of effects of the item
     * @param description
     *  description of the Item
     */
    public UsableItemImpl(final int id, final String name, final List<ActionEffect> effects, final String description) {
        super(id, name, effects, description);
    }

    @Override
    public final Action getAction() {
        return new ActionImpl(null, this.getName(), this.getEffects(), 1, TargetType.EVERYONE);
    }

    @Override
    public final Item copy() {
        return new UsableItemImpl(this.getId(), this.getName(), this.getEffects(), this.getDescription());
    }

    @Override
    public final String toString() {
        return this.getName() + ": " + this.getEffects().stream().map(e -> e.getLogMessage()).collect(Collectors.joining(", ", "[", "]")) + " | " + this.getDescription();
    }

}
