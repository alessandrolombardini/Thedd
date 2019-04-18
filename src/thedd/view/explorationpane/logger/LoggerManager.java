package thedd.view.explorationpane.logger;

import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 * A manager for {@link Logger}s. It can be run on a secondary thread.
 *
 */
public interface LoggerManager extends Runnable, Worker<Integer> {

    /**
     * Set how to behave when this Worker is cancelled.
     * @param value
     *          the behavior to adopt on cancel calls.
     */
    void setOnCancelled(EventHandler<WorkerStateEvent> value);

    /**
     * Set how to behave when this Worker has successfully finished.
     * @param value
     *          the behavior to adopt on successful termination
     */
    void setOnSucceded(EventHandler<WorkerStateEvent> value);

    /**
     * Set the logger to be managed.
     * @param logger
     *          the new logger which will be managed by this object
     */
    void setManagedLogger(Logger logger);

}
