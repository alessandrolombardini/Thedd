package thedd.view.explorationpane.logger;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 * A manager for {@link Logger}s. It can be run on a secondary thread.
 *
 */
public class LoggerManager extends Task<Integer> {

    private static final int MILLISECONDS_SLEEP = 1500;

    //This logger is a graphic component. It cannot be static as it can vary between different LoggerManagers.
    @SuppressWarnings("PMD.LoggerIsNotStaticFinal")
    private final Logger managedLogger;
    private final Queue<String> queuedLogStrings;
    private final EventHandler<WorkerStateEvent> loggerCloser = new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(final WorkerStateEvent event) {
            Platform.runLater(() -> LoggerManager.this.managedLogger.setVisibility(false));
        }
    };

    /**
     * Create a LoggerManager bounded to a Logger.
     * @param logger
     *          the logger to be managed
     * @param queuedLogStrings
     *          the strings to be displayed in the logger
     */
    public LoggerManager(final Logger logger, final Queue<String> queuedLogStrings) {
        this.queuedLogStrings = new LinkedList<>(Objects.requireNonNull(queuedLogStrings));
        this.managedLogger = Objects.requireNonNull(logger);
        setOnCancelled(loggerCloser);
        setOnSucceeded(loggerCloser);
    }

    @Override
    protected final Integer call() throws Exception {
        managedLogger.setVisibility(true);
        queuedLogStrings.forEach(c -> {
            Platform.runLater(() -> managedLogger.setText(c));
            try {
                Thread.sleep(MILLISECONDS_SLEEP);
            } catch (InterruptedException e) {
            }
        });
        return 0;
    }

}
