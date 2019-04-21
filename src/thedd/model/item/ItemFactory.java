package thedd.model.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

import thedd.model.character.statistics.Statistic;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.action.effect.ActionModifierAdderEffect;
import thedd.model.combat.action.effect.DamageResistanceAdderEffect;
import thedd.model.combat.action.effect.EquipmentStatisticChangerEffect;
import thedd.model.combat.action.implementations.DivineIntervention;
import thedd.model.combat.action.implementations.NastyStrike;
import thedd.model.combat.modifier.DamageAdderModifier;
import thedd.model.combat.tag.EffectTag;
import thedd.model.item.equipableitem.EquipableItem;
import thedd.model.item.equipableitem.implementations.EquipableItemAmulet;
import thedd.model.item.equipableitem.implementations.EquipableItemChest;
import thedd.model.item.equipableitem.implementations.EquipableItemGloves;
import thedd.model.item.equipableitem.implementations.EquipableItemGreaves;
import thedd.model.item.equipableitem.implementations.EquipableItemHelmet;
import thedd.model.item.equipableitem.implementations.EquipableItemRing;
import thedd.model.item.equipableitem.implementations.EquipableItemShield;
import thedd.model.item.equipableitem.implementations.EquipableItemSword;
import thedd.model.item.equipableitem.implementations.EquipableItemTwoHandedAxe;
import thedd.model.item.usableitem.UsableItemBomb;
import thedd.model.item.usableitem.UsableItemPotion;
import thedd.utils.randomcollections.list.RandomList;
import thedd.utils.randomcollections.list.RandomListImpl;
import thedd.utils.randomcollections.weighteditem.WeightedItemImpl;

/**
 * Factory for generating random items from a database.
 */
public final class ItemFactory {

    private static final List<Function<ItemRarity, Item>> DATABASE = new ArrayList<>();
    private static final Random RNGENERATOR = new Random();
    private static final RandomList<ItemRarity> RARITY_LIST = new RandomListImpl<>();
    private static final int MAX_DAMAGE_MODIFIER_VALUE = 3;
    private static final int MAX_STAT_MODIFIER_VALUE = 2;

    private static final List<Statistic> STAT_MOD = new ArrayList<>();
    private static final List<EffectTag> DMG_MOD = new ArrayList<>();
    private static final List<EffectTag> RES_MOD = new ArrayList<>();

    static {
        initDatabase();
    }

    private ItemFactory() {
    }

    /**
     *  Initialize the Item database. 
     */
    public static void initDatabase() {
        Arrays.asList(ItemRarityImpl.values()).stream().forEach(v -> RARITY_LIST.add(new WeightedItemImpl<>(v,  v.getBaseWeight())));

        DATABASE.add(UsableItemBomb::getNewInstance);
        DATABASE.add(UsableItemPotion::getNewInstance);
        DATABASE.add(EquipableItemSword::getNewInstance);
        DATABASE.add(EquipableItemShield::getNewInstance);
        DATABASE.add(EquipableItemTwoHandedAxe::getNewInstance);
        DATABASE.add(EquipableItemChest::getNewInstance);
        DATABASE.add(EquipableItemGloves::getNewInstance);
        DATABASE.add(EquipableItemAmulet::getNewInstance);
        DATABASE.add(EquipableItemGreaves::getNewInstance);
        DATABASE.add(EquipableItemRing::getNewInstance);
        DATABASE.add(EquipableItemHelmet::getNewInstance);

    }

    /**
     * Return a new {@link thedd.model.item.Item}. If an {@link thedd.model.item.equipableitem.EquipableItem}
     * is extracted, then additional {@link model.combat.action.Action} and {@link model.combat.action.effect.ActionEffect}
     * are added as well, accordingly with the rarity of the item; otherwise the new item is returned without additions.
     * 
     * @return
     *          a random item from the database
     */
    public static Item getRandomItem() {
        final Item newItem = DATABASE.get(RNGENERATOR.nextInt(DATABASE.size())).apply(RARITY_LIST.getNext());
        if (newItem.isEquipable()) {
            final EquipableItem eItem = ((EquipableItem) newItem);
            final int maxNumOfAdditionalModifiers = eItem.getRarityModifiers().get(eItem.getRarity()).getLeft();
            final int maxNumOfActions = eItem.getRarityModifiers().get(eItem.getRarity()).getRight();
            for (int i = 0; i < maxNumOfAdditionalModifiers; i++) {
                final ModifierType modType = ModifierType.values()[RNGENERATOR.nextInt(ModifierType.values().length)];
                eItem.addActionEffect(Objects.requireNonNull(getRandomActionEffect(modType)));
            }
            for (int i = 0; i < maxNumOfActions; i++) {
                Action additionalAction = AdditionalActionPool.getRandomAdditionalAction();
                while (eItem.getAdditionalActions().contains(additionalAction)) {
                    additionalAction = AdditionalActionPool.getRandomAdditionalAction();
                }
                eItem.addAdditionalAction(additionalAction);
            }
            clearExtractedTagLists();
        }
        return newItem;
    }

    private static void clearExtractedTagLists() {
        STAT_MOD.clear();
        DMG_MOD.clear();
        RES_MOD.clear();
    }

    private static ActionEffect getRandomActionEffect(final ModifierType modType) {
        switch (Objects.requireNonNull(modType)) {
        case MORE_DAMAGE:
            EffectTag dmgType = EffectTag.values()[RNGENERATOR.nextInt(EffectTag.values().length)];
            while (DMG_MOD.contains(dmgType) || dmgType.isHidden()) {
                dmgType = EffectTag.values()[RNGENERATOR.nextInt(EffectTag.values().length)];
            }
            DMG_MOD.add(dmgType);
            return new ActionModifierAdderEffect(new DamageAdderModifier(Math.ceil(RNGENERATOR.nextDouble() * MAX_DAMAGE_MODIFIER_VALUE),
                                                                         new ArrayList<>(),
                                                                         dmgType),
                                                false);
        case DAMAGE_RESISTANCE:
            EffectTag resType = EffectTag.values()[RNGENERATOR.nextInt(EffectTag.values().length)];
            while (RES_MOD.contains(resType) || resType.isHidden()) {
                resType = EffectTag.values()[RNGENERATOR.nextInt(EffectTag.values().length)];
            }
            RES_MOD.add(resType);
            return new DamageResistanceAdderEffect(Math.ceil(RNGENERATOR.nextDouble() * MAX_DAMAGE_MODIFIER_VALUE), 
                                                   resType, 
                                                   false, 
                                                   false);
        case MORE_STAT:
            Statistic statTarget = Statistic.values()[RNGENERATOR.nextInt(Statistic.values().length)];
            while (STAT_MOD.contains(statTarget)) {
                statTarget = Statistic.values()[RNGENERATOR.nextInt(Statistic.values().length)];
            }
            STAT_MOD.add(statTarget);
            return new EquipmentStatisticChangerEffect(statTarget, 
                                                       RNGENERATOR.nextInt(MAX_STAT_MODIFIER_VALUE) + 1);
        default:
            return null;
        }
    }

    private enum ModifierType {
        MORE_DAMAGE, DAMAGE_RESISTANCE, MORE_STAT;
    }

    private static class AdditionalActionPool {
        private static final List<Action> ACTIONS = new ArrayList<>();

        static {
            ACTIONS.add(new NastyStrike(TargetType.EVERYONE));
            ACTIONS.add(new DivineIntervention(TargetType.EVERYONE));
        }

        private static Action getRandomAdditionalAction() {
            return ACTIONS.get(RNGENERATOR.nextInt(ACTIONS.size()));
        }
    }
}
