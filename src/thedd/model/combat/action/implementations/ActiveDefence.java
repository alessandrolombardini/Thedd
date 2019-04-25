package thedd.model.combat.action.implementations;

import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageTypeImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.tag.ActionTag;

/**
 * Standard defensive action: if the attacker fails, allows the defender to take an extra action.
 */
public class ActiveDefence extends ActionImpl {

    private static final String NAME = "Active defence";
    private static final String DESCRIPTION = "The fighter takes a defensive stance and prepares " 
                                              + "to counter incoming attacks.\n" 
                                              + "If an offensive action targeting this character "
                                              + "misses, the the round will pause and the character will "
                                              + "be allowed to take an extra action.\n";
    private static final double BASE_HITCHANCE = 1d;

    /**
     * Public constructor.
     */
    public ActiveDefence() {
        super(new ActionBuilder().setName(NAME)
                                 .setDescription(DESCRIPTION)
                                 .setTargetType(TargetType.SELF)
                                 .setBaseHitChance(BASE_HITCHANCE)
                                 .setLogMessage(LogMessageTypeImpl.PARRY_ACTION)
                                 .build());
        addTag(ActionTag.IGNORES_MODIFIERS, true);
        addTag(ActionTag.DEFENSIVE, true);
        addTag(ActionTag.PARRY, true);

    }
}
