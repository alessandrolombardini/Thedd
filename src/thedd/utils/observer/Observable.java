package thedd.utils.observer;

import java.util.Collection;

/**
 * An object which can be observed by {@link thedd.utils.observer.Observer}s.
 * When the designed event is verified. this object triggers every Observer registered
 * and can sends a message to them.
 *
 * @param <T>
 *      the type of the message sent, if any, to every Observer. 
 *      If no message is sent, T must be {@link Void}. 
 */
public interface Observable<T> {

    /**
     * Register a new {@link thedd.utils.observer.Observer} which will be notified.
     * 
     * @throws IllegalArgumentException
     *          if the Observer is not accepted by this Observable or the value passed is null
     * @param newObserver
     *          the new Observer to register
     */
    void bindObserver(Observer<T> newObserver);

    /**
     * Remove the {@link thedd.utils.observer.Observer} from the registered ones, if present.
     * It will no longer notified.
     * 
     * @throws IllegalStateException
     *          if the observer is not present in the registered one collection
     * 
     * @param observer
     *          the Observer to unregister.
     */
    void removeObserver(Observer<T> observer);

    /**
     * Notify every registered {@link thedd.utils.observer.Observer} and send them a message, 
     * if requested.
     */
    void emit();

    /**
     * Return an immutable copy of the collection
     * of registered {@link thedd.utils.observer.Observer}s.
     * @return
     *          The immutable collection of registered Observers.
     */
    Collection<Observer<T>> getRegisteredObservers();
}
