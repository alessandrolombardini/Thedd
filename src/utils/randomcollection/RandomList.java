package utils.randomcollection;

import java.util.List;

/**
 * A RandomCollection which uses a List as a base Collection.
 * @param <E> the type of object to be stored in the set
 */
public interface RandomList<E> extends RandomCollection<E>, List<E> {

    /**
     * Returns an unmodifiable List of E elements.
     * @return the list of elements
     */
    List<E> getList();

}
