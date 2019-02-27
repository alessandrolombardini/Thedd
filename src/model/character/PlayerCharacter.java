package model.character;

import java.util.EnumMap;

/**
 * Class that Implements a Player Character.
 */
public class PlayerCharacter extends AbstractCharacter {

    /**
     * PlayerCharacter's constructor.
     * 
     * @param basicStat , a map with the basic statistics.
     * @param name , the string name of the player character.
     */
    public PlayerCharacter(final EnumMap<Statistic, StatValuesImpl> basicStat, final String name) {
        super(basicStat, name);
    }

}
