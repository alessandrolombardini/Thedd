package thedd.model.character.types;

import org.apache.commons.lang3.RandomUtils;

import thedd.model.character.BasicCharacterImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.implementations.HeavyAttack;
import thedd.model.combat.action.implementations.LightAttack;
import thedd.utils.randomcollections.RandomPrority;

/**
 * Headless extension of {@link thedd.model.character.BasicCharacterImpl}.
 */
public class Headless extends BasicCharacterImpl {

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
     */
    public Headless() {
        super(DEFAULT_NAME, false);
        this.addWeightedAction(new LightAttack(TargetType.FOE), RandomPrority.LOW);
        this.addWeightedAction(new HeavyAttack(TargetType.FOE), RandomPrority.DEFAULT);
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
