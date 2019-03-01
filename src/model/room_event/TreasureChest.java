package model.room_event;

import java.util.Arrays;

import model.combat.enums.TargetType;
import model.combat.implementations.AbstractAction;
import model.combat.interfaces.Action;

/**
 * 
 *
 */
public class TreasureChest extends ContraptionImpl implements Contraption {

    private static final String NAME = "Treasure Chest";
    private static final Action ACTION = new AbstractAction(null, NAME, Arrays.asList(new ItemGiverEffect()), 1, TargetType.EVERYONE) { };
    /**
     * 
     */
    public TreasureChest() {
        super(NAME, ACTION);
    }

    public static Contraption newInstance() {
        return new TreasureChest();
    }
}
