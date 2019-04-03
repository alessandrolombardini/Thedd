package thedd.view.explorationpane.enums;

/**
 * The selection state which determines the controller methods called.
 *
 */
public enum TargetSelectionState {
    /**
     * A combat target is selected.
     */
    COMBAT,

    /**
     * An InteractableActionPerformer is selected.
     */
    EXPLORATION, 

    /**
     * There is no need to select something. Ignore the input.
     */
    INACTIVE;

}
