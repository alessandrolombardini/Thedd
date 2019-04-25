package thedd.model.character.types;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomUtils;
import thedd.model.character.BasicCharacterImpl;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.action.implementations.ActiveDefence;
import thedd.model.combat.action.implementations.FieryTouch;
import thedd.model.combat.action.implementations.HeavyAttack;
import thedd.model.combat.action.implementations.LightAttack;
import thedd.model.combat.modifier.DamageModifier;
import thedd.model.combat.modifier.Modifier;
import thedd.model.combat.modifier.ModifierActivation;
import thedd.model.combat.requirements.tags.TagRequirement;
import thedd.model.combat.requirements.tags.TagRequirementType;
import thedd.model.combat.tag.EffectTag;
import thedd.model.combat.tag.Tag;
import thedd.utils.randomcollections.RandomPrority;

/**
 * Dark Destructor extension of
 * {@link thedd.model.character.BasicCharacterImpl}.
 */
public class DarkDestructor extends BasicCharacterImpl {

    private static final String DEFAULT_NAME = "Dark Destructor";
    private static final double DAMAGE_RESISTANCE = -0.3;
    private static final double DAMAGE_WEAKNESS = 0.2;
    private static final int BASE_AGILITY = 8;
    private static final int VARIATION_AGILITY = 1;
    private static final int BASE_HEALTH = 150;
    private static final int VARIATION_HEALTH = 0;
    private static final int BASE_CONSTITUTION = 5;
    private static final int VARIATION_CONSTITUTION = 1;
    private static final int BASE_STRENGTH = 7;
    private static final int VARIATION_STRENGTH = 1;

    /**
     * DarkDestructor's constructor.
     */
    public DarkDestructor() {
        super(DEFAULT_NAME, false);
        setPermanentModifiers();
    }

    private void setPermanentModifiers() {
        final ModifierActivation defensive = ModifierActivation.ACTIVE_ON_DEFENCE;
        final List<Tag> requiredTags = new ArrayList<Tag>();
        final List<Tag> allowedTags = new ArrayList<Tag>();
        // Resistance to physical damage
        final Modifier<ActionEffect> damageResistance = new DamageModifier(DAMAGE_RESISTANCE, false, true, defensive);
        allowedTags.add(EffectTag.NORMAL_DAMAGE);
        allowedTags.add(EffectTag.AP_DAMAGE);
        damageResistance.addRequirement(new TagRequirement<>(false, TagRequirementType.ALLOWED, allowedTags));
        addEffectModifier(damageResistance, true);
        // Weakness to holy damage
        final Modifier<ActionEffect> holyDmgWeakness = new DamageModifier(DAMAGE_WEAKNESS, false, true, defensive);
        requiredTags.add(EffectTag.HOLY_DAMAGE);
        holyDmgWeakness.addRequirement(new TagRequirement<>(false, TagRequirementType.REQUIRED, requiredTags));
        addEffectModifier(holyDmgWeakness, true);
        //Initializing actions (all of them ignore modifiers)
        Action action = new LightAttack(TargetType.FOE);
        action.getEffects().forEach(e -> e.addTag(EffectTag.IGNORES_MODIFIERS, true));
        addWeightedAction(action, RandomPrority.HIGH);
        action = new FieryTouch(TargetType.FOE);
        action.getEffects().forEach(e -> e.addTag(EffectTag.IGNORES_MODIFIERS, true));
        addWeightedAction(action, RandomPrority.LOW);
        action = new ActiveDefence();
        addWeightedAction(action, RandomPrority.DEFAULT);
        action = new HeavyAttack(TargetType.FOE);
        action.getEffects().forEach(e -> e.addTag(EffectTag.IGNORES_MODIFIERS, true));
        addWeightedAction(action, RandomPrority.DEFAULT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHealthPointBaseValue() {
        return RandomUtils.nextInt(0, VARIATION_HEALTH + 1) + BASE_HEALTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAgilityStatBaseValue() {
        return RandomUtils.nextInt(0, VARIATION_AGILITY + 1) + BASE_AGILITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getConstitutionStatBaseValue() {
        return RandomUtils.nextInt(0, VARIATION_CONSTITUTION + 1) + BASE_CONSTITUTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStrengthStatBaseValue() {
        return RandomUtils.nextInt(0, VARIATION_STRENGTH + 1) + BASE_STRENGTH;
    }
}
