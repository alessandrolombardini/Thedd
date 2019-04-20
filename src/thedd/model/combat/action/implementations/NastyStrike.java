package thedd.model.combat.action.implementations;

import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageType;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.DamageEffect;
import thedd.model.combat.action.effect.StatusGiverEffect;
import thedd.model.combat.action.targeting.DefaultTargeting;
import thedd.model.combat.status.poison.PoisonStatus;
import thedd.model.combat.tag.ActionTag;
import thedd.model.combat.tag.EffectTag;

/**
 * Nasty Strike action: deals low damage, but it always hits the target and inflicts the Poison status.
 */
public class NastyStrike extends ActionImpl {

    private static final String NAME = "Nasty Strike";
    private static final String DESCRIPTION = "A malicious blow dealt with monstrous agility. " 
                                               + "It injects a powerful toxin in the body of the target.";
    private static final double BASE_DAMAGE = 1.0;
    private static final int STATUS_DURATION = 3;
    private static final double BASE_HIT_CHANCE = 1.0;

    /**
     * 
     */
    public NastyStrike() {
        super(NAME, ActionCategory.SPECIAL, new DefaultTargeting(), BASE_HIT_CHANCE, TargetType.EVERYONE, DESCRIPTION, LogMessageType.STANDARD_ACTION);
        this.addTag(ActionTag.OFFENSIVE, true);
        this.addTag(ActionTag.UNBLOCKABLE, true);
        this.addTag(ActionTag.IGNORES_MODIFIERS, true);

        final DamageEffect damageEffect = new DamageEffect(BASE_DAMAGE);
        damageEffect.addTag(EffectTag.POISON_DAMAGE, true);
        damageEffect.addTag(EffectTag.IGNORES_MODIFIERS, true);
        this.addEffect(damageEffect);

        final StatusGiverEffect poisonStatusGiver = new StatusGiverEffect(new PoisonStatus(STATUS_DURATION));
        poisonStatusGiver.addTag(EffectTag.IGNORES_MODIFIERS, true);
        poisonStatusGiver.addTag(EffectTag.IGNORES_MODIFIERS, true);
        this.addEffect(poisonStatusGiver);
    }
}
