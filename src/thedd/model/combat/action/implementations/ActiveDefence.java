package thedd.model.combat.action.implementations;

import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageType;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.targeting.DefaultTargeting;
import thedd.model.combat.tag.ActionTag;

/**
 * Standard defensive action: if the attacker fails, allows the defender to take an extra action.
 */
public class ActiveDefence extends ActionImpl {

    private static final String NAME = "Active defence";
    private static final String DESCRIPTION = "The fighter takes a defensive stance and prepares " 
                                              + "to counter the next attack.\n" 
                                              + "If the next offensive action targeting this character "
                                              + "misses, the the round will pause and the character will "
                                              + "be allowed to take an extra action.\n"
                                              + "Being hit while in defensive mode causes the character "
                                              + "to lose its stance.";
    private static final double BASE_HITCHANCE = 1d;

    /**
     * Public constructor.
     */
    public ActiveDefence() {
        super(NAME, ActionCategory.STANDARD, new DefaultTargeting(), BASE_HITCHANCE,
                TargetType.SELF, DESCRIPTION, LogMessageType.PARRY_ACTION);
        addTag(ActionTag.DEFENSIVE, true);
        addTag(ActionTag.PARRY, true);

    }
}
