package thedd.model.item.equipableitem.implementations;


import java.util.Arrays;

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
     * Create a Sword item and add his innate effect,
     * which is a {@link thedd.model.combat.modifier.DamageAdderModifier}.
     * @param rarity
     *          the rarity of the new item
     */
    public EquipableItemSword(final ItemRarity rarity) {
        super(ID, NAME, TYPE, rarity, DESCRIPTION);
        this.addActionEffect(
            new ActionModifierAdderEffect(
                new DamageAdderModifier(BASE_DAMAGE, 
                                        Arrays.asList(new EffectTagsRequirement<>(false, 
                                                                                  TagRequirementType.REQUIRED,
                                                                                  Arrays.asList(EffectTag.NORMAL_DAMAGE))),
                                        EffectTag.NORMAL_DAMAGE), 
                false));
    }

    /**
     * Creates a new instance of {@link thedd.model.item.equipableitem.implementations.EquipableItemSword}.
     * @param rarity
     *          the rarity of the new instance
     * @return a new instance of EquipableItemSword
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new EquipableItemSword(rarity);
    }
}
