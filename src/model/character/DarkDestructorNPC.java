package model.character;

import java.util.EnumMap;

/**
 * Class that implements a DarkDestructor Non-Player Character.
 */
public class DarkDestructorNPC extends AbstractCharacter {

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
     * DarkDestructor's constructor.
     * 
     * @param basicStat , a map with basic statistics.
     * @param name      , the name of the non-player character.
     */
    private DarkDestructorNPC() {
        super("Dark Destructor");
    }

    /**
     * DarkDestructor's static factory.
     * 
     * @param multiplier it's the rate multiplied at the basic statistics.
     * @return a new instance of DarkDestructor.
     */
    public static BasicCharacter createDarkDestructorNPC(final int multiplier) {
        final BasicCharacter ret = new DarkDestructorNPC();
        ret.setBasicStat(basicStat);
        // ret.addWeightedAction(new ActionImpl() , RandomActionPrority.DEFAULT);
        return ret;
    }
}
