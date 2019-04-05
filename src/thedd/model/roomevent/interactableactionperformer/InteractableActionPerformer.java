package thedd.model.roomevent.interactableactionperformer;

import model.combat.interfaces.ActionActor;
import thedd.model.roomevent.RoomEvent;
import thedd.model.roomevent.RoomEventType;

/**
 * Specialization of {@link thedd.model.roomevent.RoomEvent}. It defines the "Contraption" {@link thedd.model.roomevent.RoomEventType}.
 *
 */
public interface InteractableActionPerformer extends RoomEvent, ActionActor {

    /**
     * Complete the RoomEvent and make it no longer available.
     */
    void complete();
}
