package model.combat.status.poison;

import model.combat.status.StatusActivationFrequency;
import model.combat.status.StatusImpl;
import model.combat.tag.StatusTag;

/**
 * Status that represents the effects of poisoning.<p>
 * It damages the afflicted actor overtime for a set duration
 */
public class PoisonStatus extends StatusImpl {

    /**
     * @param duration the duration of the status
     */
    public PoisonStatus(final int duration) {
        super("Poisoned", new PoisonStatusAction(), null, StatusActivationFrequency.OVER_TIME, duration);
        addTag(StatusTag.POISONED);
    }

}
