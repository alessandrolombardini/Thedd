package model.item;

/**
 * An interface to rappresent items. Any specialization need to extend this interface.
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
     * @return a copy of the item which called this
     */
    Item copy();
}
