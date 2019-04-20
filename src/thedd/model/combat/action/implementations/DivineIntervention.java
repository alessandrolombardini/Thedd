package thedd.model.combat.action.implementations;

import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageType;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.DamageEffect;
import thedd.model.combat.action.targeting.DefaultTargeting;
import thedd.model.combat.tag.ActionTag;
import thedd.model.combat.tag.EffectTag;

/**
 * Divine Intervention action: it deals fire and holy damage.
 */
public class DivineIntervention extends ActionImpl {

    private static final String NAME = "Divine Intervention";
    private static final String DESCRIPTION = "A punitive beam of sacred light which scorches the target.";
    private static final double BASE_FIRE_DAMAGE = 3.0;
    private static final double BASE_HOLY_DAMAGE = 3.0;
    private static final double BASE_HIT_CHANCE = 1.0;

    /**
     * 
     */
    public DivineIntervention() {
        super(NAME, ActionCategory.STANDARD, new DefaultTargeting(), BASE_HIT_CHANCE, TargetType.EVERYONE, DESCRIPTION, LogMessageType.STANDARD_ACTION);
        this.addTag(ActionTag.OFFENSIVE, true);

        final DamageEffect fireDamage = new DamageEffect(BASE_FIRE_DAMAGE);
        fireDamage.addTag(EffectTag.FIRE_DAMAGE, true);

        final DamageEffect holyDamage = new DamageEffect(BASE_HOLY_DAMAGE);
        holyDamage.addTag(EffectTag.HOLY_DAMAGE, true);

        this.addEffect(fireDamage);
        this.addEffect(holyDamage);
    }
}
