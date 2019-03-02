package model.character;

import java.util.EnumMap;

/**
 * Class that implements a Goblin Non-Player Character.
 */
public final class GoblinNPC extends GenericCharacter {

    // Questi valori sono messi a caso e credo non abbiano assolutamente senso.
    private static final int BASIC_PV = 50;
    private static final int BASIC_COS = 30;
    private static final int BASIC_FOR = 30;
    private static final int BASIC_RIFL = 20;

    private static final String NAME = "Goblin";

    private static final EnumMap<Statistic, StatValues> BASIC_STAT = new EnumMap<Statistic, StatValues>(
            Statistic.class);

    static {
        BASIC_STAT.put(Statistic.PV, new StatValuesImpl(BASIC_PV));
        BASIC_STAT.put(Statistic.COS, new StatValuesImpl(BASIC_COS));
        BASIC_STAT.put(Statistic.FOR, new StatValuesImpl(BASIC_FOR));
        BASIC_STAT.put(Statistic.RIFL, new StatValuesImpl(BASIC_RIFL));
    }

    /**
     * Goblin's constructor.
     */
    private GoblinNPC() {
        super(NAME);
    }

    /**
     * Goblin's static factory.
     * 
     * @param multiplier it's the rate multiplied at the basic statistics.
     * @return a new instance of Goblin.
     */
    public static BasicCharacter createGoblinNPC(final int multiplier) {
        final BasicCharacter ret = new GoblinNPC();
        ret.setBasicStat(BASIC_STAT);
        // ret.addWeightedAction(new ActionImpl() , RandomActionPrority.DEFAULT);
        return ret;
    }
}
