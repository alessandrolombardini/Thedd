package thedd.model.item.equipableitem.implementations;

import thedd.model.combat.action.effect.DamageResistanceAdderEffect;
import thedd.model.combat.tag.EffectTag;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;
import thedd.model.item.equipableitem.EquipableItemImpl;
import thedd.model.item.equipableitem.EquipableItemType;

/**
 * Protection for chest.
 * It provide a fair resistance against {@link thedd.model.combat.tag.EffectTag#NORMAL_DAMAGE}.
 */
public class EquipableItemChest extends EquipableItemImpl {

    private static final int ID = -4;
    private static final String NAME = "Chestplate";
    private static final EquipableItemType TYPE = EquipableItemType.CHEST;
    private static final String DESCRIPTION = "This chestplate cover a good portion of the body.";
    private static final int BASE_DAMAGE_REDUCTION = 3;

    /**
     * Create a new Chestplate Item and add his innate effect,
     * which is a {@link thedd.model.combat.action.effect.DamageResistanceAdderEffect} .
     * @param rarity
     *          the rarity of the new Item
     */
    public EquipableItemChest(final ItemRarity rarity) {
        super(ID, NAME, TYPE, rarity, DESCRIPTION);
        this.addActionEffect(new DamageResistanceAdderEffect(BASE_DAMAGE_REDUCTION, EffectTag.NORMAL_DAMAGE, false, false));
    }

    /**
     * Create a new instance of {@link thedd.model.item.equipableitem.implementations.EquipableItemChest}
     * with a given rarity.
     * @param rarity
     *          the rarity of the new Item
     * @return
     *          the new item
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new EquipableItemChest(rarity);
    }
}
