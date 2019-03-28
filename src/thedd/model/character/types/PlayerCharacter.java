package thedd.model.character.types;

import java.util.EnumMap;

import thedd.model.character.BasicCharacterImpl;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.StatValuesImpl;
import thedd.model.character.statistics.Statistic;

/**
 * Class that Implements a Player Character.
 */
public class PlayerCharacter extends BasicCharacterImpl {

    // Questi valori sono messi a caso e credo non abbiano assolutamente senso.
    private static final int BASIC_PV = 50;
    private static final int BASIC_COS = 30;
    private static final int BASIC_FOR = 30;
    private static final int BASIC_RIFL = 20;
    private final EnumMap<Statistic, StatValues> basicStat = new EnumMap<Statistic, StatValues>(Statistic.class);

    /**
     * PlayerCharacter's constructor.
     * 
     * @param name the string name of the player character.
     */
    public PlayerCharacter(final String name) {
        super(name);
        initStat();
        this.setBasicStat(basicStat);
        // ret.addWeightedAction(new ActionImpl(), RandomActionPrority.DEFAULT);
    }

    private void initStat() {
        basicStat.put(Statistic.PV, new StatValuesImpl(BASIC_PV));
        basicStat.put(Statistic.COS, new StatValuesImpl(BASIC_COS));
        basicStat.put(Statistic.FOR, new StatValuesImpl(BASIC_FOR));
        basicStat.put(Statistic.RIFL, new StatValuesImpl(BASIC_RIFL));
    }
}
