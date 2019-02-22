package utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An implementation of RandomCollection which uses a Set as a base Collection.
 * @param <E> the type of object to be stored in the set
 */
public class RandomSetImpl<E> extends AbstractRandomCollection<E> implements RandomSet<E> {

    private final Set<WeightedItem> set = new HashSet<>();

    /**
     * Adds (as per Set.add() implementation) an item to the set.
     */
    @Override
    public RandomCollection<E> add(final E item, final double weight) {
        if (weight >= 0d) {
            set.add(new WeightedItem(item, weight)); 
        }
        return this;
    }

    /**
     * Updates the specified item with the new specified weight.
     */
    @Override
    public boolean updateItemWeight(final E item, final double weight) {
        final WeightedItem newItem = new WeightedItem(item, weight);
        if (!set.contains(newItem) || weight < 0d) {
            return false;
        } else {
            set.remove(newItem);
            set.add(newItem);
            return true;
        }
    }

    /**
     * Returns the set of weighted items.
     */
    @Override
    protected Collection<AbstractRandomCollection<E>.WeightedItem> getWeightedCollection() {
        return set;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<E> getSet() {
        final Set<E> result = set.stream()
                .map(e -> e.getItem())
                .collect(Collectors.toSet());
        return Collections.unmodifiableSet(result);
    }

}
