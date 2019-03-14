package model.item.usableitem;

import java.util.ArrayList;

import model.combat.action.effect.DamageEffect;

public class UsableItemBomb extends UsableItemImpl {

    private static final int ID = 1;
    private static final String NAME = "Bomb";
    private static final String DESCRIPTION = "An explosive. Be careful while handing it";
    private static final double DAMAGE = 20.0;

    /**
     * 
     */
    public UsableItemBomb() {
        super(ID, NAME, new ArrayList<>(), DESCRIPTION);
        this.addActionEffect(new DamageEffect(DAMAGE));
    }
}
