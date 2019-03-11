package model.room_event;

import java.util.Arrays;

import model.combat.enums.TargetType;
import model.combat.implementations.AbstractAction;
import model.combat.interfaces.Action;

/**
 * Specialization of {@link model.room_event.InteractableActionPerformer}.
 * It gives to the interactor a random item.
 */
public class TreasureChest extends AbstractInteractableActionPerformer implements InteractableActionPerformer {

    private static final String NAME = "Treasure Chest";
    private static final Action ACTION = new AbstractAction(null, NAME, Arrays.asList(new ItemGiverEffect()), 1, TargetType.EVERYONE) { };

    /**
     * 
     */
    public TreasureChest() {
        super(NAME, ACTION);
    }

    /**
     * 
     * @return
     *  a new instance of TreasureChest.
     */
    public static InteractableActionPerformer newInstance() {
        return new TreasureChest();
    }

    @Override
    public final boolean isSkippable() {
        return true;
    }
}
