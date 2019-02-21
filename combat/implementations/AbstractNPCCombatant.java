package combat.implementations;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import combat.interfaces.Action;
import combat.interfaces.ActionActor;
import combat.interfaces.AutomaticActionActor;
import combat.interfaces.NPCAction;

public abstract class AbstractNPCCombatant extends AbstractCombatant implements AutomaticActionActor {

    private List<NPCAction> actionList;

    public AbstractNPCCombatant(final String name) {
        super(name);
    }

    @Override
    public void setAvailableActionsList(final List<? extends Action> actions) {
        super.setAvailableActionsList(actions);
        actionList = getAvailableActionsList()
                                    .stream()
                                    .map(a -> (NPCAction) a)
                                    .collect(Collectors.toList());
        final double totalActionsProbabilities = actionList.stream()
                .mapToDouble(a -> a.getPickWeight())
                .sum();
        if (totalActionsProbabilities != 1.0) {
            throw new IllegalStateException("Actions pick chance's upper bounds do not amount to 1");
        }
    }

    @Override
    public void setNextAction() {
        //Code taken from https://stackoverflow.com/questions/6737283/weighted-randomness-in-java
        // Compute the total weight of all items together
        double random = 0d;
        double totalWeight = 0.0d;
        for (final NPCAction a : actionList) {
            totalWeight += a.getPickWeight();
        }

        // Now choose a random item
        while (random == 0d) {
            random = Math.random() * totalWeight;
        }
        for (final NPCAction a : actionList) {
            random -= a.getPickWeight();
            if (random <= 0d) {
                setAction(a);
                break;
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
