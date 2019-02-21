package utils;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

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
    protected abstract Collection<WeightedItem> getWeightedCollection();

    @Override
    public final E getNext() {
        //Code taken and slightly modified from https://stackoverflow.com/questions/6737283/weighted-randomness-in-java
        double random = 0d;
        double totalWeight = 0.0d;
        for (final WeightedItem element : getWeightedCollection()) {
            totalWeight += element.weight;
        }

        // Now choose a random item
        while (random == 0d) {
            random = Math.random() * totalWeight;
        }
        for (final WeightedItem element : getWeightedCollection()) {
            random -= element.weight;
            if (random <= 0d) {
                return element.item;
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
                .map(e -> "item:" + e.item + " weight " + e.weight)
                .collect(Collectors.joining(",", "[", "]"));
    }

    /**
     * An item composed of an object of type E and an associated weight.
     */
    protected final class WeightedItem {
        private final double weight;
        private final E item;

        WeightedItem(final E item, final double weight) {
            this.item = item;
            this.weight = weight;
        }

        /**
         * Returns the item.
         * @return the item associated with this class
         */
        public E getItem() {
            return item;
        }

        @Override
        public boolean equals(final Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof AbstractRandomCollection<?>.WeightedItem)) {
                return false;
            } else {
                return this.item.equals(((AbstractRandomCollection<?>.WeightedItem) other).item);
            }
        }
        @Override
        public int hashCode() {
            return Objects.hashCode(item);
        }
    }
}
