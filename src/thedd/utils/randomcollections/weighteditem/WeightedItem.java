package thedd.utils.randomcollections.weighteditem;

/**
 * An item to be used in a RandomCollection<br>
 * Holds a reference to the item of type E and a weight associated
 * with that item.
 * @param <E> is the type of the object
 */
public interface WeightedItem<E> {

    /**
     * Returns the weight of item.
     * @return the weight of the item
     */
    double getWeight();

    /**
     * Returns the item.
     * @return the item associated with this class
     */
    E getItem();

    /**
     * Sets the weight of the item.
     * @param weight the new weight of the item
     */
    void setWeight(double weight);
}
