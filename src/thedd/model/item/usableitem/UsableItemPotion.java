package thedd.model.item.usableitem;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.LogMessageType;
import thedd.model.combat.action.effect.HealingEffect;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;
/**
 * Class of Item Potion that heals the target.
 *
 */
public final class UsableItemPotion extends UsableItemImpl {

    private static final int ID = 0;
    private static final String NAME = "Potion";
    private static final String DESCRIPTION = "A mystical beverage that heals wounds";
    private static final double HEALING_VALUE = 0.25;
    /**
     * Create an instance of a Potion item of a certain rarity.
     * The higher the {@link thedd.model.item.ItemRarity} is,
     * the better the {@link thedd.model.combat.action.effect.HealingEffect} is.
     * @param rarity
     *  the rarity of the potion
     */
    private UsableItemPotion(final ItemRarity rarity) {
        super(ID, NAME, rarity, DESCRIPTION, true, true);
        this.addActionEffect(new HealingEffect(HEALING_VALUE * this.getEffectsMultiplier().get(rarity)));
    }

    /**
     * Create a new instance of {@link thedd.model.item.usableitem.UsableItemPotion}.
     * @param rarity
     *  the rarity of the new potion
     * @return
     *  a new Potion of the designed rarity
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new UsableItemPotion(rarity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Action buildAction() {
        return new ActionBuilder().setName(this.getName())
                .setCategory(ActionCategory.ITEM)
                .setBaseHitChance(1d)
                .setDescription(DESCRIPTION)
                .setLogMessage(LogMessageType.ITEM_ACTION)
                .build();
    }
}
