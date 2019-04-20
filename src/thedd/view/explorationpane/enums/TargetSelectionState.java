package thedd.view.explorationpane.enums;

/**
 * The selection state which determines the controller methods called.
 *
 */
public enum TargetSelectionState {
    /**
     * A combat target is selected.
     */
    COMBAT_TARGET,

    /**
     * An InteractableActionPerformer is selected.
     */
    EXPLORATION, 

    /**
     * A combat target statistics will be displayed.
     */
    COMBAT_INFORMATION,

    /**
     * There are stairs to choose among.
     */
    STAIRS;

}
