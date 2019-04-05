package thedd.model.roomevent.interactableactionperformer;

import java.util.Arrays;
import java.util.Objects;

import model.combat.implementations.AbstractActionActor;
import model.combat.interfaces.Action;
import thedd.model.roomevent.RoomEventType;

/**
 * Abstract implementation of {@link thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer}.
 * Each specialization must specify whether it is mandatory.
 */
public abstract class AbstractInteractableActionPerformer extends AbstractActionActor implements InteractableActionPerformer {

    //private final Action action;
    private boolean completed;

    /**
     * 
     * @param name
     *          name of the contraption
     * @param action
     *          action performed by the contraption
     */
    public AbstractInteractableActionPerformer(final String name, final Action action) {
        super(name);
        super.setAction(Objects.requireNonNull(action));
        this.getAction().ifPresent(a -> a.setSource(this));
        this.setAvailableActionsList(Arrays.asList(action));
        this.completed = false;
    }

    @Override
    public final RoomEventType getType() {
        return RoomEventType.INTERACTABLE_ACTION_PERFORMER;
    }

    @Override
    public final boolean isCompleted() {
        return completed;
    }

    @Override
    public final void complete() {
        if (!completed) {
            completed = !completed;
        }
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + (this.getType().hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InteractableActionPerformer other = (InteractableActionPerformer) obj;
        if (getName() == null) {
            if (other.getName() != null) {
                return false;
            }
        } else if (!getName().equals(other.getName())) {
            return false;
        }
        return this.getType() == other.getType();
    }

    @Override
    public abstract boolean isSkippable();

}
