package model.item;

/**
 * Types of {@link model.item.EquipableItem}.
 *
 */
public enum EquipableItemType {
    /**
     * One handed Items. One can equip two of this items, either of the same type (weapon or shield) or one of both types, at any time.
     */
    ONE_HANDED(1, 2), 
    /**
     * Two handed Items. They count as two one-handed items. One can only equip one two-handed item at any time.
     */
    TWO_HANDED(2, 2), 
    /**
     * 
     */
    HELMET(1, 1), 
    /**
     * 
     */
    CHEST(1, 1), 
    /**
     * 
     */
    GLOVES(1, 1), 
    /**
     * 
     */
    GREAVES(1, 1), 
    /**
     * 
     */
    RING(1, 2), 
    /**
     * 
     */
    AMULET(1, 1);

    private final int maxCount;
    private final int equipValue;

    EquipableItemType(final int value, final int maxCount) {
        this.maxCount = maxCount; 
        this.equipValue = value;
    }

    /**
     * 
     * @return
     *          the maximum number of EquipableItems of a specific type that a character should equip at once.
     */
    public int getMaxCount() {
        return this.maxCount;
    }
    /**
     * 
     * @return
     *  the value of the type of the item
     */
    public int getEquipValue() {
        return this.equipValue;
    }

    /**
     * 
     * @return whether the Item is a weapon (or a shield)
     */
    public boolean isWeapon() {
        return this.equals(ONE_HANDED) || this.equals(TWO_HANDED);
    }

}
