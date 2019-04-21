package thedd.model.roomevent.interactableactionperformer;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.LogMessageType;
import thedd.model.combat.action.effect.HealingEffect;

/**
 * Specialization of {@link thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer}.
 * On interaction it fully heals the one or more character.
 */
public class ContraptionSanctuary extends AbstractInteractableActionPerformer implements Contraption {

    private static final String NAME = "Sanctuary";
    private static final Action ACTION;

    static {
        final String description = "A sanctuary which heals wayfares who stop by.";
        ACTION = new ActionBuilder().setName(NAME)
                                    .setCategory(ActionCategory.INTERACTABLE)
                                    .setBaseHitChance(1d)
                                    .setDescription(description)
                                    .setLogMessage(LogMessageType.CONTRAPTION_ACTION)
                                    .build();
        ACTION.addEffect(new HealingEffect(1.0));
    }

    /**
     * Create a new Sanctuary and set his Action to heal everybody in the target's party.
     */
    public ContraptionSanctuary() {
        super(NAME, ACTION.getCopy());
    }

    /**
     * 
     * @return
     *  a new instance of this Contraption
     */
    public static Contraption newInstance() {
        return new ContraptionSanctuary();
    }

    @Override
    public final boolean isSkippable() {
        return true;
    }
}
