package thedd.model.combat.actionexecutor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import thedd.model.character.BasicCharacter;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.result.ActionResult;
import thedd.model.combat.action.result.ActionResultImpl;
import thedd.model.combat.action.result.ActionResultType;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.instance.ActionExecutionInstance;
import thedd.model.combat.instance.CombatStatus;
import thedd.model.combat.status.Status;

/**
 *  Executes the provided action and the actions of the {@link Status} that
 *  may have been applied by the main action.<p>
 *  Only executes one round.<p>
 *
 *  This executor does not support the addActorToQueue method.
 */
public class OutOfCombatActionExecutor implements ActionExecutor {

    private Optional<Action> action = Optional.empty();
    private ActionExecutionInstance combatInstance;
    private Optional<ActionResult> currentActionResult = Optional.empty();
    private Optional<Status> currentStatus = Optional.empty();
    private final List<Status> statusQueue = new ArrayList<>();
    private Iterator<Status> iterator;
    private boolean roundStarted;

    /**
     * @param action the action to be executed
     */
    public OutOfCombatActionExecutor(final Action action) {
        this.action = Optional.of(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExecutionInstance(final ActionExecutionInstance newInstance) {
        combatInstance = newInstance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startExecutor() {
        combatInstance.increaseRoundNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepareNextRound() {
        getExecutionInstance().setCombatStatus(CombatStatus.STARTED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeCurrentAction() {
        if (currentActionResult.isPresent()) {
            currentActionResult.get().getResults().stream()
                                                  .filter(p -> p.getValue() == ActionResultType.HIT)
                                                  .forEach(p -> {
                                                      final ActionActor target = p.getKey();
                                                      action.get().applyEffects(target);
                                                      if (target instanceof BasicCharacter
                                                              && !((BasicCharacter) target).isAlive()) {
                                                          statusQueue.removeAll(target.getStatuses());
                                                      } else {
                                                          addNewlyAppliedStatuses(target);
                                                      }
                                                  });
            if (iterator == null) {
                iterator = statusQueue.iterator();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNextAction() {
        if (iterator != null && iterator.hasNext() && !action.isPresent()) {
            currentStatus = Optional.of(iterator.next());
            currentStatus.get().update(combatInstance);
            action = currentStatus.get().getAction();
        } else {
            currentStatus = Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ActionResult> evaluateCurrentAction() {
        combatInstance.setCombatStatus(CombatStatus.ROUND_IN_PROGRESS);
        roundStarted = true;
        if (action.isPresent()) {
            final Action a = action.get();
            final ActionResult result = new ActionResultImpl(a);
            final List<ActionActor> targets = a.getTargets();
            targets.forEach(target -> {
                a.rollToHit(target);
                if (a.isTargetHit()) {
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
        if (combatInstance.getNumberOfAliveCharacters(combatInstance.getPlayerParty()) <= 0) {
            combatInstance.setCombatStatus(CombatStatus.PLAYER_LOST);
            return;
        } 

        action = Optional.empty();
        if (currentStatus.isPresent() && currentStatus.get().getCurrentDuration() <= 0) {
            currentStatus.get().update(combatInstance);
            action = currentStatus.get().getAction();
            currentStatus.get().getAfflictedActor().get().removeStatus(currentStatus.get());
        }
        if (!iterator.hasNext() && !action.isPresent()) {
            combatInstance.getAllParties().forEach(a -> a.getStatuses().forEach(s -> s.setIsUpdated(false)));
            combatInstance.getAllParties().forEach(a -> a.resetActionsQueue());
            combatInstance.setCombatStatus(CombatStatus.COMBAT_ENDED);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CombatStatus getExecutionStatus() {
        return combatInstance.getCombatStatus();
    }

    /**
     * Does nothing: no actors can be added during this execution.
     */
    @Override
    public void addActorToQueue(final ActionActor actor) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRoundReady() {
        return !roundStarted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getOrderedActorsList() {
        return combatInstance.getAllParties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionExecutionInstance getExecutionInstance() {
        return combatInstance.getCopy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canActorAct(final ActionActor actor) {
        return !roundStarted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ActionResult> getLastActionResult() {
        return currentActionResult;
    }

    private void addNewlyAppliedStatuses(final ActionActor target) {
        target.getStatuses().stream()
                            .filter(s -> !s.isUpdated())
                            .filter(s -> s.getBaseDuration() == s.getCurrentDuration())//this means that they must have been applied just now
                            .forEach(s -> {
                                statusQueue.add(s);
                            });
    }

}
