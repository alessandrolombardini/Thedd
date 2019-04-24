package thedd.model.combat.action.implementations;

import java.util.Arrays;

import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.action.effect.DamageEffect;
import thedd.model.combat.tag.ActionTag;
import thedd.model.combat.tag.EffectTag;

/**
 * Heavy attack action: deals normal and AP damage but has a lower hit chance.
 */
public class HeavyAttack extends ActionImpl {

    private static final String NAME = "Heavy attack";
    private static final String DESCRIPTION = "A decisive blow dealt with great strength." 
                                              + "\nIt deals more damage on average, but it's " 
                                              + "an hard move to pull off.";
    private static final double BASE_HITCHANCE = 0.6d;
    private static final double BASE_DAMAGE_NORMAL = 15d;
    private static final double BASE_DAMAGE_AP = 5d;

    /**
     * @param targetType the target type of the action
     */
    public HeavyAttack(final TargetType targetType) {
        super(new ActionBuilder().setName(NAME)
                                 .setDescription(DESCRIPTION)
                                 .setBaseHitChance(BASE_HITCHANCE)
                                 .setTargetType(targetType)
                                 .build());
        addTag(ActionTag.OFFENSIVE, true);

        //Effects
        final ActionEffect normalDamage = new DamageEffect(BASE_DAMAGE_NORMAL);
        final ActionEffect apDamage = new DamageEffect(BASE_DAMAGE_AP);

        //Effects tags
        normalDamage.addTag(EffectTag.NORMAL_DAMAGE, true);
        apDamage.addTag(EffectTag.AP_DAMAGE, true);

        addEffects(Arrays.asList(normalDamage, apDamage));
    }

}
