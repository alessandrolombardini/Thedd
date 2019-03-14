package utils.randomcollection;

import java.util.Collection;

import utils.randomcollection.weightedItem.WeightedItem;

/**
 * A collection capable of executing random weighted selection.
 *
 * @param <E> the type of the items stored in this collection
 */
public interface RandomCollection<E> extends Collection<E> {

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
    boolean remove(Object item);

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

    /**
     * Adds an item to the collection.
     * @param item the item to be added
     * @return the updated collection
     */
    RandomCollection<E> add(WeightedItem<E> item);

    /**
     * Adds a collection of {@link WeightedItem}.
     * @param items the collection to be added
     * @return the updated collection
     */
    RandomCollection<E> addAllWeighted(Collection<WeightedItem<E>> items);

}
