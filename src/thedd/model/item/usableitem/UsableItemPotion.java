package thedd.model.item.usableitem;

import thedd.model.combat.action.effect.HealingEffect;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;
/**
 * Class of Item Potion that heals the target.
 *
 */
public class UsableItemPotion extends UsableItemImpl {

    private static final int ID = 0;
    private static final String NAME = "Potion";
    private static final String DESCRIPTION = "A mystical beverage that heal wounds";
    private static final double HEALING_VALUE = 0.25;
    /**
     * Create an instance of a Potion item of a certain rarity.
     * 
     * @param rarity
     *  the rarity of the potion. The higher the rarity is, the better the healing effect is.
     */
    public UsableItemPotion(final ItemRarity rarity) {
        super(ID, NAME, rarity, DESCRIPTION);
        this.addActionEffect(new HealingEffect(HEALING_VALUE * this.getEffectsMultiplier().get(rarity)));
    }

    /**
     * 
     * @param rarity
     *  the rarity of the new potion
     * @return
     *  a new Potion of the designed rarity
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new UsableItemPotion(rarity);
    }
}
