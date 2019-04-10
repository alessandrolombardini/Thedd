package thedd.model.character.types;

import thedd.model.character.BasicCharacterImpl;

/**
 * Player Character extension of {@link thedd.model.character.BasicCharacterImpl}.
 */
public class PlayerCharacter extends BasicCharacterImpl {

    /**
     * PlayerCharacter's constructor.
     * 
     * @param name Optional<String> the string name of the player character.
     * @param multiplier rate multiplied at the basic statistics.
     */
    public PlayerCharacter(final String name, final double multiplier) {
        super(name, multiplier);
        // ret.addWeightedAction(new ActionImpl(), RandomActionPrority.DEFAULT);
    }
}
