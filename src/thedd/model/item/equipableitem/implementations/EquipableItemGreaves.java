package thedd.model.item.equipableitem.implementations;

import thedd.model.combat.action.effect.DamageResistanceAdderEffect;
import thedd.model.combat.tag.EffectTag;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;
import thedd.model.item.equipableitem.EquipableItemImpl;
import thedd.model.item.equipableitem.EquipableItemType;

/**
 * Protection for legs and feet.
 * They provide a scarce resistance against {@link thedd.model.combat.tag.EffectTag#NORMAL_DAMAGE}.
 */
public final class EquipableItemGreaves extends EquipableItemImpl {

    private static final int ID = -6;
    private static final String NAME = "Greaves";
    private static final EquipableItemType TYPE = EquipableItemType.GREAVES;
    private static final String DESCRIPTION = "This greaves protect the wearer from the most dangerous harm: striking the bed leg with the pinky toe!";
    private static final int BASE_DAMAGE_REDUCTION = 1;

    /**
     * Create a new Greaves Item and add his innate effect,
     * which is a {@link thedd.model.combat.action.effect.DamageResistanceAdderEffect}.
     * @param rarity
     *  the rarity of the new Greaves
     */
    private EquipableItemGreaves(final ItemRarity rarity) {
        super(ID, NAME, TYPE, rarity, DESCRIPTION);
        this.addActionEffect(new DamageResistanceAdderEffect(BASE_DAMAGE_REDUCTION, EffectTag.NORMAL_DAMAGE, false, false));
    }

    /**
     * Create a new instance of {@link EquipableItemGreaves} with a given rarity.
     * @param rarity
     *  the rarity of the new Item
     * @return
     *  the new instance of Greaves
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new EquipableItemGreaves(rarity);
    }
}
