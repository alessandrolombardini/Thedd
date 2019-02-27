package model.item;

/**
 * Types of {@link model.item.EquipableItem}.
 *
 */
public enum EquipableItemType {
    /**
     * One handed Items. One can equip two of this items, either of the same type (weapon or shield) or one of both types, at any time.
     */
    ONE_HANDED, 
    /**
     * Two handed Items. They count as two one-handed items. One can only equip one two-handed item at any time.
     */
    TWO_HANDED, 
    /**
     * 
     */
    HELMET, 
    /**
     * 
     */
    CHEST, 
    /**
     * 
     */
    GLOVES, 
    /**
     * 
     */
    GREAVES, 
    /**
     * 
     */
    RING, 
    /**
     * 
     */
    AMULET;

    private static final int MAX_NUM_OF_RINGS = 2;

    /**
     * 
     * @return whether the Item is a weapon (or a shield)
     */
    public boolean isWeapon() {
        return this == ONE_HANDED || this == TWO_HANDED;
    }

    /**
     * 
     * @return
     *  the max number of rings one should equip
     */
    public static int getMaxNumOfRings() {
        return MAX_NUM_OF_RINGS;
    }
}
