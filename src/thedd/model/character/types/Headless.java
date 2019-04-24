package thedd.model.character.types;

import java.util.Arrays;

import org.apache.commons.lang3.RandomUtils;
import thedd.model.character.BasicCharacterImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.implementations.HeavyAttack;
import thedd.model.combat.action.implementations.LightAttack;
import thedd.model.combat.modifier.HitChanceModifier;
import thedd.model.combat.modifier.ModifierActivation;
import thedd.model.combat.requirements.tags.EffectTagsRequirement;
import thedd.model.combat.requirements.tags.TagRequirementType;
import thedd.model.combat.tag.EffectTag;
import thedd.utils.randomcollections.RandomPrority;

/**
 * Headless extension of {@link thedd.model.character.BasicCharacterImpl}.
 */
public class Headless extends BasicCharacterImpl {

    private static final String DEFAULT_NAME = "Headless";
    private static final int BASE_AGILITY = 4;
    private static final int VARIATION_AGILITY = 1;
    private static final int BASE_HEALTH = 50;
    private static final int VARIATION_HEALTH = 10;
    private static final int BASE_CONSTITUTION = 7;
    private static final int VARIATION_CONSTITUTION = 1;
    private static final int BASE_STRENGTH = 7;
    private static final int VARIATION_STRENGTH = 1;

    /**
     * Headless' constructor.
     */
    public Headless() {
        super(DEFAULT_NAME, false);
        this.addWeightedAction(new LightAttack(TargetType.FOE), RandomPrority.DEFAULT);
        this.addWeightedAction(new HeavyAttack(TargetType.FOE), RandomPrority.DEFAULT);
        //This modifier aims to make this character more likely to hit when performing
        //a Heavy Attack without the need to raise it's Agility statistics (thus modifying the other
        //action's hitchances). A better requirement is needed though.
        final HitChanceModifier heavyAtkMod = new HitChanceModifier(0.1, true, ModifierActivation.ACTIVE_ON_ATTACK);
        heavyAtkMod.addRequirement(new EffectTagsRequirement<>(true, TagRequirementType.REQUIRED, Arrays.asList(EffectTag.AP_DAMAGE)));
        this.addActionModifier(heavyAtkMod, true);
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
