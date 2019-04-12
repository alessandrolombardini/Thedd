package thedd.model.roomevent.interactableactionperformer;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageType;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.DamageEffect;
import thedd.model.combat.action.targeting.DefaultTargeting;


/**
 * Specialization if {@link thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer}.
 * It deals damage to the target. 
 */
public class ContraptionTrap extends AbstractInteractableActionPerformer implements Contraption {

    private static final String NAME = "Trap";
    private static final Action ACTION;
    private static final double BASE_DAMAGE = 20.0;

    static {
        final String description = "A nasty contraption which hurts who fail to avoid it.";
        ACTION = new ActionImpl(NAME, ActionCategory.INTERACTABLE,
                                 new DefaultTargeting(), 1.0, 
                                 TargetType.EVERYONE, description,
                                 LogMessageType.CONTRAPTION_ACTION);
        ACTION.addEffect(new DamageEffect(BASE_DAMAGE));
    }

    /**
     * Create a new Trap and set the action.
     */
    public ContraptionTrap() {
        super(NAME, ACTION.getCopy());
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
