package thedd.model.combat.actionexecutor;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import thedd.model.character.BasicCharacter;
import thedd.model.character.types.DarkDestructor;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.result.ActionResult;
import thedd.model.combat.action.result.ActionResultImpl;
import thedd.model.combat.action.result.ActionResultType;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.actor.automatic.AutomaticActionActor;
import thedd.model.combat.instance.ActionExecutionInstance;
import thedd.model.combat.instance.CombatStatus;
import thedd.model.combat.instance.ExecutionInstanceImpl;
import thedd.model.combat.status.Status;
import thedd.model.combat.tag.ActionTag;

/**
 *  Logic of a default combat<p>
 *  By default, actors are sorted comparing their priority, obtained via {@link ActionActor#getPriority()},
 *  if the priority is found to be equal and one actor is already placed in the queue, then that actor takes
 *  priority.<p>
 *  Actors' statuses are also updated at the start of every actor's turn, their provided actions are executed
 *  first and, at the end of the actor's turn, if expired, statuses are removed and their expiring action is
 *  executed before passing to the next queued actor.<p>
 *  The action selected by and actor which has successfully parried an action
 */
public class DefaultCombatActionExecutor implements ActionExecutor {

    private Optional<Action> currentAction = Optional.empty();
    private Optional<ActionActor> currentActor = Optional.empty();
    private ActionExecutionInstance combatInstance = new ExecutionInstanceImpl();
    private Optional<ActionResult> currentActionResult = Optional.empty();
    private final List<Action> actionsQueue = new LinkedList<>(); //A queue of actions that will be executed before the current actor's one
    private final List<ActionActor> actorsQueue = new LinkedList<>();
    private final Comparator<ActionActor> actorsSortingOrder = new Comparator<ActionActor>() {
        @Override
        public int compare(final ActionActor a, final ActionActor b) {
            final int aPriority = a.getPriority();
            final int bPriority = b.getPriority();
            if (canActorAct(a) && !canActorAct(b)) {
                return -1;
            }
            if (!canActorAct(a) && canActorAct(b)) {
                return 1;
            }
            if (aPriority == bPriority) {
                if (actorsQueue.contains(a) && !actorsQueue.contains(b)) {
                    return -1;
                }
                if (actorsQueue.contains(b) && !actorsQueue.contains(a)) {
                    return 1;
                }
            }
            return bPriority - aPriority;
        }
    };

    /**
     * Public constructor.
     */
    public DefaultCombatActionExecutor() {
        this(Collections.<ActionActor>emptySet(), Collections.<ActionActor>emptySet());
    }

    /**
     * Public constructor.
     * @param hostileNPCs the List of Actors to placed in the party opposed to player's
     */
    public DefaultCombatActionExecutor(final Set<ActionActor> hostileNPCs) {
        this(hostileNPCs, Collections.<ActionActor>emptySet());
    }

