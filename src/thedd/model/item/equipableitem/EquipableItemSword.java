package thedd.model.item.equipableitem;

import model.combat.modifier.DamageModifier;
import model.combat.modifier.ModifierActivation;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;
import thedd.model.item.ModifierAdderEffect;

/**
 * One handed weapon.
 *
 */
public class EquipableItemSword extends EquipableItemImpl {

    private static final int ID = -1;
    private static final String NAME = "Sword";
    private static final EquipableItemType TYPE = EquipableItemType.ONE_HANDED;
    private static final String DESCRIPTION = "A sword";
    private static final int BASE_DAMAGE = 3;
    /**
     * Create a Sword instance and add effects.
     * @param rarity
     *          the rarity of the new item
     */
    public EquipableItemSword(final ItemRarity rarity) {
        super(ID, NAME, TYPE, rarity, DESCRIPTION);
        this.addActionEffect(new ModifierAdderEffect(new DamageModifier(BASE_DAMAGE, false, ModifierActivation.ACTIVE_ON_ATTACK), false));
    }

    /**
     * @param rarity
     *          the rarity of the new instance
     * @return a new instance of EquipableItemSword
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new EquipableItemSword(rarity);
    }
}
