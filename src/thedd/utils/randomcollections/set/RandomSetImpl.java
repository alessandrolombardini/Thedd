package thedd.utils.randomcollections.set;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import thedd.utils.randomcollections.AbstractRandomCollection;
import thedd.utils.randomcollections.RandomCollection;
import thedd.utils.randomcollections.RandomPrority;
import thedd.utils.randomcollections.weighteditem.WeightedItem;
import thedd.utils.randomcollections.weighteditem.WeightedItemImpl;

/**
 * An implementation of RandomCollection which uses a Set as a base Collection.
 * @param <E> the type of object to be stored in the set
 */
public class RandomSetImpl<E> extends AbstractRandomCollection<E> implements RandomSet<E> {

    private final Set<WeightedItem<E>> set = new LinkedHashSet<>();

    /**
     * Adds (as per Set.add() implementation) an item to the set.
     */
    @Override
    public RandomCollection<E> add(final E item, final double weight) {
        if (weight >= 0d) {
            set.add(new WeightedItemImpl<E>(item, weight)); 
        }
        return this;
    }

    /**
     * Adds (as per Set.add() implementation) an item to the set.
     */
    @Override
    public RandomCollection<E> add(final WeightedItem<E> item) {
        if (item != null && item.getWeight() >= 0d) {
            set.add(new WeightedItemImpl<E>(item.getItem(), item.getWeight())); 
        }
        return this;
    }

    /**
     * Adds (as per Set.add() implementation) one or more items to the set.
     */
    @Override
    public RandomCollection<E> addAllWeighted(final Collection<WeightedItem<E>> items) {
        items.forEach(i -> {
            if (i.getWeight() >= 0d) {
                set.add(new WeightedItemImpl<E>(i.getItem(), i.getWeight())); 
            }
        });
        return this;
    }

    /**
     * Updates the specified item with the new specified weight.
     */
    @Override
    public boolean updateItemWeight(final E item, final double weight) {
        final WeightedItemImpl<E> newItem = new WeightedItemImpl<E>(item, weight);
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
    protected Collection<WeightedItem<E>> getWeightedCollection() {
        return Collections.unmodifiableSet(set);
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

    /**
     * Adds the element to the collection.<br>
     * If e is not of type WeightedItem, sets its weight to RandomPriority.DEFAULT.
     * @see java.util.Set#add(Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean add(final E e) {
        final boolean result = getSet().contains(e);
        if (e instanceof WeightedItem<?>) {
            final WeightedItem<E> item = ((WeightedItem<E>) e);
            add(item);
        } else {
            add(e, RandomPrority.DEFAULT.getWeight());
        }
        return result;
    }

    /**
     * Adds the elements of the collection to the set as per
     * {@link RandomSetImpl#add(Object)}.
     * @see java.util.Set#addAll(Object)
     */
    @Override
    public boolean addAll(final Collection<? extends E> c) {
        if (c == null) {
            return false;
        }
        boolean result = false;
        for (final Iterator<? extends E> i = c.iterator(); i.hasNext();) {
            if (add(i.next())) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Clears (as per Set.clear()) the set.
     */
    @Override
    public void clear() {
        set.clear();
    }

    /**
     * @see java.util.Set#contains(Object)
     */
    @Override
    public boolean contains(final Object o) {
        return getSet().contains(o);
    }

    /**
     * @see java.util.Set#containsAll(Collection)
     */
    @Override
    public boolean containsAll(final Collection<?> c) {
        return getSet().containsAll(c);
    }

    /**
     * @see java.util.Set#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * @see java.util.Set#iterator()
     */
    @Override
    public Iterator<E> iterator() {
        return getSet().iterator();
    }

    /**
     * @see java.util.Set#removeAll(Collection)
     */
    @Override
    public boolean removeAll(final Collection<?> c) {
        if (c == null) {
            return false;
        }
        boolean changed = false;
        for (final Iterator<?> i = c.iterator(); i.hasNext();) {
            if (set.remove(i.next())) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * @see java.util.Set#remove(Object)
     */
    @Override
    public boolean remove(final Object item) {
        return set.removeIf(i -> i.getItem().equals(item));
    }

    /**
     * @see java.util.Set#retainAll(Collection)
     */
    @Override
    public boolean retainAll(final Collection<?> c) {
        if (c == null) {
            return false;
        }
        boolean changed = false;
        for (final Iterator<E> i = getSet().iterator(); i.hasNext();) {
            if (!c.contains(i.next())) {
                remove(i);
                changed = true;
            }
        }
        return changed;
    }

    /**
     * @see java.util.Set#size()
     */
    @Override
    public int size() {
        return set.size();
    }

    /**
     * @see java.util.Set#toArray()
     */
    @Override
    public Object[] toArray() {
        return getSet().toArray();
    }

    /**
     * @see java.util.Set#toArray(Object[])
     */
    @Override
    public <T> T[] toArray(final T[] a) {
        return getSet().toArray(a);
    }

}
