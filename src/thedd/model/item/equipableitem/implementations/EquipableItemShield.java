package thedd.model.item.equipableitem.implementations;

import java.util.Objects;

import thedd.model.combat.action.effect.ActionModifierAdderEffect;
import thedd.model.combat.modifier.HitChanceModifier;
import thedd.model.combat.modifier.ModifierActivation;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;
import thedd.model.item.equipableitem.EquipableItemImpl;
import thedd.model.item.equipableitem.EquipableItemType;

/**
 * One handed weapon.
 * His innate effect give to the equipper a lower chance to get hit.
 */
public class EquipableItemShield extends EquipableItemImpl {

    private static final int ID = -1;
    private static final String NAME = "Shield";
    private static final EquipableItemType TYPE = EquipableItemType.ONE_HANDED;
    private static final String DESCRIPTION = "A simple shield. It can offer some protection against direct attacks.";
    private static final double BASE_HIT_CHANCE_MOD = 0.2;

    /**
     * Create a Shield item and add his innate effect,
     * which adds an {@link thedd.model.combat.modifier.HitChanceModifier}
     * to action against the equipper of this item.
     * @param rarity
     *          the rarity of the new item
     */
    public EquipableItemShield(final ItemRarity rarity) {
        super(ID, NAME, TYPE, Objects.requireNonNull(rarity), DESCRIPTION);
        final HitChanceModifier hcm = new HitChanceModifier(BASE_HIT_CHANCE_MOD, false, ModifierActivation.ACTIVE_ON_DEFENCE);
        this.addActionEffect(new ActionModifierAdderEffect(hcm, false));
    }

    /**
     * Create a new instance of {@link thedd.model.item.equipableitem.implementations.EquipableItemShield}.
     * @param rarity
     *          the rarity of the new instance
     * @return a new instance of EquipableItemShield
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new EquipableItemShield(rarity);
    }
}
