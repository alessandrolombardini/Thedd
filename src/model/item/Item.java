package model.item;

/**
 * An interface to represent items. Any specialization need to extend this interface.
 *
 */
public interface Item {

    /**
     * 
     * @return the name of the object
     */
    String getName();

    /**
     * 
     * @return whether the object which calls this method is an EquipableItem
     */
    boolean isEquipable();

    /**
     * 
     * @return whether the object which calls this method is a UsableItem
     */
    boolean isUsable();

    /**
     * 
     * @return the rarity of the item.
     */
    ItemRarity getRarity();

    /**
     * @return a copy of the item which called this
     */
    Item copy();

    /**
     * @return the id if the item
     */
    int getId();

    /**
     * 
     * @return the description of the item
     */
    String getDescription();
}
