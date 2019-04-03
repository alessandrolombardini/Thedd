package thedd.view.explorationpane;

import thedd.view.explorationpane.enums.PartyType;

/**
 * Component that visualize an actor.
 *
 */
public interface ActorViewer {
    /**
     * Return the party side of the actor visualized.
     * @return
     *          the party side
     */
    PartyType getPartySide();

    /**
     * Return the position in the party of the actor visualized.
     * @return
     *          the party position
     */
    int getPartyPosition();
}
