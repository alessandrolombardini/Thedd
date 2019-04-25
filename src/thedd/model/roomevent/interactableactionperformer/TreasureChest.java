package thedd.model.roomevent.interactableactionperformer;

import java.util.Arrays;
import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.LogMessageTypeImpl;
import thedd.model.combat.action.effect.ItemGiverEffect;

/**
 * Specialization of {@link thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer}.
 * It gives to the user a random item.
 */
public class TreasureChest extends AbstractInteractableActionPerformer implements InteractableActionPerformer {

    private static final String NAME = "Treasure Chest";
    private static final String DESCRIPTION = "A chest that contains an unknown item.";

    /**
     * 
     */
    public TreasureChest() {
        super(NAME, new ActionBuilder().setName(NAME)
                                       .setCategory(ActionCategory.INTERACTABLE)
                                       .setBaseHitChance(1d)
                                       .setDescription(DESCRIPTION)
                                       .setEffects(Arrays.asList(new ItemGiverEffect()))
                                       .setLogMessage(LogMessageTypeImpl.TREASURE_ACTION)
                                       .build());
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
