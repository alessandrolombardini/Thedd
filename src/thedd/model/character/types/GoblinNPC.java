package thedd.model.character.types;

import thedd.model.character.BasicCharacterImpl;

/**
 * Class that implements a Goblin Non-Player Character.
 */
public class GoblinNPC extends BasicCharacterImpl {

    /**
     * Goblin's constructor.
     * 
     * @param name       name of this NPC.
     * @param multiplier rate multiplied at the basic statistics.
     */
    public GoblinNPC(final String name, final double multiplier) {
        super(name, multiplier);
        // this.addWeightedAction(new ActionImpl() , RandomActionPrority.DEFAULT);
    }
}
