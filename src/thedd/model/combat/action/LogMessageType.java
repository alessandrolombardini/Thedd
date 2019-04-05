package thedd.model.combat.action;

import thedd.model.combat.actor.ActionActor;

/**
 *  An enum designed to provide basic formatted strings for logging
 *  results of actions.
 */
public enum LogMessageType {

    /**
     * Standard actions shared between characters.
     */
    STANDARD_ACTION,
    /**
     * Actions provided by statuses.
     */
    STATUS_ACTION,
    /**
     * Actions that lead to a parry.
     */
    PARRY_ACTION,
    /**
     * Actions provided by items.
     */
    ITEM_ACTION,

    /**
     * Actions provided by {@link thedd.model.roomevent.interactableactionperformer.Contraption}.
     */
    CONTRAPTION_ACTION,
    /**
     * Actions provided by {@link thedd.model.roomevent.interactableactionperformer.TreasureChest}.
     */
    TREASURE_ACTION;

    /**
     * Gets the message associated with the specified action.
     * @param success true if the action was a success, false otherwise
     * @param action the action to be logged
     * @param target the target of the action
     * @return a formatted string representing the log message of the action
     */
    public String getLogMessage(final boolean success, final Action action, final ActionActor target) {
        final String source = action.getSource().get().getName();
        switch (this) {
        case ITEM_ACTION:
            return "[Item log messag is still undefined]";
        case PARRY_ACTION:
            return success ? String.format("%s is guarding against attacks [%s]", source, action.getName()) 
                    : "[PARRY: this message should not be seen]";
        case STANDARD_ACTION:
            return success ? String.format("%s has hit %s [%s]", source, target.getName(), action.getName())
                    : String.format("%s has missed %s [%s]", source, target.getName(), action.getName());
        case STATUS_ACTION:
            return success ? String.format("%s has failed to resist %s", target.getName(), action.getName())
                    : String.format("%s has resisted %s", target.getName(), action.getName());
        case CONTRAPTION_ACTION:
            return success ? String.format("%s activated %s", target.getName(), action.getName())
                    : String.format("%s avoided %s", target.getName(), action.getName());
        case TREASURE_ACTION: 
            return success ? String.format("%s opened %s", target.getName(), action.getName())
                    : String.format("%s failed to open %s", target.getName(), action.getName());
        default:
            return "[Log message missing]";
        }
    }
}
