package model.item;

/**
 *  Rarity of an item. It can change the modifiers of an Item, 
 *  the magnitude of the effect or the weight of a random extraction.
 *
 */
public interface ItemRarity {

    /**
     * Return the rarity in a string form.
     * @return
     *          the rarity literal form
     */
    String getLiteral();

    /**
     * Return the implicit base weight of the item.
     * <p>
     * It can be used as it is in an extraction or 
     * modified by a value based on the difficulty 
     * of the game, if implemented.
     * <p>
     * The lower it is, the more rare is the item.
     * @return
     *   the integer value representing the weight of the rarity
     */
    int getBaseWeight();
}
