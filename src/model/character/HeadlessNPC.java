package model.character;

import java.util.EnumMap;


/**
 * Class that implements a Headless Non-Player Character.
 */
public class HeadlessNPC extends AbstractCharacter {

    /**
     * Headless NonPlayerCharacter's constructor.
     * 
     * @param basicStat , a map with basic statistics.
     * @param name      , the name of the non-player character.
     */
    public HeadlessNPC(final EnumMap<Statistic, StatValuesImpl> basicStat, final String name) {
        super(basicStat, name);
    }

}
