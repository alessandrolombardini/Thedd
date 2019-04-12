package thedd.model.item.equipableitem.implementations;

import thedd.model.combat.action.effect.DamageResistanceAdderEffect;
import thedd.model.combat.tag.EffectTag;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;
import thedd.model.item.equipableitem.EquipableItemImpl;
import thedd.model.item.equipableitem.EquipableItemType;

/**
 * Protection for hands.
 * Provides a scarce protection against {@link thedd.model.combat.tag.EffectTag#NORMAL_DAMAGE}.
 */
public class EquipableItemGloves extends EquipableItemImpl {

    private static final int ID = -5;
    private static final String NAME = "Gloves";
    private static final EquipableItemType TYPE = EquipableItemType.GLOVES;
    private static final String DESCRIPTION = "Gloves protects fingers from cuts, but they are not comfortable for eating.";
    private static final int BASE_DAMAGE_REDUCTION = 1;

    /**
     * Create a new Gloves Item and add his innate effect,
     * which is a {@link thedd.model.combat.action.effect.DamageResistanceAdderEffect}.
     * @param rarity
     *  the rarity of the new Gloves
     */
    public EquipableItemGloves(final ItemRarity rarity) {
        super(ID, NAME, TYPE, rarity, DESCRIPTION);
        this.addActionEffect(new DamageResistanceAdderEffect(BASE_DAMAGE_REDUCTION, EffectTag.NORMAL_DAMAGE, false, false));
    }

    /**
     * Create a new instance of {@link thedd.model.item.equipableitem.implementations.EquipableItemGloves}
     * with a given rarity.
     * @param rarity
     *  the rarity of the new item
     * @return
     *  the new instance of {@link EquipableItemGloves}
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new EquipableItemGloves(rarity);
    }
}
