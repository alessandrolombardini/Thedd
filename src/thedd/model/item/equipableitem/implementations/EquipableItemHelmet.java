package thedd.model.item.equipableitem.implementations;

import thedd.model.combat.action.effect.DamageResistanceAdderEffect;
import thedd.model.combat.tag.EffectTag;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;
import thedd.model.item.equipableitem.EquipableItemImpl;
import thedd.model.item.equipableitem.EquipableItemType;

/**
 * Protection for the head.
 * It provide a scarce resistance against {@link thedd.model.combat.tag.EffectTag#NORMAL_DAMAGE}.
 */
public final class EquipableItemHelmet extends EquipableItemImpl {

    private static final int ID = -7;
    private static final String NAME = "Helmet";
    private static final EquipableItemType TYPE = EquipableItemType.HELMET;
    private static final String DESCRIPTION = "Always wear an helmet while walking under a ladder.";
    private static final int BASE_DAMAGE_REDUCTION = 1;

    /**
     * Create a new Helmet Item and add his innate effect,
     * which is a {@link thedd.model.combat.action.effect.DamageResistanceAdderEffect}.
     * @param rarity
     *  the rarity of the new Item
     */
    private EquipableItemHelmet(final ItemRarity rarity) {
        super(ID, NAME, TYPE, rarity, DESCRIPTION);
        this.addActionEffect(new DamageResistanceAdderEffect(BASE_DAMAGE_REDUCTION, EffectTag.NORMAL_DAMAGE, false, false));
    }

    /**
     * Create a new instance of {@link EquipableItemHelmet} with a given rarity.
     * @param rarity
     *  the rarity of the new item
     * @return
     *  the new instance of the Item
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new EquipableItemHelmet(rarity);
    }
}
