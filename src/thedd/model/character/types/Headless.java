package thedd.model.character.types;

import thedd.model.character.BasicCharacterImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.implementations.LightAttack;
import thedd.utils.randomcollections.RandomPrority;

/**
 * Headless extension of {@link thedd.model.character.BasicCharacterImpl}.
 */
public class Headless extends BasicCharacterImpl {

    /**
     * Headless' constructor.
     * 
     * @param name       name of this NPC.
     * @param multiplier rate multiplied at the basic statistics.
     */
    public Headless(final String name, final double multiplier) {
        super(name, multiplier, false);
        // ret.addWeightedAction(new ActionImpl() , RandomActionPrority.DEFAULT);
        this.addWeightedAction(new LightAttack(TargetType.FOE), RandomPrority.DEFAULT);

    }
}
