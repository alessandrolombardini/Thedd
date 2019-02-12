package model.item;

import model.combat.Action;

/**
 * Specialized interface of {@link model.item.Item} of usable items.
 *
 */
public interface UsableItem extends Item {
    /**
     * 
     * @return the action to apply to a target
     */
    Action getAction();

}
