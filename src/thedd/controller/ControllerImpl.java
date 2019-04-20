package thedd.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import javafx.application.Platform;
import thedd.controller.information.PlayerInformation;
import thedd.controller.information.PlayerInformationImpl;
import thedd.controller.information.StatisticsInformation;
import thedd.controller.information.StatisticsInformationImpl;
import thedd.model.Model;
import thedd.model.ModelImpl;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.result.ActionResult;
import thedd.model.combat.actionexecutor.ActionExecutor;
import thedd.model.combat.actionexecutor.OutOfCombatActionExecutor;
import thedd.model.combat.actionexecutor.StatusUpdateActionExecutor;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.encounter.HostileEncounter;
import thedd.model.combat.instance.ActionExecutionInstance;
import thedd.model.combat.instance.CombatStatus;
import thedd.model.combat.instance.ExecutionInstanceImpl;
import thedd.model.character.BasicCharacter;
import thedd.model.character.statistics.Statistic;
import thedd.model.item.Item;
import thedd.model.item.ItemFactory;
import thedd.model.item.usableitem.UsableItem;
import thedd.model.roomevent.RoomEvent;
import thedd.model.roomevent.combatevent.CombatEvent;
import thedd.model.world.environment.EnvironmentImpl;
import thedd.model.world.environment.Session;
import thedd.model.world.environment.SessionImpl;
import thedd.model.world.floor.FloorDetails;
import thedd.model.world.room.Room;
import thedd.view.View;

/**
 * Implementation of the {@link Controller}.
 */
public class ControllerImpl implements Controller {

    private final View view;
    private final Model model;
    private PlayerInformation playerInfo;
    private StatisticsInformation statisticsInfo;
    private Optional<ActionExecutor> actionExecutor = Optional.empty();

