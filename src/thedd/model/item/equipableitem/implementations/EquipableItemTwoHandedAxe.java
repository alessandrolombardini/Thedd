package thedd.model.item.equipableitem.implementations;

import java.util.Arrays;
import java.util.Objects;

import thedd.model.combat.action.effect.ActionModifierAdderEffect;
import thedd.model.combat.modifier.DamageAdderModifier;
import thedd.model.combat.requirements.tags.EffectTagsRequirement;
import thedd.model.combat.requirements.tags.TagRequirementType;
import thedd.model.combat.tag.EffectTag;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;
import thedd.model.item.equipableitem.EquipableItemImpl;
import thedd.model.item.equipableitem.EquipableItemType;

/**
 * Two-handed weapon. 
 * His innate effect is more normal damage to action with {@link EffectTag#NORMAL_DAMAGE}.
 */
public final class EquipableItemTwoHandedAxe extends EquipableItemImpl {

    private static final int ID = -3;
    private static final String NAME = "Two-handed Axe";
    private static final EquipableItemType TYPE = EquipableItemType.TWO_HANDED;
    private static final String DESCRIPTION = "This axe should be used to chop trees, but chopping heads should not be hard for this either.";
    private static final int BASE_DAMAGE = 8;

    /**
     * Create a Two-handed Axe item of a given rarity.
     * The innate effect is added.
     * @param rarity
     *  the rarity of the new item
     */
    private EquipableItemTwoHandedAxe(final ItemRarity rarity) {
        super(ID, NAME, TYPE, Objects.requireNonNull(rarity), DESCRIPTION);
        final DamageAdderModifier dmgMod = new DamageAdderModifier(BASE_DAMAGE,
                                                                   Arrays.asList(new EffectTagsRequirement<>(false, TagRequirementType.REQUIRED, Arrays.asList(EffectTag.NORMAL_DAMAGE))),
                                                                   EffectTag.NORMAL_DAMAGE);
        this.addActionEffect(new ActionModifierAdderEffect(dmgMod, false));
    }

    /**
     * Create a new instance of {@link thedd.model.item.equipableitem.implementations.EquipableItemTwoHandedAxe}.
     * @param rarity
     *  rarity of the new instance
     * @return
     *  the new instance
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new EquipableItemTwoHandedAxe(rarity);
    }
}
