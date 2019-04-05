package thedd.model.roomevent.interactableactionperformer;

import java.util.ArrayList;

import model.combat.enums.TargetType;
import model.combat.implementations.AbstractAction;
import model.combat.implementations.DamageEffect;
import model.combat.interfaces.Action;

/**
 * Specialization if {@link thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer}.
 * It deals damage to the target. 
 */
public class ContraptionTrap extends AbstractInteractableActionPerformer implements Contraption {

    private static final String NAME = "Trap";
    private static final Action ACTION = new AbstractAction(null, NAME, new ArrayList<>(), 1.0, TargetType.EVERYONE) { }; 
    private static final double BASE_DAMAGE = 20.0;

    /**
     * Create a new Trap and set the action.
     */
    public ContraptionTrap() {
        super(NAME, ACTION);
        this.getAction().get().addEffect(new DamageEffect(BASE_DAMAGE));
    }

    /**
     * 
     * @return
     *  a new instance of this contraption
     */
    public static Contraption newInstance() {
        return new ContraptionTrap();
    }

    @Override
    public final boolean isSkippable() {
        return true;
    }
}
