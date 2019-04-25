package thedd.view.explorationpane;

/**
 * 
 *
 */
public interface DialogResponseManager {

    /**
     * Set the behavior when the DialogResponse is affirmative.
     * @param onAccept
     *          the function to run
     */
    void setDialogAccepted(Runnable onAccept);

    /**
     * Set the behaviour when the DialogResponse is negative.
     * @param onDecline
     *          the function to run
     */
    void setDialogDeclined(Runnable onDecline);

}
