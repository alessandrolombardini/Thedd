package thedd.model.item.equipableitem.implementations;

import thedd.model.character.statistics.Statistic;
import thedd.model.combat.action.effect.EquipmentStatisticChangerEffect;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;
import thedd.model.item.equipableitem.EquipableItemImpl;
import thedd.model.item.equipableitem.EquipableItemType;

/**
 * A charm which makes the wearer feel thougher.
 * It provides an additional point of {@link thedd.model.character.statistics.Statistic#HEALTH_POINT}.
 */
public final class EquipableItemAmulet extends EquipableItemImpl {

    private static final int ID = -9;
    private static final String NAME = "Amulet";
    private static final EquipableItemType TYPE = EquipableItemType.AMULET;
    private static final String DESCRIPTION = "A simple charm which makes the wearer feel tougher.";
    private static final int ADDITIONAL_HP = 5;

    /**
     * Create a new Amulet Item and add his innate effect,
     * which is a {@link thedd.model.combat.action.effect.EquipmentStatisticChangerEffect}.
     * @param rarity
     *  the rarity of the new item
     */
    private EquipableItemAmulet(final ItemRarity rarity) {
        super(ID, NAME, TYPE, rarity, DESCRIPTION);
        this.addActionEffect(new EquipmentStatisticChangerEffect(Statistic.HEALTH_POINT, ADDITIONAL_HP));
    }

    /**
     * Create a new instance of {@link thedd.model.item.equipableitem.implementations.EquipableItemAmulet}.
     * @param rarity
     *          the rarity of the item
     * @return
     *  the new instance with the given rarity
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new EquipableItemAmulet(rarity);
    }
}
