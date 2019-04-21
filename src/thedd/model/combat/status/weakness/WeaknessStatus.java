package thedd.model.combat.status.weakness;

import thedd.model.combat.status.StatusActivationFrequency;
import thedd.model.combat.status.StatusImpl;
import thedd.model.combat.tag.StatusTag;

/**
 * One time status which decreases constitution, agility and strength for a
 * duration of rounds when applied.
 */
public class WeaknessStatus extends StatusImpl {

    /**
     * @param duration the duration of the status
     */
    public WeaknessStatus(final int duration) {
        super("Weakness", new WeaknessStatusAction(), new WeaknessStatusActionDeact(), StatusActivationFrequency.ONE_TIME, duration);
        addTag(StatusTag.WEAKENED);
    }

}
