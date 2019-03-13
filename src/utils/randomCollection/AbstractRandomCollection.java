package utils.randomCollection;

import java.util.Collection;
import java.util.stream.Collectors;

import utils.randomCollection.weightedItem.WeightedItem;

/**
 * Abstract class with common implementations for variants of RandomCollection.
 *
 * @param <E> the type of the stored item
 */
public abstract class AbstractRandomCollection<E> implements RandomCollection<E> {

    /**
     * {@inheritDoc}
     */
    public abstract RandomCollection<E> add(E item, double weight);

    /**
     * {@inheritDoc}
     */
    public abstract boolean updateItemWeight(E item, double weight);

    /**
     * Returns the collection of WeightedItems of the specific implementation of RandomCollection.
     * @return the WeightedItems collection
     */
    protected abstract Collection<WeightedItem<E>> getWeightedCollection();

    @Override
    public final E getNext() {
        //Code taken and slightly modified from https://stackoverflow.com/questions/6737283/weighted-randomness-in-java
        double random = 0d;
        double totalWeight = 0.0d;
        for (final WeightedItem<E> element : getWeightedCollection()) {
            totalWeight += element.getWeight();
        }

        // Now choose a random item
        while (random == 0d) {
            random = Math.random() * totalWeight;
        }
        for (final WeightedItem<E> element : getWeightedCollection()) {
            random -= element.getWeight();
            if (random <= 0d) {
                return (E) element.getItem();
            }
        }
        throw new IllegalStateException("This should not happen");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getWeightedCollection().stream()
                .map(e -> "item:" + e.getItem() + " weight " + e.getWeight())
                .collect(Collectors.joining(",", "[", "]"));
    }
}
