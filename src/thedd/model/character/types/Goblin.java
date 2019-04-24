package thedd.model.character.types;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import thedd.model.character.BasicCharacterImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.action.implementations.LightAttack;
import thedd.model.combat.action.implementations.NastyStrike;
import thedd.model.combat.modifier.DamageModifier;
import thedd.model.combat.modifier.Modifier;
import thedd.model.combat.modifier.ModifierActivation;
import thedd.model.combat.requirements.tags.TagRequirement;
import thedd.model.combat.requirements.tags.TagRequirementType;
import thedd.model.combat.tag.EffectTag;
import thedd.model.combat.tag.Tag;
import thedd.utils.randomcollections.RandomPrority;

/**
 * Goblin extension of {@link thedd.model.character.BasicCharacterImpl}.
 */
public class Goblin extends BasicCharacterImpl {

    private static final String DEFAULT_NAME = "Goblin";
    private static final double POISON_RESISTANCE = -0.8;
    private static final int BASE_AGILITY = 6;
    private static final int VARIATION_AGILITY = 2;
    private static final int BASE_HEALTH = 30;
    private static final int VARIATION_HEALTH = 10;
    private static final int BASE_CONSTITUTION = 4;
    private static final int VARIATION_CONSTITUTION = 2;
    private static final int BASE_STRENGTH = 4;
    private static final int VARIATION_STRENGTH = 3;

    /**
     * Goblin's constructor.
     */
    public Goblin() {
        super(DEFAULT_NAME, false);
        setPermanentModifiers();
        this.addWeightedAction(new LightAttack(TargetType.FOE), RandomPrority.VERY_HIGH);
        this.addWeightedAction(new NastyStrike(TargetType.FOE), RandomPrority.LOW);
    }

    private void setPermanentModifiers() {
        final ModifierActivation defensive = ModifierActivation.ACTIVE_ON_DEFENCE;
        final List<Tag> requiredTags = new ArrayList<Tag>();
        final Modifier<ActionEffect> poisonResistance = new DamageModifier(POISON_RESISTANCE, true, true, defensive);
        requiredTags.add(EffectTag.POISON_DAMAGE);
        poisonResistance.addRequirement(new TagRequirement<>(false, TagRequirementType.REQUIRED, requiredTags));
        addEffectModifier(poisonResistance, true);
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
