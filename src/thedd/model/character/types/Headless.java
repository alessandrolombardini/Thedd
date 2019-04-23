package thedd.model.character.types;

import java.util.EnumMap;
import java.util.Random;

import thedd.model.character.BasicCharacter;
import thedd.model.character.BasicCharacterImpl;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.StatValuesImpl;
import thedd.model.character.statistics.Statistic;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.implementations.HeavyAttack;
import thedd.model.combat.action.implementations.LightAttack;
import thedd.utils.randomcollections.RandomPrority;

/**
 * Headless extension of {@link thedd.model.character.BasicCharacterImpl}.
 */
public class Headless extends BasicCharacterImpl {

    private final Random seed;
    private final EnumMap<Statistic, StatValues> stat;
    private static final String DEFAULT_NAME = "Headless";
    private static final int BASE_AGILITY = 4;
    private static final int VARIATION_AGILITY = 2;
    private static final int BASE_HEALTH = 90;
    private static final int VARIATION_HEALTH = 30;
    private static final int BASE_CONSTITUTION = 8;
    private static final int VARIATION_CONSTITUTION = 0;
    private static final int BASE_STRENGTH = 7;
    private static final int VARIATION_STRENGTH = 2;

    /**
     * Headless' constructor.
     * 
     * @param name name of this NPC.
     */
    public Headless(final String name) {
        super(name, false);
        seed = new Random();
        stat = new EnumMap<>(Statistic.class);
        initStatValues();
        this.setStatistics(stat);
        this.addWeightedAction(new LightAttack(TargetType.FOE), RandomPrority.LOW);
        this.addWeightedAction(new HeavyAttack(TargetType.FOE), RandomPrority.DEFAULT);
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
     * Static Factory Method that create a new Headless Character.
     * 
     * @return a new Headless Character.
     */
    public static BasicCharacter getNewInstance() {
        return new Headless(DEFAULT_NAME);
    }
}
