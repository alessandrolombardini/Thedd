package model.item;

import java.util.Map;
import java.util.stream.Collectors;

import model.character.Statistic;
import model.combat.Action;

/**
 * Implementation of {@link model.item.UsableItem}.
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
    public UsableItemImpl(final int id, final String name, final Map<Statistic, Integer> effects, final String description) {
        super(id, name, effects, description);
    }

    @Override
    public final Action getAction() {
        return null;
    }

    @Override
    public final Item copy() {
        return new UsableItemImpl(this.getId(), this.getName(), this.getEffects(), this.getDescription());
    }

    @Override
    public final String toString() {
        return this.getName() + ": " + this.getEffects().entrySet().stream().map(e -> e.getKey() + " -> " + e.getValue()).collect(Collectors.joining(", ", "[", "]")) + " | " + this.getDescription();
    }

}
