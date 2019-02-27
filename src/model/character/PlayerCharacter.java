package model.character;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import model.combat.enums.RandomActionPrority;
import model.combat.implementations.ActionImpl;
import model.combat.interfaces.Action;

/**
 * Class that Implements a Player Character.
 */
public class PlayerCharacter extends AbstractCharacter {
    // Questi valori sono messi a caso e credo non abbiano assolutamente senso.
    // Datemi dei consigli su come farli meglio thankyouuuu
    private static final int basicPV = 50;
    private static final int basicCOS = 30;
    private static final int basicFOR = 30;
    private static final int basicRIFL = 20;

    private static final EnumMap<Statistic, StatValues> basicStat = new EnumMap<Statistic, StatValues>(Statistic.class);
    private static final List<Action> basicActions = new ArrayList<>();
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
     * @param name       the name chosen by the player.
     * @param multiplier it's the rate multiplied at the basic statistics.
     * @return
     */
    public static BasicCharacter createPlayerCharacter(final String name) {
        final BasicCharacter ret = new PlayerCharacter(name);
        ret.setBasicStat(basicStat);
        ret.addWeightedAction(new ActionImpl()/*Devo ancora capire bene*/ , RandomActionPrority.DEFAULT);
        return ret;
    }

}
