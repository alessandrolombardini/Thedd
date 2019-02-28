package model.character;

import java.util.EnumMap;

/**
 * Class that Implements a Player Character.
 */
public class PlayerCharacter extends AbstractCharacter {
    // Questi valori sono messi a caso e credo non abbiano assolutamente senso.
    private static final int basicPV = 50;
    private static final int basicCOS = 30;
    private static final int basicFOR = 30;
    private static final int basicRIFL = 20;

    private static final EnumMap<Statistic, StatValues> basicStat = new EnumMap<Statistic, StatValues>(Statistic.class);

    static {
        basicStat.put(Statistic.PV, new StatValuesImpl(basicPV));
        basicStat.put(Statistic.COS, new StatValuesImpl(basicCOS));
        basicStat.put(Statistic.FOR, new StatValuesImpl(basicFOR));
        basicStat.put(Statistic.RIFL, new StatValuesImpl(basicRIFL));
    }

    /**
     * PlayerCharacter's constructor.
     * 
     * @param name , the string name of the player character.
     */
    private PlayerCharacter(final String name) {
        super(name);
    }

    /**
     * PlayerCharacter's static factory.
     * 
     * @param name the name chosen by the player.
     * @return a new instance of Player Character
     */
    public static BasicCharacter createPlayerCharacter(final String name) {
        final BasicCharacter ret = new PlayerCharacter(name);
        ret.setBasicStat(basicStat);
        //ret.addWeightedAction(new ActionImpl(), RandomActionPrority.DEFAULT);
        return ret;
    }

}
