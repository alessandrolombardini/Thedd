package model.character;

import java.util.EnumMap;

/**
 * Class that implements a Goblin Non-Player Character.
 */
public class GoblinNPC extends AbstractCharacter {

    /**
     * Headless NonPlayerCharacter's constructor.
     * 
     * @param basicStat , a map with basic statistics.
     * @param name      , the name of the non-player character.
     */
    public GoblinNPC(final EnumMap<Statistic, StatValuesImpl> basicStat, final String name) {
        super(basicStat, name);
    }
}
