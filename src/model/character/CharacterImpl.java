package model.character;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import model.item.Item;

/**
 * Class that define a Generic Character.
 */
public class CharacterImpl implements Character {

    private final EnumMap<Statistic, StatValues> stat;
    private final List<Action> actions;

    /**
     * GenericCharacter's constructor.
     * @param basicStat , a map with the basic statistic values of the character.
     * @param basicActions , a list of the possible actions of the character.
     */
    public CharacterImpl(final EnumMap<Statistic, StatValues> basicStat, final List<Action> basicActions) {
        this.stat = new EnumMap<>(basicStat);
        this.actions = new ArrayList<>(basicActions);
    }

    @Override
    public final void updateStat(final Statistic stat, final int value) {
        this.stat.get(stat).updateActual(value);
    }

    @Override
    public final boolean isAlive() {
        return (this.stat.get(Statistic.PV).getActual() > 0 ? true : false);
    }

    @Override
    public final StatValues getStat(final Statistic stat) {
        return this.stat.get(stat);
    }

    @Override
    public final List<Action> getActions() {
        return this.actions;
    }

    @Override
    public final void equipItem(final int itemid) {
        //to-do
    }

    @Override
    public final void removeItem(final int itemId) {
        //to-do
    }

    @Override
    public final List<Item> getEquippedItems() {
        return null; //to-do
    }

}