    /**
     * Create a new Controller instance.
     * 
     * @param view view reference
     */
    public ControllerImpl(final View view) {
        Objects.requireNonNull(view);
        this.view = view;
        this.model = new ModelImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean newGame(final String playerName, final String numberOfRooms, final String numberOfFloors) {
        if (this.isValidNumberOfRooms(numberOfRooms) && this.isValidNumberOfFloors(numberOfFloors)) {
            final int numOfRooms = Integer.parseInt(numberOfRooms);
            final int numOfFloors = Integer.parseInt(numberOfFloors);
            final Session session = new SessionImpl(Optional.ofNullable(playerName), numOfFloors, numOfRooms);
            this.model.setSession(session);
            this.playerInfo = new PlayerInformationImpl(this.model.getSession().getPlayerCharacter());
            this.statisticsInfo = new StatisticsInformationImpl(this.model.getSession().getPlayerCharacter());

            BasicCharacter charac = this.model.getSession().getPlayerCharacter();
            IntStream.range(0, 15).forEach(i -> {
                charac.getInventory().addItem(ItemFactory.getRandomItem());
            });

            return true;
        }
        return false;
    }

    @Override
    public final boolean isValidNumberOfRooms(final String numberOfRooms) {
        if (this.checkNumber(numberOfRooms)) {
            final int numOfRooms = Integer.parseInt(numberOfRooms);
            return numOfRooms >= EnvironmentImpl.MIN_NUMBER_OF_ROOMS;
        }
        return false;
    }

    @Override
    public final boolean isValidNumberOfFloors(final String numberOfFloors) {
        if (this.checkNumber(numberOfFloors)) {
            final int numOfFloors = Integer.parseInt(numberOfFloors);
            return numOfFloors >= EnvironmentImpl.MIN_NUMBER_OF_FLOORS;
        }
        return false;
    }

    private boolean checkNumber(final String number) {
        return !number.isEmpty() && number.chars().allMatch(Character::isDigit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void closeApplication() {
        Platform.exit();
    }

    // -------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCombatActive() {
        return this.model.getSession().getPlayerCharacter().isInCombat();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteItem(final Item item) {
        this.model.getSession().getPlayerCharacter().getInventory().removeItem(item);
        this.view.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useItem(final Item item) {
        if (item.isUsable()) {
            final UsableItem usable = (UsableItem) item;
            this.selectAction(usable.getAction());
            playerInfo.setUsedItem(item);
            this.view.update();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equipItem(final Item item) {
        final boolean ret = this.model.getSession().getPlayerCharacter().equipItem(item);
        this.view.update();
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unequipItem(final Item item) {
        this.model.getSession().getPlayerCharacter().unequipItem(item);
        this.view.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInformation getPlayerInformation() {
        return this.playerInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatisticsInformation getStatisticsInformation() {
        return this.statisticsInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStatistics(final BasicCharacter character) {
        this.statisticsInfo.setCharacter(character);
        this.view.update();
    }

    // ----------------------------------------------------Action execution and
    // combat management session

    /**
     * {@inheritDoc}
     */
    @Override
    public void undoActionSelection() {
        final ActionActor playerActor = this.model.getSession().getPlayerCharacter();
        playerActor.resetSelectedAction();
        // call view to tell player to select a new action
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void targetSelected(final ActionActor target) {
        final ActionActor playerActor = this.model.getSession().getPlayerCharacter();
        final ActionExecutor currentExecutor = actionExecutor.get();
        playerActor.getSelectedAction().ifPresent(a -> {
            playerInfo.getUsedItem().ifPresent(i -> {
                this.model.getSession().getPlayerCharacter().getInventory().removeItem(i);
                playerInfo.resetUsedItem();
            });
            a.setTargets(target, a.getValidTargets(currentExecutor.getExecutionInstance()));
            currentExecutor.addActorToQueue(playerActor);
        });
        if (currentExecutor.isRoundReady()) {
            evaluateNextAction();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeSingleAction(final Action action) {
        final ActionActor playerActor = this.model.getSession().getPlayerCharacter();
        final ActionExecutionInstance instance = new ExecutionInstanceImpl();
        actionExecutor = Optional.of(new OutOfCombatActionExecutor(action));
        instance.addPlayerPartyMember(playerActor);
        actionExecutor.get().setExecutionInstance(instance);
        if (action.getTargets().isEmpty()) {
            // call view to tell player to select a target
        } else {
            evaluateNextAction();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeCurrentAction() {
        if (!actionExecutor.isPresent()) {
            return;
        }
        final ActionExecutor executor = actionExecutor.get();
        executor.executeCurrentAction();
        // tell view to log
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void evaluateExecutionState() {
        if (!actionExecutor.isPresent()) {
            return;
        }
        final ActionExecutor executor = actionExecutor.get();
        executor.updateExecutionStatus();
        final CombatStatus status = executor.getExecutionStatus();
        switch (status) {
        case COMBAT_ENDED:
            actionExecutor = Optional.empty();
            break;
        case PLAYER_LOST:
            // TODO
            break;
        case PLAYER_WON:
            // TODO
            break;
        case ROUND_IN_PROGRESS:
            evaluateNextAction();
            break;
        case ROUND_PAUSED:
            //tell view to select a new action (by extension, enable all appropriate view components) 
            break;
        default:
            executor.prepareNextRound();
            // view.showNextTurn ?
            // tell view to select a new action
            break;
        }
    }

    private void startCombat(final HostileEncounter encounter) {
        final ActionActor playerActor = this.model.getSession().getPlayerCharacter();
        final ActionExecutionInstance instance = new ExecutionInstanceImpl();
        final ActionExecutor combatExecutor = encounter.getCombatLogic();
        instance.addPlayerPartyMember(playerActor);
        instance.addNPCsPartyMembers(encounter.getNPCs());
        combatExecutor.setExecutionInstance(instance);
        combatExecutor.startExecutor();
        actionExecutor = Optional.of(combatExecutor);
        if (combatExecutor.isRoundReady()) {
            evaluateNextAction();
        } else {
            // call view to tell player to select an action
        }
    }

    private void evaluateNextAction() {
        actionExecutor.ifPresent(a -> {
            a.setNextAction();
            final Optional<ActionResult> result = a.evaluateCurrentAction();
            // view visualize(result)
        });
    }

    private void updateStatuses() {
        final ActionExecutor executor = new StatusUpdateActionExecutor();
        final ActionExecutionInstance instance = new ExecutionInstanceImpl();
        instance.addPlayerPartyMember(model.getSession().getPlayerCharacter());
        executor.setExecutionInstance(instance);
        executor.startExecutor();
        actionExecutor = Optional.of(executor);
        evaluateNextAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectAction(final Action action) {
        model.getSession().getPlayerCharacter().addActionToQueue(action.getCopy(), true);
        // call view to tell player to select a target (even if the action target type
        // is SELF?)
    }

    @Override
    public final boolean nextRoom() {
        final boolean isChanged = this.model.getSession().getEnvironment().getCurrentFloor().nextRoom();
        if (isChanged) {
            final Room room = this.model.getSession().getEnvironment().getCurrentFloor().getCurrentRoom();
            if (!room.getEvents().stream().anyMatch(e -> e instanceof CombatEvent)) {
                this.updateStatuses();
                final CombatEvent combat = room.getEvents().stream().filter(e -> e instanceof CombatEvent)
                                                                    .findAny()
                                                                    .map(e -> (CombatEvent) e)
                                                                    .get();
                this.startCombat(combat.getHostileEncounter());
            }
        }
        return isChanged;
    }

    @Override
    public final boolean nextFloor(final FloorDetails floorDetails) {
        return this.model.getSession().getEnvironment().setNextFloor(floorDetails);
    }

    @Override
    public final List<RoomEvent> getRoomEvents() {
        return this.model.getSession().getEnvironment().getCurrentFloor().getCurrentRoom().getEvents();
    }

    @Override
    public final List<FloorDetails> getStairsOptions() {
        return this.model.getSession().getEnvironment().getFloorOptions();
    }

    @Override
    public final boolean isCurrentLastFloor() {
        return this.model.getSession().getEnvironment().isCurrentLastFloor();
    }

    @Override
    public final boolean isCurrentLastRoom() {
        return !this.model.getSession().getEnvironment().getCurrentFloor().hasNextRoom();
    }

    @Override
    public final boolean isCurrentRoomCompleted() {
        return this.model.getSession().getEnvironment().getCurrentFloor().getCurrentRoom().checkToMoveOn();
    }

    @Override
    public final boolean hasPlayerWon() {
        return this.model.getSession().hasPlayerWon();
    }

    @Override
    public final BasicCharacter getPlayer() {
        return this.model.getSession().getPlayerCharacter();
    }
}
