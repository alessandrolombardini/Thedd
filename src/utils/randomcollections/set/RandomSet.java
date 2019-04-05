package utils.randomcollections.set;

import java.util.Set;

import utils.randomcollections.RandomCollection;

/**
 * A RandomCollection which uses a Set as a base Collection.
 * @param <E> the type of object to be stored in the set
 */
public interface RandomSet<E> extends RandomCollection<E>, Set<E> {

    /**
     * Returns an unmodifiable Set containing the items of type E.
     * @return the set of E items
     */
    Set<E> getSet();

}
