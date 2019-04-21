package thedd.model.combat.status.poison;

import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageType;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.action.effect.DamageEffect;
import thedd.model.combat.tag.EffectTag;

/**
 * The activationAction of a {@link PoisonStatus}.<p>
 * Deals poison damage to the afflicted actor 
 */
public class PoisonStatusAction extends ActionImpl {

    private static final double DAMAGE = 5d;
    private static final double HITCHANCE = 1d;
    private static final String NAME = "Poisoning";

    /**
     * 
     */
    public PoisonStatusAction() {
        super(new ActionBuilder().setName(NAME)
                                 .setBaseHitChance(HITCHANCE)
                                 .setCategory(ActionCategory.STATUS)
                                 .setTargetType(TargetType.SELF)
                                 .setLogMessage(LogMessageType.STATUS_APPLY)
                                 .build());
        final ActionEffect effect = new DamageEffect(DAMAGE);
        effect.addTag(EffectTag.POISON_DAMAGE, true);
        addEffect(effect);
    }

}
