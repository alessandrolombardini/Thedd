package model.item;

import java.util.ArrayList;

import model.character.Statistic;

/**
 * One handed weapon.
 *
 */
public class EquipableItemSword extends EquipableItemImpl {

    private static final int ID = -1;
    private static final String NAME = "Sword";
    private static final EquipableItemType TYPE = EquipableItemType.ONE_HANDED;
    private static final String DESCRIPTION = "A sword";

    /**
     * Create a Sword instance and add effects.
     */
    public EquipableItemSword() {
        super(ID, NAME, TYPE, new ArrayList<>(), DESCRIPTION);
        this.addActionEffect(new EquipementStatisticBonusEffect(Statistic.FOR, 1));
    }
}
