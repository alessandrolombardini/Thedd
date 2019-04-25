package thedd.model.item.equipableitem.implementations;

import thedd.model.character.statistics.Statistic;
import thedd.model.combat.action.effect.EquipmentStatisticChangerEffect;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;
import thedd.model.item.equipableitem.EquipableItemImpl;
import thedd.model.item.equipableitem.EquipableItemType;

/**
 * A ring which provide an additional point of {@link Statistic#STRENGTH}.
 */
public final class EquipableItemRing extends EquipableItemImpl {

    private static final int ID = -8;
    private static final String NAME = "Ring";
    private static final EquipableItemType TYPE = EquipableItemType.RING;
    private static final String DESCRIPTION = "A simple iron ring, but it is heavy, so it add mass to the wearer's swinging arm.";
    private static final int ADDITIONAL_STRENGTH = 1;

    /**
     * Create a new Ring wand adds his innate effect,
     * which is {@link EquipmentStatisticChangerEffect}.
     * @param rarity
     *  the rarity of the new Ring
     */
    private EquipableItemRing(final ItemRarity rarity) {
        super(ID, NAME, TYPE, rarity, DESCRIPTION);
        this.addActionEffect(new EquipmentStatisticChangerEffect(Statistic.STRENGTH, ADDITIONAL_STRENGTH));
    }

    /**
     * Create a new instance of {@link EquipableItemRing} with a given rarity.
     * @param rarity
     *  the rarity of the new item
     * @return
     *  the new instance of the item
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new EquipableItemRing(rarity);
    }

}
