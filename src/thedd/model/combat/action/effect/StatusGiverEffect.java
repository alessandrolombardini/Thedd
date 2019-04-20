package thedd.model.combat.action.effect;

import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.status.Status;

/**
 * An action effect that can assign (via {@link ActionActor#addStatus})
 * a {@link Status} to an {@link ActionActor}.
 */
public class StatusGiverEffect extends AbstractActionEffect {

    private final Status status;

    /**
     * @param status the status that will be applied to the target of the effect
     */
    public StatusGiverEffect(final Status status) {
        super();
        this.status = status;
    }

    @Override
    public final String getDescription() {
        final StringBuilder sb = new StringBuilder();
        return sb.append("Applies ")
                 .append(status.getName())
                 .append(" (")
                 .append(status.getBaseDuration())
                 .append(" turns)")
                 .toString();
    }

    /**
     * Applies the assigned status to the target.
     */
    @Override
    public void apply(final ActionActor target) {
        target.addStatus(status);
    }

    @Override
    public final String getLogMessage() {
        return status.getName() + " applied for " + status.getBaseDuration() + " rounds";
    }

    @Override
    public final String getPreviewMessage() {
        return "Applies " + status.getName(); 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionEffect getSpecializedCopy() {
        return new StatusGiverEffect(status);
    }

}
