package thedd.model.combat.status.defensive;

import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageTypeImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.ActionModifierAdderEffect;
import thedd.model.combat.modifier.HitChanceModifier;

/**
 * Action applied when activating a defensive status.
 * Adds the provided modifier to the target.
 */
public class DefensiveStatusAction extends ActionImpl {
    private static final String NAME = "Defensive stance";
    private static final double BASE_HITCHANCE = 1d;

    /**
     * @param modifier the modifier to add to the actor
     */
    public DefensiveStatusAction(final HitChanceModifier modifier) {
        super(new ActionBuilder().setName(NAME)
                                 .setBaseHitChance(BASE_HITCHANCE)
                                 .setCategory(ActionCategory.STATUS)
                                 .setTargetType(TargetType.SELF)
                                 .setLogMessage(LogMessageTypeImpl.STATUS_APPLY)
                                 .build());
        addEffect(new ActionModifierAdderEffect(modifier, false));
    }
}
