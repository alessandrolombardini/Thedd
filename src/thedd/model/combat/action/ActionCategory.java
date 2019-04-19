package thedd.model.combat.action;

/**
 *  The category of an {@link Action}.
 */
public enum ActionCategory {

    /**
     * Standard actions, such as simple strikes/parry/maneuvers.
     */
    STANDARD,
    /**
     * Special abilities that often apply modifiers/statuses.
     */
    SPECIAL,
    /**
     * Actions given by items when used.
     */
    ITEM, 
    /**
     * Actions given by the interaction with InteractableActionPerformer.
     */
    INTERACTABLE,
    /**
     * Actions given by a Status.
     */
    STATUS;

}
