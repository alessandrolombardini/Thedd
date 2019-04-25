package thedd.model.combat.action.targeting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import thedd.model.character.BasicCharacter;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.instance.ActionExecutionInstance;

/**
 *  Default implementation of the {@link ActionTargeting}
 *  interface.
 */
public class DefaultTargeting implements ActionTargeting {

    /**
     * It targets only the main provided target.
     */
    @Override
    public List<ActionActor> getTargets(final ActionActor target, final List<ActionActor> availableTargets) {
        final List<ActionActor> targets = new ArrayList<>();
        targets.add(Objects.requireNonNull(target));
        return targets;
    }

    /**
     * Returns as valid targets:<br>
     * -The party of the {@link ActionActor} executing the
     *  action if the {@link TargetType} is ALLY<br>
     * -The party opposed to the one of the actor executing
     *  the action if the TargetType is FOE<br>
     * -Every actor in the instance if the TargetType is
     *  EVERYONE<br>
     * -The actor executing the action if the TargetType is
     *  SELF.
     */
    @Override
    public List<ActionActor> getValidTargets(final ActionExecutionInstance combatInstance, final Action sourceAction) {
        final ActionActor source = sourceAction.getSource().get();
        final TargetType targetType = sourceAction.getTargetType();
        List<ActionActor> targets = new ArrayList<>();
        switch (targetType) {
        case ALLY:
            targets = combatInstance.getNPCsParty().contains(source) ? combatInstance.getPlayerParty() 
                            : combatInstance.getNPCsParty();
            break;
        case EVERYONE:
            targets = combatInstance.getAllParties();
            break;
        case FOE:
            targets =  combatInstance.getPlayerParty().contains(source) ? combatInstance.getNPCsParty()
                            : combatInstance.getPlayerParty();
            break;
        case SELF:
            return Collections.singletonList((ActionActor) source);
        default:
            throw new IllegalStateException("Target type of the action was not found");
        }
        return targets.stream()
                      .filter(a -> !(a instanceof BasicCharacter) 
                              || (a instanceof BasicCharacter && ((BasicCharacter) a).isAlive()))
                      .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionTargeting getCopy() {
        return new DefaultTargeting();
    }

}
