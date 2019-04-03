package thedd.view.utils.interfaces;

import java.util.Collection;

/**
 * An object which can be observed by {@link thedd.view.utils.interfaces.Observer}s.
 * When the designed event is verified. this object triggers every Observer registered
 * and can sends a message to them.
 *
 * @param <T>
 *      the type of the message sent, if any, to every Observer. 
 *      If no message is sent, T must be {@link Void}. 
 */
public interface Observable<T> {

    /**
     * Register a new {@link thedd.view.utils.interfaces.Observer} which will be notified.
     * 
     * @throws IllegalArgumentException
     *          if the Observer is not accepted by this Observable
     * @param newObserver
     *          the new Observer to register
     */
    void bindObserver(Observer<T> newObserver);

    /**
     * Remove the {@link thedd.view.utils.interfaces.Observer} from the registered ones, if present.
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
     * Notify every registered {@link thedd.view.utils.interfaces.Observer} and send them a message, 
     * if requested.
     */
    void emit();

    /**
     * Return an immutable copy of the collection
     * of registered {@link thedd.view.utils.interfaces.Observer}s.
     * @return
     *          The immutable collection of registered Observers.
     */
    Collection<Observer<T>> getRegisteredObservers();
}
