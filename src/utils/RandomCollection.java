package utils;
/**
 * A collection capable of executing random weighted selection.
 *
 * @param <E> the type of the items stored in this collection
 */
public interface RandomCollection<E> {

    /**
     * Adds an item to the collection.
     * @param weight the weight of the item
     * @param item the item to be inserted
     * @return the updated collection
     */
    RandomCollection<E> add(E item, double weight);

    /**
     * Removes an item from the collection.
     * @param item the item to be inserted
     * @return true if the element was removed, false otherwise
     */
    boolean remove(E item);

    /**
     * Gets the next random item.
     * @return a random item
     */
    E getNext();

    /**
     * Updates the weight of the given item.
     * @param item the item to be updated
     * @param weight the new weight value
     * @return true if the value was updated, false if the item was not found
     */
    boolean updateItemWeight(E item, double weight);

}
