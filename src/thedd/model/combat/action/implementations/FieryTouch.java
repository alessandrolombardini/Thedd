package thedd.model.combat.action.implementations;

import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.DamageEffect;
import thedd.model.combat.tag.ActionTag;
import thedd.model.combat.tag.EffectTag;

/**
 * Fiery touch action: deals high amount of fire damage but has a lower hit chance.
 */
public class FieryTouch extends ActionImpl {

    private static final String NAME = "Fiery touch";
    private static final String DESCRIPTION = "The user sets the target ablaze with a "
                                              + "lethal magical touch."
                                              + "\nDeals great amounts "
                                              + "of damage, but it's rather hard to score an hit "
                                              + "due to the need of being close to the target.";
    private static final double BASE_HITCHANCE = 0.4d;
    private static final double BASE_FIRE_DAMAGE = 40d;

    /**
     * @param targetType the target type of the action
     */
    public FieryTouch(final TargetType targetType) {
        super(new ActionBuilder().setName(NAME)
                .setCategory(ActionCategory.SPECIAL)
                .setDescription(DESCRIPTION)
                .setBaseHitChance(BASE_HITCHANCE)
                .setTargetType(targetType)
                .build());
        this.addTag(ActionTag.OFFENSIVE, true);

        final DamageEffect fireDamage = new DamageEffect(BASE_FIRE_DAMAGE);
        fireDamage.addTag(EffectTag.FIRE_DAMAGE, true);

        addEffect(fireDamage);
    }

}
