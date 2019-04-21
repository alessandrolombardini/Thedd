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
 * Light attack action: deals low normal damage, has high base hit chance.
 */
public class LightAttack extends ActionImpl {

    private static final String NAME = "Light attack";
    private static final String DESCRIPTION = "A swift and precise attack." 
                                              + "\nWhile more precise, it deals " 
                                              + "less damage on average.";
    private static final double BASE_HITCHANCE = 0.7d;
    private static final double BASE_DAMAGE_NORMAL = 5d;

    /**
     * @param targetType the target type of the action
     */
    public LightAttack(final TargetType targetType) {
        super(new ActionBuilder().setName(NAME)
                                 .setDescription(DESCRIPTION)
                                 .setBaseHitChance(BASE_HITCHANCE)
                                 .setTargetType(targetType)
                                 .build());
        addTag(ActionTag.OFFENSIVE, true);

        //Effects
        final ActionEffect damageEffect = new DamageEffect(BASE_DAMAGE_NORMAL);

        //Effects tags
        damageEffect.addTag(EffectTag.NORMAL_DAMAGE, true);
        addEffects(Arrays.asList(damageEffect));
    }

}
