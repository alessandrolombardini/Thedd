package thedd.view.actionselector;

/**
 * Identifies the different interactions between the {@link ActionSelectorController}
 * and its sub panes.
 */
public enum Command {
    /**
     * Signals that the controller should refresh its components.
     */
    UPDATE,
    /**
     * Signals that the previous action was selected.
     */
    PREVIOUS,
    /**
     * Signals that the next action is selected.
     */
    NEXT,
    /**
     * Signals that the current action has been selected.
     */
    SELECT,
    /**
     * Signals that the selection of the current action has been undone.
     */
    RETURN
}
