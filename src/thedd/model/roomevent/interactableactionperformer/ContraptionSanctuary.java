package thedd.model.roomevent.interactableactionperformer;

import java.util.ArrayList;

import model.combat.enums.TargetType;
import model.combat.implementations.AbstractAction;
import model.combat.interfaces.Action;
import model.item.HealingEffect;

/**
 * Specialization of {@link thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer}.
 * On interaction it fully heals the one or more character.
 */
public class ContraptionSanctuary extends AbstractInteractableActionPerformer implements Contraption {

    private static final String NAME = "Sanctuary";
    private static final Action ACTION = new AbstractAction(null, "Sanctuary", new ArrayList<>(), 1.0, TargetType.EVERYONE) { };

    /**
     * Create a new Sanctuary and set his Action to heal everybody in the target's party.
     */
    public ContraptionSanctuary() {
        super(NAME, ACTION);
        ACTION.addEffect(new HealingEffect(Double.POSITIVE_INFINITY));
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
