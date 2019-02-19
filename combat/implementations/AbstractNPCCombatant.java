package combat.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import combat.interfaces.Action;
import combat.interfaces.ActionActor;
import combat.interfaces.NPCAction;
import combat.interfaces.NPCCombatant;

public abstract class AbstractNPCCombatant extends AbstractCombatant implements NPCCombatant {

    private List<NPCAction> actionList;

    public AbstractNPCCombatant(final String name) {
        super(name);
    }

    @Override
    public void setNextAction() {
        setNextAIAction();
    }

    @Override
    public void setAvailableActionsList(final List<? extends Action> actions) {
        super.setAvailableActionsList(actions);
        actionList = getAvailableActionsList()
                                    .stream()
                                    .map(a -> (NPCAction)a)
                                    .collect(Collectors.toList());
        final double totalActionsProbabilities = actionList.stream()
                .mapToDouble(a -> a.getPickChanceUpperBound())
                .sum();
        if(totalActionsProbabilities != 1.0) {
            throw new IllegalStateException("Actions pick chance's upper bounds do not amount to 1");
        }
    }

    @Override
    public void setNextAIAction() {
        final Random dice = new Random();
        final double diceRoll = dice.nextDouble();
        for (final NPCAction action : actionList) {
            if (diceRoll > action.getPickChanceLowerBound() && diceRoll < action.getPickChanceUpperBound()) {
                setAction(action);
                setRandomTarget();
            }
        }
    }

    protected void setRandomTarget() {
        List<ActionActor> targetParty = new ArrayList<>();
        switch (getAction().get().getTargetType()) {
        case ALLY:
            targetParty = getCombatInstance().getNPCsParty();
            break;
        case EVERYONE:
            targetParty = getCombatInstance().getAllParties();
            break;
        case FOE:
            targetParty = getCombatInstance().getPlayerParty();
            break;
        case SELF:
            targetParty = getCombatInstance().getNPCsParty();
            break;
        default:
            throw new IllegalStateException("Action target type could not be found");
        }
        final Random random = new Random();
        final int targetIndex = random.nextInt(targetParty.size());
        setTargets(targetParty.get(targetIndex));
    }

}
