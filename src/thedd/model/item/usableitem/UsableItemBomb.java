package thedd.model.item.usableitem;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.LogMessageType;
import thedd.model.combat.action.effect.DamageEffect;
import thedd.model.combat.action.targeting.TargetTargetParty;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;

/**
 * A {@link UsableItem} that deals damage to the target. 
 *
 */
public final class UsableItemBomb extends UsableItemImpl {

    private static final int ID = 1;
    private static final String NAME = "Bomb";
    private static final String DESCRIPTION = "An explosive. Be careful while handing it.\n"
                                              + "Causes damage to the target and all its allies";
    private static final double DAMAGE = 20.0;

    /**
     * @param rarity
     *  the rarity of the item. The higher it is, the higher is the damage dealt by the bomb
     */
    private UsableItemBomb(final ItemRarity rarity) {
        super(ID, NAME, rarity, DESCRIPTION, true, false);
        this.addActionEffect(new DamageEffect(DAMAGE * this.getEffectsMultiplier().get(rarity)));
    }

    /**
     * Create a new instance of {@link thedd.model.item.usableitem.UsableItemBomb}.
     * @param rarity
     *          the rarity of the bomb
     * @return
     *          a new item Bomb of the designed rarity
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new UsableItemBomb(rarity);
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
                .setTargetingPolicy(new TargetTargetParty())
                .setLogMessage(LogMessageType.ITEM_ACTION)
                .build();
    }
}
