package model.combat.actionexecutor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import model.combat.action.Action;
import model.combat.action.result.ActionResult;
import model.combat.action.result.ActionResultImpl;
import model.combat.action.result.ActionResultType;
import model.combat.actor.ActionActor;
import model.combat.instance.ActionExecutionInstance;
import model.combat.instance.ExecutionInstaceImpl;
import model.combat.instance.CombatStatus;
import model.combat.status.Status;

/**
 * Updates the {@link Status} of all the {@link ActionActor} present
 * in the {@link ActionExecutionInstance}, executes their provided
 * actions and, if necessary, removes them.<p>
 * It goes through only one round.
 */
public class StatusUpdateActionExecutor implements ActionExecutor {

    private ActionExecutionInstance instance = new ExecutionInstaceImpl();
    private boolean done = false;
    private final List<Status> queue = new ArrayList<>();
    private Optional<Status> currentStatus = Optional.empty();
    private Optional<Action> currentAction = Optional.empty();
    private Optional<ActionResult> currentActionResult = Optional.empty();
    private Iterator<Status> iterator;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExecutionInstance(final ActionExecutionInstance newInstance) {
        instance = newInstance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startExecutor() {
        instance.setCombatStatus(CombatStatus.STARTED);
        instance.getAllParties().forEach(a -> {
            queue.addAll(a.getStatuses());
        });
        iterator = queue.iterator();
        prepareNextRound();
    }

    /**
     * Does nothing.
     */
    @Override
    public void prepareNextRound() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeCurrentAction() {
        if (currentActionResult.isPresent()) {
            final Action action = currentAction.get();
            currentActionResult.get().getResults().stream()
                                                  .filter(p -> p.getValue() == ActionResultType.HIT)
                                                  .forEach(p -> {
                                                      final ActionActor target = p.getKey();
                                                      action.applyEffects(target);
                                                      //TODO: check if the target is k.o.
                                                  });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNextAction() {
        if (iterator.hasNext() && !currentAction.isPresent()) {
            currentStatus = Optional.of(iterator.next());
            currentStatus.get().update(instance);
        }
        if (!currentAction.isPresent() && currentStatus.isPresent()) {
            currentAction = currentStatus.get().getAction();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ActionResult> evaluateCurrentAction() {
        instance.setCombatStatus(CombatStatus.ROUND_IN_PROGRESS);
        if (!currentAction.isPresent()) {
            currentActionResult = Optional.empty();
        } else {
            final Action action = currentAction.get();
            final ActionResult result = new ActionResultImpl(action);
            final List<ActionActor> targets = action.getTargets();
            targets.forEach(target -> {
                action.rollToHit(target);
                if (action.isTargetHit()) {
                    result.addResult(target, ActionResultType.HIT);
                } else {
                    result.addResult(target, ActionResultType.MISSED);
                }
            });
            currentActionResult = Optional.of(result);
        }
        return currentActionResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateExecutionStatus() {
        currentAction = Optional.empty();
        //FIRST CHECK IF THE PARTY IS ALIVE
        currentStatus.ifPresent(status -> {
            if (status.getCurrentDuration() <= 0) {
                status.update(instance);
                currentAction = status.getAction();
                status.getAfflictedActor().get().removeStatus(status);
                currentStatus = Optional.empty();
            }
        });

        if (!iterator.hasNext() && !currentAction.isPresent()) {
            done = true;
            queue.forEach(s -> s.setIsUpdated(false));
            instance.setCombatStatus(CombatStatus.COMBAT_ENDED);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CombatStatus getExecutionStatus() {
        return instance.getCombatStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addActorToQueue(final ActionActor actor) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRoundReady() {
        return !done;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getOrderedActorsList() {
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionExecutionInstance getExecutionInstance() {
        return instance;
    }

}
