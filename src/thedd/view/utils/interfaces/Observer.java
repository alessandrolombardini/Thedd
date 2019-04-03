package thedd.view.utils.interfaces;

import java.util.Optional;

/**
 * Object which can observe {@link thedd.view.utils.interfaces.Observable}s. 
 * It has to explicitly connect to the Observable objects, 
 * then it will be notified when any Observable this is registered to is triggered.
 * {@link thedd.view.utils.interfaces.Observable}s can send a message to this object.
 *
 * @param <T>
 *      the type of the message this Observer can accept. 
 *      If an Observer needs no message, the type needs to be {@link Void}.
 */
public interface Observer<T> {

    /**
     * This method is called by {@link thedd.view.utils.interfaces.Observable}s when an event has occurred.
     * The message can be empty. 
     * 
     * @throws IllegalArgumentException
     *          if this Observer expects a message 
     *          and the message passed as argument is {@link Optional#empty}.
     * @param message the message sent by the Observable object that emitted the trigger.
     */
    void trigger(Optional<T> message);
}
