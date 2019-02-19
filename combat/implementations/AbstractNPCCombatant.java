package combat.implementations;

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
        if (totalActionsProbabilities != 1.0) {
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
            }
        }
    }

    @Override
    public void setNextTarget(final List<ActionActor> availableTargets) {
        setRandomTarget(availableTargets);
    }

    protected void setRandomTarget(final List<ActionActor> availableTargets) {
        final Random random = new Random();
        final int targetIndex = random.nextInt(availableTargets.size());
        getAction().get().setTargets(availableTargets.get(targetIndex), availableTargets);
    }

}
