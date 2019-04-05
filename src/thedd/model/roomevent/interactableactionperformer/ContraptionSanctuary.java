package thedd.model.roomevent.interactableactionperformer;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageType;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.targeting.DefaultTargeting;

/**
 * Specialization of {@link thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer}.
 * On interaction it fully heals the one or more character.
 */
public class ContraptionSanctuary extends AbstractInteractableActionPerformer implements Contraption {

    private static final String NAME = "Sanctuary";
    private static final Action ACTION;

    static {
        final String description = "";
        ACTION = new ActionImpl(NAME, ActionCategory.INTERACTABLE, 
                                 new DefaultTargeting(), 1.0, 
                                 TargetType.EVERYONE, description, 
                                 LogMessageType.CONTRAPTION_ACTION);
        //TODO ACTION.addEffect(new HealingEffect(1.0));
        
    }
    
    /**
     * Create a new Sanctuary and set his Action to heal everybody in the target's party.
     */
    public ContraptionSanctuary() {
        super(NAME, ACTION);
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
