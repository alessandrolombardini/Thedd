package model.item.usableitem;

import java.util.ArrayList;

import model.item.HealingEffect;
/**
 * Class of Item Potion that heals the target.
 *
 */
public class UsableItemPotion extends UsableItemImpl {

    private static final int ID = 0;
    private static final String NAME = "Potion";
    private static final String DESCRIPTION = "A mystical beverage that heal wounds";
    private static final double HEALING_VALUE = 20.0;
    /**
     * Create an instance of a Potion item.
     */
    public UsableItemPotion() {
        super(ID, NAME, new ArrayList<>(), DESCRIPTION);
        this.addActionEffect(new HealingEffect(HEALING_VALUE));
    }

}
