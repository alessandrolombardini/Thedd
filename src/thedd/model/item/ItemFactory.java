package thedd.model.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import thedd.model.item.equipableitem.EquipableItem;
import thedd.model.item.equipableitem.EquipableItemSword;
import thedd.model.item.usableitem.UsableItemBomb;
import thedd.model.item.usableitem.UsableItemPotion;
import thedd.utils.randomcollections.list.RandomList;
import thedd.utils.randomcollections.list.RandomListImpl;
import thedd.utils.randomcollections.weightedItem.WeightedItemImpl;

/**
 * Factory for generating random items from a database.
 */
public final class ItemFactory {

    private static final List<Function<ItemRarity, Item>> DATABASE = new ArrayList<>();
    private static final Random RNGENERATOR = new Random();
    private static final RandomList<ItemRarity> RARITY_LIST = new RandomListImpl<>();

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
        Item newItem = DATABASE.get(RNGENERATOR.nextInt(DATABASE.size())).apply(RARITY_LIST.getNext());

        if (newItem.isEquipable()) {
            EquipableItem eItem = ((EquipableItem) newItem);
            final int maxNumOfModifiers = eItem.getRarityModifiers().get(eItem.getRarity()).getLeft() 
                                           //weapons have the base damage modifier
                                          + (eItem.getType().isWeapon() ? 1 : 0);
            final int maxNumOfActions = eItem.getRarityModifiers().get(eItem.getRarity()).getRight();
            for (int i = 0; i < maxNumOfModifiers; i++) {
                //add effects
            }
            for (int i = 0; i < maxNumOfActions; i++) {
                //add actions
            }
        }
        return newItem;

    }
}
