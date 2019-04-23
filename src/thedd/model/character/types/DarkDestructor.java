package thedd.model.character.types;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import thedd.model.character.BasicCharacter;
import thedd.model.character.BasicCharacterImpl;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.StatValuesImpl;
import thedd.model.character.statistics.Statistic;
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

    private final Random seed;
    private final EnumMap<Statistic, StatValues> stat;
    private static final String DEFAULT_NAME = "Dark Destructor";
    private static final double DAMAGE_RESISTANCE = -0.3;
    private static final double DAMAGE_WEAKNESS = 0.2;
    private static final int BASE_AGILITY = 8;
    private static final int VARIATION_AGILITY = 1;
    private static final int BASE_HEALTH = 150;
    private static final int VARIATION_HEALTH = 40;
    private static final int BASE_CONSTITUTION = 5;
    private static final int VARIATION_CONSTITUTION = 1;
    private static final int BASE_STRENGTH = 7;
    private static final int VARIATION_STRENGTH = 1;

    /**
     * DarkDestructor's constructor.
     * 
     * @param name name of this Boss.
     */
    public DarkDestructor(final String name) {
        super(name, false);
        seed = new Random();
        stat = new EnumMap<>(Statistic.class);
        initStatValues();
        this.setStatistics(stat);
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
        addWeightedAction(new LightAttack(TargetType.FOE), RandomPrority.HIGH);
        addWeightedAction(new FieryTouch(TargetType.FOE), RandomPrority.LOW);
        addWeightedAction(new ActiveDefence(), RandomPrority.LOW);
        addWeightedAction(new HeavyAttack(TargetType.FOE), RandomPrority.DEFAULT);
    }

    private void initStatValues() {
        final int value = seed.nextInt(VARIATION_HEALTH + 1) + BASE_HEALTH;
        this.stat.put(Statistic.HEALTH_POINT, new StatValuesImpl(value, value));
        this.stat.put(Statistic.AGILITY,
                new StatValuesImpl(seed.nextInt(VARIATION_AGILITY + 1) + BASE_AGILITY, StatValuesImpl.NO_MAX));
        this.stat.put(Statistic.CONSTITUTION, new StatValuesImpl(
                seed.nextInt(VARIATION_CONSTITUTION + 1) + BASE_CONSTITUTION, StatValuesImpl.NO_MAX));
        this.stat.put(Statistic.STRENGTH,
                new StatValuesImpl((seed.nextInt(VARIATION_STRENGTH + 1) + BASE_STRENGTH), StatValuesImpl.NO_MAX));
    }

    /**
     * Static Factory Method that create a new Dark Destructor Character.
     * 
     * @return a new Dark Destructor Character.
     */
    public static BasicCharacter getNewInstance() {
        return new DarkDestructor(DEFAULT_NAME);
    }
}
