package thedd.model.combat.status.defensive;

import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageTypeImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.ActionModifierRemoveEffect;
import thedd.model.combat.modifier.HitChanceModifier;

/**
 * Action activated upon Defensive status expiration.
 */
public class DefensiveStatusActionDeact extends ActionImpl {
    private static final String NAME = "Defensive stance";
    private static final double BASE_HITCHANCE = 1d;

    /**
     * @param modifier the modifier to remove from the actor
     */
    public DefensiveStatusActionDeact(final HitChanceModifier modifier) {
        super(new ActionBuilder().setName(NAME)
                                 .setBaseHitChance(BASE_HITCHANCE)
                                 .setCategory(ActionCategory.STATUS)
                                 .setTargetType(TargetType.SELF)
                                 .setLogMessage(LogMessageTypeImpl.STATUS_EXPIRE)
                                 .build());
        addEffect(new ActionModifierRemoveEffect(modifier));
    }
}
