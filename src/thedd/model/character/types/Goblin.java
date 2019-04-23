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

    private final Random seed;
    private final EnumMap<Statistic, StatValues> stat;
    private static final String DEFAULT_NAME = "Goblin";
    private static final double POISON_RESISTANCE = -0.8;
    private static final int BASE_AGILITY = 7;
    private static final int VARIATION_AGILITY = 2;
    private static final int BASE_HEALTH = 60;
    private static final int VARIATION_HEALTH = 30;
    private static final int BASE_CONSTITUTION = 4;
    private static final int VARIATION_CONSTITUTION = 2;
    private static final int BASE_STRENGTH = 5;
    private static final int VARIATION_STRENGTH = 2;

    /**
     * Goblin's constructor.
     * 
     * @param name name of this Character.
     */
    public Goblin(final String name) {
        super(name, false);
        seed = new Random();
        stat = new EnumMap<>(Statistic.class);
        initStatValues();
        this.setStatistics(stat);
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
     * Static Factory Method that create a new Goblin Character.
     * 
     * @return a new Goblin Character.
     */
    public static BasicCharacter getNewInstance() {
        return new Goblin(DEFAULT_NAME);
    }
}
