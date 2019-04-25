package thedd.model.combat.action.implementations;

import java.util.Arrays;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageTypeImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.DamageEffect;
import thedd.model.combat.action.effect.StatusGiverEffect;
import thedd.model.combat.action.executionpolicies.ExtraActionToSource;
import thedd.model.combat.action.targeting.TargetTargetParty;
import thedd.model.combat.requirements.tags.SourceTagRequirement;
import thedd.model.combat.requirements.tags.TagRequirementType;
import thedd.model.combat.status.weakness.WeaknessStatus;
import thedd.model.combat.tag.ActionTag;
import thedd.model.combat.tag.EffectTag;
import thedd.model.combat.tag.StatusTag;

/**
 * Divine Intervention action: it deals fire and holy damage but weakens the user.
 */
public class DivineIntervention extends ActionImpl {

    private static final String NAME = "Divine Intervention";
    private static final String DESCRIPTION = "A punitive beam of sacred light which scorches the target and all its allies. "
                                              + "\n\nCalling upon the gods requires concentration, using this action will cause "
                                              + "the user to become Weakend by fatigue.";
    private static final double BASE_FIRE_DAMAGE = 5.0;
    private static final double BASE_HOLY_DAMAGE = 35.0;
    private static final double BASE_HIT_CHANCE = 1.0;
    private static final double WEAKNESS_BASE_HITCHANCE = 1d;
    private static final Action EXTRA_ACTION = new ActionBuilder().setName("Weakness")
                                                                   .setCategory(ActionCategory.STATUS)
                                                                   .setLogMessage(LogMessageTypeImpl.STATUS_ACTION)
                                                                   .setBaseHitChance(WEAKNESS_BASE_HITCHANCE)
                                                                   .setEffects(Arrays.asList(new StatusGiverEffect(new WeaknessStatus(3))))
                                                                   .build();

    /**
     * @param targetType the type of target of this action
     */
    public DivineIntervention(final TargetType targetType) {
        super(new ActionBuilder().setName(NAME)
                                 .setCategory(ActionCategory.SPECIAL)
                                 .setDescription(DESCRIPTION)
                                 .setBaseHitChance(BASE_HIT_CHANCE)
                                 .setTargetType(targetType)
                                 .setTargetingPolicy(new TargetTargetParty())
                                 .setExecutionPolicy(new ExtraActionToSource(EXTRA_ACTION.getCopy()))
                                 .build());
        EXTRA_ACTION.addTag(ActionTag.IGNORES_MODIFIERS, true);
        this.addTag(ActionTag.OFFENSIVE, true);
        this.addTag(ActionTag.IGNORES_HITCHANCE_MOD, true);
        this.addTag(ActionTag.IGNORES_DMG_ADDER_MOD, true);
        this.addRequirement(new SourceTagRequirement<>(false, TagRequirementType.UNALLOWED, StatusTag.WEAKENED));

        final DamageEffect fireDamage = new DamageEffect(BASE_FIRE_DAMAGE);
        fireDamage.addTag(EffectTag.FIRE_DAMAGE, true);

        final DamageEffect holyDamage = new DamageEffect(BASE_HOLY_DAMAGE);
        holyDamage.addTag(EffectTag.HOLY_DAMAGE, true);

        this.addEffect(fireDamage);
        this.addEffect(holyDamage);
    }

}
