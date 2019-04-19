package thedd.model.item;

/**
 * An interface to represent items. Any specialization need to extend this interface.
 * 
 */
public interface Item {

    /**
     * Return the name of the Item with the rarity of it.
     * @return the name of the object
     */
    String getName();

    /**
     * Return only the name of the item.
     * @return the name of the item
     */
    String getBaseName();

    /**
     * Return whether the item is an instance of {@link thedd.model.item.equipableitem.EquipableItem}.
     * @return whether the object which calls this method is an EquipableItem
     */
    boolean isEquipable();

    /**
     * Return whether the item is an instance of {@link thedd.model.item.usableitem.UsableItem}.
     * @return whether the object which calls this method is a UsableItem
     */
    boolean isUsable();

    /**
     * Return the {@link thedd.model.item.ItemRarity}.
     * @return the rarity of the item.
     */
    ItemRarity getRarity();

    /**
     * Return the unique id of the item.
     * @return the id of the item
     */
    int getId();

    /**
     * 
     * @return the description of the item
     */
    String getDescription();

    /**
     * Return the description of all effects of the item.
     * If the item has no effect. this method shall return an empty String.
     * @return the description of the effects
     */
    String getEffectDescription();
}
