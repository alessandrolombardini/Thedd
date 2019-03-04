package model.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factory for generating random items from a database.
 */
public final class ItemFactory {

    private static final List<Item> DATABASE = new ArrayList<>();
    private static final Random RNGENERATOR = new Random();

    static {
        initDatabase();
    }

    private ItemFactory() {
    }

    /**
     *  Initialize the Item database. 
     */
    public static void initDatabase() {
        DATABASE.add(new UsableItemPotion());
        DATABASE.add(new UsableItemBomb());
        DATABASE.add(new EquipableItemSword());
    }

    /**
     * 
     * @return
     *          a random item from the database
     */
    public static Item getRandomItem() {
        return DATABASE.get(RNGENERATOR.nextInt(DATABASE.size())).copy();
    }
}
