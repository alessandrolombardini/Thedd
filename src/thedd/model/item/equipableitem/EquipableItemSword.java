package thedd.model.item.equipableitem;


import java.util.Arrays;

import thedd.model.combat.action.effect.ActionModifierAdderEffect;
import thedd.model.combat.modifier.DamageAdderModifier;
import thedd.model.combat.requirements.tags.EffectTagsRequirement;
import thedd.model.combat.requirements.tags.TagRequirementType;
import thedd.model.combat.tag.EffectTag;
import thedd.model.item.Item;
import thedd.model.item.ItemRarity;

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
     * @param rarity
     *          the rarity of the new instance
     * @return a new instance of EquipableItemSword
     */
    public static Item getNewInstance(final ItemRarity rarity) {
        return new EquipableItemSword(rarity);
    }
}
