package model.character;

import java.util.List;

import model.item.Item;

public class GenericCharacter implements Character {

    @Override
    public void updateStat(Statistic stat, int value) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isAlive() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public StatValues getStat(Statistic stat) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Action> getActions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void equipItem(int itemid) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeItem(int itemId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Item> getEquippedItems() {
        // TODO Auto-generated method stub
        return null;
    }

}
