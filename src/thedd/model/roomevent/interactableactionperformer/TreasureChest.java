package thedd.model.roomevent.interactableactionperformer;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageType;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.ItemGiverEffect;
import thedd.model.combat.action.targeting.DefaultTargeting;

/**
 * Specialization of {@link thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer}.
 * It gives to the user a random item.
 */
public class TreasureChest extends AbstractInteractableActionPerformer implements InteractableActionPerformer {

    private static final String NAME = "Treasure Chest";
    private static final Action ACTION;
    static {
        final String description = "A chest that contains an unknown item.";
        ACTION = new ActionImpl(NAME, ActionCategory.INTERACTABLE, 
                                new DefaultTargeting(), 1.0, 
                                TargetType.EVERYONE, description, 
                                LogMessageType.TREASURE_ACTION);
        ACTION.addEffect(new ItemGiverEffect());
    }

    /**
     * 
     */
    public TreasureChest() {
        super(NAME, ACTION.getCopy());
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
