package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An implementation of RandomCollection which uses a List as a base Collection.
 * @param <E> the type of object to be stored in the set
 */
public class RandomListImpl<E> extends AbstractRandomCollection<E> implements RandomList<E> {

    private final List<WeightedItem> list = new ArrayList<>();

    /**
     * Adds (as per List.add() implementation) an item to the list.
     */
    @Override
    public RandomCollection<E> add(final E item, final double weight) {
        if (weight >= 0d) {
            list.add(new WeightedItem(item, weight));
        }
        return this;
    }

    /**
     * Updates the specified item with the new specified weight.
     */
    @Override
    public boolean updateItemWeight(final E item, final double weight) {
        final WeightedItem newItem = new WeightedItem(item, weight);
        if (!list.contains(newItem) || weight < 0d) {
            return false;
        } else {
            list.remove(newItem);
            list.add(newItem);
            return true;
        }
    }

    /**
     * Returns the set of weighted items.
     */
    @Override
    protected Collection<AbstractRandomCollection<E>.WeightedItem> getWeightedCollection() {
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> getList() {
        final List<E> result = list.stream()
                .map(e -> e.getItem())
                .collect(Collectors.toList());
        return Collections.unmodifiableList(result);
    }

}