    /**
     * Public constructor.
     * @param hostileNPCs the List of Actors to placed in the party opposed to player's
     * @param partyMembers the List of Actors to placed in the player's party
     */
    public DefaultCombatActionExecutor(final Set<ActionActor> hostileNPCs, final Set<ActionActor> partyMembers) {
        combatInstance.addNPCsPartyMembers(hostileNPCs);
        combatInstance.addPlayerPartyMembers(partyMembers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNextAction() {
        if (actionsQueue.isEmpty()) {
            if (!currentActor.isPresent()) {
                currentActor = Optional.of(actorsQueue.get(0));
                updateTurnStartStatuses(currentActor.get());
            }
            currentAction = currentActor.get().getNextQueuedAction();
        } else {
            currentAction = Optional.of(actionsQueue.get(0));
            currentActor = currentAction.get().getSource();
            actionsQueue.remove(0);
        }
        if (currentAction.isPresent()) {
            combatInstance.setCombatStatus(CombatStatus.ROUND_IN_PROGRESS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeCurrentAction() {
        if (currentAction.isPresent() && currentActionResult.isPresent()) {
            final Action action = currentAction.get();
            currentActionResult.get()
                               .getResults()
                               .stream()
                               .forEach(p -> {
                                   final ActionActor target = p.getKey();
                                   switch (p.getValue()) {
                                   case HIT:
                                       applyEffects(action, target);
                                       break;
                                   case MISSED:
                                       checkStatusResistance(action, target);
                                       break;
                                    case PARRIED:
                                        applyParry(target);
                                        break;
                                    default:
                                        break;
                                   }
                                });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ActionResult> evaluateCurrentAction() {
        if (currentAction.isPresent()) {
            final Action action = currentAction.get();
            final ActionResult result = new ActionResultImpl(action.getCopy());
            boolean interrupted = false;
            final List<ActionActor> targets = action.getTargets();
            if (!action.getRequirements().stream().allMatch(r -> r.isFulfilled(action))) {
                interrupted = true;
            }
            for (int i = 0; i < targets.size() && !interrupted; i++) {
                final ActionActor target = targets.get(i);
                action.rollToHit(target);
                if (action.isTargetHit()) {
                    result.addResult(target, ActionResultType.HIT);
                } else if (canTargetParry(target)) {
                    result.addResult(target, ActionResultType.PARRIED);
                    interrupted = true;
                } else {
                    result.addResult(target, ActionResultType.MISSED);
                }
            }
            currentActionResult = Optional.of(result);
        } else {
            currentActionResult = Optional.empty();
        }
        return currentActionResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateExecutionStatus() {
        combatInstance.getAllParties().forEach(a -> a.resetTurnInitiative());

        //Closes the combat instantly (without going trough the process of updating statuses)
        //if the player has won against the final boss
        if (combatInstance.getNPCsParty().stream().anyMatch(a -> a instanceof DarkDestructor)) {
            checkPlayerVictory();
        }

        if (combatInstance.getNumberOfAliveCharacters(combatInstance.getPlayerParty()) <= 0) {
            combatInstance.setCombatStatus(CombatStatus.PLAYER_LOST);
            return;
        }

        if (currentActor.isPresent() && currentActor.get().getActionQueue().isEmpty()) {
            final ActionActor actor = currentActor.get();
            updateExpiredStatuses(actor);
            if (currentActor.get().getActionQueue().isEmpty()) {
                actorsQueue.remove(actor);
                currentActor = Optional.empty();
            }
        }

        if (combatInstance.getCombatStatus() == CombatStatus.ROUND_PAUSED) {
            return;
        }

        if (actorsQueue.isEmpty() && actionsQueue.isEmpty() 
                && combatInstance.getCombatStatus() == CombatStatus.ROUND_IN_PROGRESS) {
            combatInstance.setCombatStatus(CombatStatus.ROUND_ENDED);
            combatInstance.getAllParties().forEach(ActionActor::resetSelectedAction);
            combatInstance.getAllParties().forEach(a -> a.getStatuses().forEach(s -> s.setIsUpdated(false)));
        }

        if (combatInstance.getCombatStatus() != CombatStatus.ROUND_IN_PROGRESS) {
            checkPlayerVictory();
        }

    }

    /**
     * {@inheritDoc}<p>
     * 
     * @throws IllegalArgumentException if the actor was already present in the queue
     */
    @Override
    public void addActorToQueue(final ActionActor actor) {
        if (actorsQueue.contains(actor)) {
            throw new IllegalArgumentException("The actor was already present in the queue");
        }

        if (combatInstance.getCombatStatus() == CombatStatus.ROUND_PAUSED) {
            actorsQueue.add(0, actor);
        } else {
            actorsQueue.add(actor);
            actorsQueue.sort(actorsSortingOrder);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startExecutor() { 
        combatInstance.setCombatStatus(CombatStatus.STARTED);
        combatInstance.getAllParties().forEach(a -> a.setIsInCombat(true));
        prepareNextRound();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepareNextRound() {
        combatInstance.increaseRoundNumber();
        setNextAIMoves();
        final List<ActionActor> orderedActors = getOrderedActorsList();
        IntStream.range(0, combatInstance.getAllParties().size()).forEach(i -> orderedActors.get(i).setTurnInitiative(i + 1));
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
    public CombatStatus getExecutionStatus() {
        return combatInstance.getCombatStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRoundReady() {
        final List<ActionActor> availableActors = Stream.concat(combatInstance.getPlayerParty().stream(),
                                                                combatInstance.getNPCsParty().stream())
                                                                .filter(a -> canActorAct(a))
                                                                .collect(Collectors.toList());
        return combatInstance.getCombatStatus() == CombatStatus.ROUND_PAUSED
                || actorsQueue.containsAll(availableActors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getOrderedActorsList() {
        return Collections.unmodifiableList(combatInstance.getAllParties().stream()
                                                                           .sorted(actorsSortingOrder)
                                                                           .collect(Collectors.toList()));
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
        if (actor instanceof BasicCharacter) {
            return ((BasicCharacter) actor).isAlive(); //Could also check for tags like stunned and whatnot
        } else {
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ActionResult> getLastActionResult() {
        return currentActionResult;
    }

    /**
     * Tells all the actors in the hostile party to set their next
     * actions and targets.
     */
    protected void setNextAIMoves() {
        final List<ActionActor> availableNPCs = combatInstance.getNPCsParty().stream()
                                                                             .filter(this::canActorAct)
                                                                             .collect(Collectors.toList());
        for (final ActionActor npc : availableNPCs) {
            if (npc instanceof AutomaticActionActor) {
                setNextAIMove((AutomaticActionActor) npc);
            } else {
                throw new IllegalStateException("Only AutomaticActionActors are allowed in the NPCs party");
            }
        }
    }

    /**
     * Tells an Actor to set its next action and targets.
     * @param actor the actor that will prepare its next move
     */
    protected void setNextAIMove(final AutomaticActionActor actor) {
        actor.selectNextMove(combatInstance.getCopy());
        addActorToQueue(actor);
    }

    /**
     * Determines whether a target actor can actively block the 
     * current action.
     * @param target the target to be tested
     * @return true of the target can parry the action, false otherwise
     */
    protected boolean canTargetParry(final ActionActor target) {
        /*An action can be blocked only if:
         * -The action is not unblockable
         * -The action is offensive
         * -The target of the action is not the actor executing it
         * -The action is not of type STATUS
         * -The target's action is of type parry
        */
        return target.getSelectedAction().isPresent()
                && !currentAction.get().getTags().contains(ActionTag.UNBLOCKABLE)
                && currentAction.get().getTags().contains(ActionTag.OFFENSIVE)
                && currentAction.get().getTargetType() != TargetType.SELF
                && currentAction.get().getCategory() != ActionCategory.STATUS
                && target.getSelectedAction().get().getTags().contains(ActionTag.PARRY);
    }

    private void applyParry(final ActionActor target) {
        target.resetActionsQueue();
        actorsQueue.remove(target);
        if (combatInstance.getNPCsParty().contains(target)) { //Maybe check if target is Automatic AND his behavior is active
            setNextAIMove((AutomaticActionActor) target);
        } else {
            combatInstance.setCombatStatus(CombatStatus.ROUND_PAUSED);
        }
    }

    private void checkStatusResistance(final Action action, final ActionActor target) {
        if (action.getCategory() == ActionCategory.STATUS
                && target.equals(action.getSource().get())) {
                final ActionActor afflictedActor = action.getSource().get();
                afflictedActor.getStatuses().stream()
                                             .filter(s -> s.getAction().isPresent())
                                             .filter(s -> s.getAction().get().equals(action))
                                             .findFirst()
                                             .ifPresent(this::depleteStatus);
        }
    }

    private void applyEffects(final Action action, final ActionActor target) {
        action.applyEffects(target);
        if (target instanceof BasicCharacter
                && !((BasicCharacter) target).isAlive()) {
            actorsQueue.remove(target);
        }
        updateNewlyAppliedStatuses(target);
    }

    private void updateTurnStartStatuses(final ActionActor actor) {
        actor.getStatuses().stream()
                           .filter(s -> !s.isUpdated())
                           .forEach(s -> {
                               s.update(combatInstance);
                               if (s.getCurrentDuration() >= 0 || s.isPermanent()) {
                                   s.getAction().ifPresent(a -> {
                                           actor.insertActionIntoQueue(0, a, false);
                                   });
                               }
                           });
    }

    private void updateExpiredStatuses(final ActionActor actor) {
        final List<Status> toRemove = actor.getStatuses().stream()
                                                         .filter(s -> s.getCurrentDuration() <= 0)
                                                         .collect(Collectors.toList());
        toRemove.forEach(s -> {
            s.update(combatInstance);
            s.getAction().ifPresent(a -> {
                actor.addActionToQueue(a, false);
                actor.removeStatus(s);
            });
        });
    }

    private void updateNewlyAppliedStatuses(final ActionActor target) {
        target.getStatuses().stream()
                            .filter(s -> !s.isUpdated())
                            .filter(s -> s.getBaseDuration() == s.getCurrentDuration())//this means that they must have been applied just now
                            .forEach(s -> {
                                s.update(combatInstance);
                                s.getAction().ifPresent(actionsQueue::add);
                            });
    }

    private void depleteStatus(final Status s) {
        while (s.getCurrentDuration() != 0 && !s.isPermanent()) {
            s.update(combatInstance);
        }
    }

    private void checkPlayerVictory() {
        if (combatInstance.getNumberOfAliveCharacters(combatInstance.getNPCsParty()) <= 0) {
            combatInstance.setCombatStatus(CombatStatus.PLAYER_WON);
            combatInstance.getPlayerParty().forEach(a -> {
                a.setIsInCombat(false);
                a.resetActionsQueue();
            });
            return;
        }
    }

}
