package thedd.model.roomevent.interactableactionperformer;

import thedd.model.combat.actor.ActionActor;
import thedd.model.roomevent.RoomEvent;

/**
 * Specialization of {@link thedd.model.roomevent.RoomEvent}.
 * It defines the "Interactable Action Performer" {@link thedd.model.roomevent.RoomEventType}.
 *
 */
public interface InteractableActionPerformer extends RoomEvent, ActionActor {

    /**
     * Complete the RoomEvent and make it no longer available.
     */
    void complete();
}
