package thedd.utils.randomcollections.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import thedd.utils.randomcollections.AbstractRandomCollection;
import thedd.utils.randomcollections.RandomCollection;
import thedd.utils.randomcollections.RandomPrority;
import thedd.utils.randomcollections.weightedItem.WeightedItem;
import thedd.utils.randomcollections.weightedItem.WeightedItemImpl;

/**
 * An implementation of RandomCollection which uses a List as a base Collection.
 * @param <E> the type of object to be stored in the set
 */
public class RandomListImpl<E> extends AbstractRandomCollection<E> implements RandomList<E> {

    private final List<WeightedItem<E>> list = new ArrayList<>();

    /**
     * Adds (as per List.add() implementation) an item to the list.
     */
    @Override
    public RandomCollection<E> add(final E item, final double weight) {
        if (weight >= 0d) {
            list.add(new WeightedItemImpl<E>(item, weight));
        }
        return this;
    }

    /**
     * Updates the specified item with the new specified weight.
     */
    @Override
    public boolean updateItemWeight(final E item, final double weight) {
        final WeightedItemImpl<E> newItem = new WeightedItemImpl<E>(item, weight);
        if (!list.contains(newItem) || weight < 0d) {
            return false;
        } else {
            list.remove(newItem);
            list.add(newItem);
            return true;
        }
    }

    /**
     * Returns the list of weighted items.
     */
    @Override
    protected Collection<WeightedItem<E>> getWeightedCollection() {
        return Collections.unmodifiableList(list);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(final Object item) {
        return list.remove(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RandomCollection<E> add(final WeightedItem<E> item) {
        if (item != null && item.getWeight() >= 0d) {
            list.add(new WeightedItemImpl<E>(item.getItem(), item.getWeight())); 
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RandomCollection<E> addAllWeighted(final Collection<WeightedItem<E>> items) {
        list.addAll(items);
        return this;
    }

    /**
     * @see java.util.List#clear()
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * @see java.util.List#contains(Object)
     */
    @Override
    public boolean contains(final Object o) {
        return getList().contains(o);
    }

    /**
     * @see java.util.List#containsAll(Collection)
     */
    @Override
    public boolean containsAll(final Collection<?> c) {
        return getList().containsAll(c);
    }

    /**
     * @see java.util.List#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * @see java.util.List#iterator()
     */
    @Override
    public Iterator<E> iterator() {
        return getList().iterator();
    }

    /**
     * @see java.util.List#removeAll(Collection)
     */
    @Override
    public boolean removeAll(final Collection<?> c) {
        if (c == null) {
            return false;
        }
        boolean changed = false;
        for (final Iterator<?> i = c.iterator(); i.hasNext();) {
            if (list.remove(i.next())) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * @see java.util.List#retainAll(Collection)
     */
    @Override
    public boolean retainAll(final Collection<?> c) {
        if (c == null) {
            return false;
        }
        boolean changed = false;
        for (final Iterator<?> i = c.iterator(); i.hasNext();) {
            if (!list.remove(i.next())) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * @see java.util.List#size()
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * @see java.util.List#toArray()
     */
    @Override
    public Object[] toArray() {
        return getList().toArray();
    }

    /**
     * @see java.util.List#toArray(Object[])
     */
    @Override
    public <T> T[] toArray(final T[] a) {
        return getList().toArray(a);
    }

    /**
     * Adds the element to the collection.<br>
     * If e is not of type WeightedItem, sets its weight to RandomPriority.DEFAULT.
     * @see java.util.Set#add(Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean add(final E e) {
        final boolean result = getList().contains(e);
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
     * @see java.util.List#get(int)
     */
    @Override
    public E get(final int arg0) {
        return getList().get(arg0);
    }

    /**
     * @see java.util.List#indexOf(Object)
     */
    @Override
    public int indexOf(final Object arg0) {
        return getList().indexOf(arg0);
    }

    /**
     * @see java.util.List#lastIndexOf(Object)
     */
    @Override
    public int lastIndexOf(final Object arg0) {
        return getList().lastIndexOf(arg0);
    }

    /**
     * @see java.util.List#listIterator()
     */
    @Override
    public ListIterator<E> listIterator() {
        return getList().listIterator();
    }

    /**
     * @see java.util.List#listIterator(int)
     */
    @Override
    public ListIterator<E> listIterator(final int arg0) {
        return getList().listIterator(arg0);
    }

    /**
     * Unsupported operation.
     * @throws UnsupportedOperationException
     */
    @Override
    public E remove(final int arg0) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation.
     * @throws UnsupportedOperationException
     */
    @Override
    public E set(final int arg0, final E arg1) {
        throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.List#subList(int, int)
     */
    @Override
    public List<E> subList(final int arg0, final int arg1) {
        return getList().subList(arg0, arg1);
    }

    /**
     * @see java.util.List#add(int, Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void add(final int index, final E element) {
        final WeightedItem<E> item;
        if (element instanceof WeightedItem<?>) {
            item = (WeightedItem<E>) element;
        } else {
            item = new WeightedItemImpl<E>(element, RandomPrority.DEFAULT.getWeight());
        }
        list.add(index, item);
    }

    /**
     * Unsupported operation.
     * @throws UnsupportedOperationException
     */
    @Override
    public boolean addAll(final int index, final Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

}
