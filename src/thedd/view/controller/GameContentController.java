package thedd.view.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import thedd.model.character.BasicCharacter;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.Statistic;
import thedd.model.character.types.DarkDestructor;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.result.ActionResult;
import thedd.model.combat.actor.ActionActor;
import thedd.model.roomevent.RoomEventType;
import thedd.model.roomevent.combatevent.CombatEvent;
import thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer;
import thedd.model.world.floor.details.FloorDetails;
import thedd.utils.observer.Observer;
import thedd.view.controller.interfaces.ExplorationView;
import thedd.view.explorationpane.ExplorationPaneImpl;
import thedd.view.explorationpane.TopStackPane;
import thedd.view.explorationpane.confirmationdialog.OptionDialog;
import thedd.view.explorationpane.enums.PartyType;
import thedd.view.explorationpane.enums.TargetSelectionState;
import thedd.view.explorationpane.logger.LoggerImpl;
import thedd.view.explorationpane.logger.LoggerManager;
import thedd.view.imageloader.DirectoryPicker;
import thedd.view.imageloader.ImageLoader;
import thedd.view.imageloader.ImageLoaderImpl;

/**
 * Controller of the top pane of the game.
 */
public class GameContentController extends ViewNodeControllerImpl implements Observer<Pair<Boolean, Pair<PartyType, Integer>>>, ExplorationView {

    @FXML
    private TopStackPane mainPane;

    private final ExplorationPaneImpl explorationPane = new ExplorationPaneImpl();
    private TargetSelectionState state;
    private Optional<Action> performing = Optional.empty();
    private final List<ActionActor> alliedParty = new ArrayList<>();
    private final List<ActionActor> enemyParty = new ArrayList<>();
    private final ImageLoader imgLoader = new ImageLoaderImpl();
    private Image currentBackgroundImage; 
    private Optional<OptionDialog> messageDialog = Optional.empty();
    private boolean firstUpdate = true;

    @Override
    public final void update() {
        if (firstUpdate) {
            firstUpdate = false;
            setNewBackgroundImage();
        }
        final List<Image> alliedImages = new ArrayList<>();
        alliedImages.add(imgLoader.loadSingleImage(DirectoryPicker.ALLY_BATTLE, "renato_corteccioni"));
        explorationPane.setAllyImages(alliedImages);
        updateSingleTarget(this.getController().getPlayer(), new ImmutablePair<PartyType, Integer>(PartyType.ALLIED, 0), Optional.empty());

        final List<Image> enemyImages = new ArrayList<>();
        if (this.getController().isCombatActive()) {
            state = TargetSelectionState.COMBAT_INFORMATION;
            final List<ActionActor> enemyActors = new ArrayList<>();
            final CombatEvent ce = this.getController().getRoomEvents().stream().filter(rm -> rm.getType() == RoomEventType.COMBAT_EVENT).findFirst().map(rm -> (CombatEvent) rm).get(); 
            ce.getHostileEncounter().getNPCs().stream().forEach(npc -> {
                enemyImages.add(mapActionActorToImage(npc)); 
                enemyActors.add(npc);
            });
            explorationPane.setEnemyImages(enemyImages);
            IntStream.range(0,  enemyActors.size()).forEach(i -> updateSingleTarget(enemyActors.get(i), new ImmutablePair<>(PartyType.ENEMY, i), Optional.empty()));
        } else {
            if (this.getController().isCurrentLastRoom()) {
                state = TargetSelectionState.STAIRS;
                this.getController().getStairsOptions().forEach(so -> enemyImages.add(imgLoader.loadSingleImage(DirectoryPicker.ROOM_CHANGER, "stairs")));
                explorationPane.setEnemyImages(enemyImages);
                IntStream.range(0, this.getController().getStairsOptions().size()).forEach(i -> explorationPane.updatePositionTooltip(new ImmutablePair<PartyType, Integer>(PartyType.ENEMY, i), stairsTooltip(i)));
            } else {
                state = TargetSelectionState.EXPLORATION;
                final List<InteractableActionPerformer> iapEvents = new ArrayList<>();
                this.getController().getRoomEvents().stream().filter(rm -> rm.getType() == RoomEventType.INTERACTABLE_ACTION_PERFORMER && !rm.isCompleted()).map(rm -> (InteractableActionPerformer) rm).forEach(iap -> iapEvents.add(iap));
                iapEvents.forEach(iap -> enemyImages.add(iapImage(iap)));
                explorationPane.setEnemyImages(enemyImages);
                IntStream.range(0, iapEvents.size()).forEach(i -> explorationPane.updatePositionTooltip(new ImmutablePair<PartyType, Integer>(PartyType.ENEMY, i), iapTooltip(iapEvents.get(i))));
            }
        }
        explorationPane.setRoomAdvancerVisible(state == TargetSelectionState.EXPLORATION && !this.getController().isCurrentLastRoom());
        explorationPane.changeBackgroundImage(currentBackgroundImage);
        mainPane.autosize();
        explorationPane.forceResize();
    }

    @Override
    protected final void initView() {
        mainPane.setDialogAccepted(() -> continueInput());
        mainPane.setDialogDeclined(() -> cancelInput()); 
        mainPane.getChildren().add(explorationPane);
        explorationPane.getRoomAdvancer().setOnMouseClicked(e -> {
            this.getController().nextRoom();
            changeRoomTransition();
            this.getView().update();
        });
        explorationPane.setActorViewerObserver(this);
        explorationPane.prefWidthProperty().bind(mainPane.widthProperty());
        explorationPane.prefHeightProperty().bind(mainPane.heightProperty());
        explorationPane.autosize();

        mainPane.autosize();
    }

    @Override
    public final void trigger(final Optional<Pair<Boolean, Pair<PartyType, Integer>>> message) {
        if (!Objects.requireNonNull(message).isPresent()) {
            throw new IllegalArgumentException("Message is empty and a non-empty message was expected");
        }
        switch (state) {
            case EXPLORATION:
                if (message.get().getLeft() && message.get().getRight().getLeft() == PartyType.ENEMY) {
                    if (!this.getController().isCurrentLastRoom()) {
                        performing = Optional.of(this.getController().getRoomEvents().stream().filter(re -> re.getType() == RoomEventType.INTERACTABLE_ACTION_PERFORMER && !re.isCompleted())
                                                                                             .map(rm -> (InteractableActionPerformer) rm)
                                                                                             .collect(Collectors.toList()).get(message.get().getRight().getRight()).getAvailableActionsList().get(0));
                        mainPane.showDialog("Do you want to interact with this object?");
                    } else {
                        this.getController().nextFloor(this.getController().getStairsOptions().get(message.get().getRight().getRight()));
                    }
                }
                break;
            case COMBAT_INFORMATION:
                if (!message.get().getLeft()) {
                    displayStatistics(message); 
                } else {
                    this.getController().updateStatistics(this.getController().getPlayer());
                }
                break;
            case COMBAT_TARGET:
                if (!message.get().getLeft()) {
                    displayStatistics(message);
                }
                if (message.get().getLeft()) {
                    final List<ActionActor> selectedParty = getSelectedParty(Objects.requireNonNull(message.get().getRight().getLeft()));
                    this.getController().targetSelected(selectedParty.get(message.get().getRight().getRight()));
                    this.getController().updateStatistics(this.getController().getPlayer());
                    alliedParty.clear();
                    enemyParty.clear();
                }
                break;
            case STAIRS:
                if (message.get().getLeft() && this.getController().nextFloor(this.getController().getStairsOptions().get(message.get().getRight().getRight()))) {
                        changeRoomTransition();
                        this.getController().nextRoom();
                        setNewBackgroundImage();
                        update();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public final void showUserMessage(final String text) {
        if (!messageDialog.isPresent()) {
            messageDialog = Optional.of(new OptionDialog());
            messageDialog.get().setMouseTransparent(true);
            mainPane.getChildren().add(messageDialog.get());
        }
        messageDialog.get().setText(text);

        final int two = 2;
        final int six = 6;
        final int bottomPadding = 10;
        messageDialog.get().getWidthProperty().bind(explorationPane.widthProperty().divide(two));
        messageDialog.get().getHeightProperty().bind(explorationPane.heightProperty().divide(six));
        messageDialog.get().translateXProperty().bind((explorationPane.widthProperty().subtract(messageDialog.get().getWidthProperty())).divide(two));
        messageDialog.get().translateYProperty().bind(new SimpleDoubleProperty(bottomPadding));
        messageDialog.get().autosize();
    }

    @Override
    public final void hideUserMessage() {
        messageDialog.ifPresent(m -> mainPane.getChildren().remove(m));
        messageDialog = Optional.empty();
    }

    private void continueInput() {
        performing.ifPresent(p -> {
            p.setTargets(this.getController().getPlayer(), Collections.emptyList());
            this.getController().executeSingleAction(p);
            ((InteractableActionPerformer) p.getSource().get()).complete();
        });
        mainPane.hideDialog();
        update();
    }

    private void cancelInput() {
        performing = Optional.empty();
        mainPane.hideDialog();
    }

    @Override
    public final void showTargets(final List<ActionActor> targetables, 
                            final List<ActionActor> alliedParty, 
                            final List<ActionActor> enemyParty,
                            final Action action) {
        final List<Pair<PartyType, Integer>> allActorPositions = new ArrayList<>();
        final List<Pair<PartyType, Integer>> targetableActors = new ArrayList<>();
        IntStream.range(0, alliedParty.size())
                 .peek(i -> allActorPositions.add(new ImmutablePair<>(PartyType.ALLIED, i)))
                 .filter(i -> targetables.contains(alliedParty.get(i)))
                 .forEach(i -> {
                     final Pair<PartyType, Integer> pos = new ImmutablePair<>(PartyType.ALLIED, i);
                     targetableActors.add(pos);
                     updateSingleTarget(alliedParty.get(i), pos, Optional.of(action));
                 });
        IntStream.range(0, enemyParty.size())
                 .peek(i -> allActorPositions.add(new ImmutablePair<>(PartyType.ENEMY, i)))
                 .filter(i -> targetables.contains(enemyParty.get(i)))
                 .forEach(i -> {
                     final Pair<PartyType, Integer> pos = new ImmutablePair<>(PartyType.ENEMY, i);
                     targetableActors.add(pos);
                     updateSingleTarget(enemyParty.get(i), pos, Optional.of(action));
                 });
        explorationPane.setTargetablePositions(targetableActors, allActorPositions);
        this.alliedParty.addAll(alliedParty);
        this.enemyParty.addAll(enemyParty);
        state = TargetSelectionState.COMBAT_TARGET;
    }

    @Override
    public final void hideTargets() {
        explorationPane.setAllAsTargetable();
        state = TargetSelectionState.COMBAT_INFORMATION;
        IntStream.range(0, alliedParty.size())
                 .forEach(i -> updateSingleTarget(alliedParty.get(i), new ImmutablePair<>(PartyType.ALLIED, i),  Optional.empty()));
        IntStream.range(0, enemyParty.size())
                 .forEach(i -> updateSingleTarget(enemyParty.get(i), new ImmutablePair<>(PartyType.ENEMY, i),  Optional.empty()));
        alliedParty.clear();
        enemyParty.clear();
    }

    @Override
    public final void logAction(final ActionResult result) {
            final LinkedList<String> queue = new LinkedList<>();
            result.getResults().forEach(r -> {
                switch (r.getRight()) {
                case HIT:
                    queue.add(result.getAction().getLogMessage(result.getAction().getTarget().get(), true));
                    result.getAction().getEffects().forEach(e -> {
                        e.setTarget(r.getKey());
                        queue.add(e.getLogMessage());
                    });
                    break;
                case MISSED:
                    queue.add(result.getAction().getLogMessage(result.getAction().getTarget().get(), false));
                    break;
                case PARRIED:
                    queue.add(r.getLeft().getName() + " parried " + result.getAction().getSource().get().getName() + "'s action");
                    break;
                default:
                    break;
                }
                if (r.getKey() instanceof BasicCharacter && !((BasicCharacter) r.getKey()).isAlive()) {
                    queue.add(r.getKey().getName() + " has been defeated.");
                    if (r.getKey() instanceof DarkDestructor) {
                        queue.add("You have completed your mission, brave knight!");
                    }
                }
            });
            final LoggerImpl log = generateLog();
            final LoggerManager lm = new LoggerManager(log, queue);
            log.setLoggerManager(lm);
            final EventHandler<MouseEvent> filter = new EventHandler<MouseEvent>() {
                @Override
                public void handle(final MouseEvent event) {
                        event.consume();
                }
            };
            mainPane.addEventFilter(MouseEvent.ANY, filter);
            final EventHandler<WorkerStateEvent> loggerCloser = new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(final WorkerStateEvent event) {
                    mainPane.getChildren().remove(log);
                    mainPane.removeEventFilter(MouseEvent.ANY, filter);
                    getController().evaluateExecutionState();
                }
            };
            lm.setOnCancelled(loggerCloser);
            lm.setOnSucceeded(loggerCloser);
            final Thread t = new Thread(lm);
            t.setDaemon(true);
            t.start();

    }

    @Override
    public final void visualizeAction(final ActionResult result) {
        this.getController().executeCurrentAction();
    }

    private void updateSingleTarget(final ActionActor target, final Pair<PartyType, Integer> position, final Optional<Action> action) {
        if (Objects.requireNonNull(target) instanceof BasicCharacter) {
            final BasicCharacter bcTarget = (BasicCharacter) target;
            final StatValues targetHP = bcTarget.getStat(Statistic.HEALTH_POINT);
            final double hitChance = action.isPresent() ? Objects.requireNonNull(action).get().getHitChance(target) : 0;
            final String hitC = String.format("%.2f%%", hitChance * 100);
            final Optional<Action> targetAction = target.getSelectedAction();
            final String tooltip = bcTarget.getName() + '\n'
                                   + "HP: " + targetHP.getActual() + "/" + targetHP.getMax() + '\n'
                                   + (action.isPresent() ? "Chanche to hit: " + hitC  + '\n' : "")
                                   + (targetAction.isPresent() ? "Next action: " + targetAction.get().getName() + '\n' : "");
            explorationPane.updatePositionTooltip(Objects.requireNonNull(position), tooltip);
            if (bcTarget.getStat(Statistic.HEALTH_POINT).getActual() <= 0) {
                    explorationPane.disableViewer(position);
            }
        }
    }

    private List<ActionActor> getSelectedParty(final PartyType side) {
        switch (Objects.requireNonNull(side)) {
            case ALLIED:
                return alliedParty;
            case ENEMY:
                return enemyParty;
            default:
                return null;
        }
    }

    private Image mapActionActorToImage(final ActionActor c) {
        return ((BasicCharacter) c).getStat(Statistic.HEALTH_POINT).getActual() > 0 
                ? imgLoader.loadSingleImage(DirectoryPicker.ENEMY_BATTLE, c.getName()) 
                : imgLoader.loadSingleImage(DirectoryPicker.CHARACTER_COMMON, "dead_character");
    }

    private void setNewBackgroundImage() {
        currentBackgroundImage = backgroundImage(this.getController().isCurrentLastFloor() && this.getController().isCurrentLastRoom());
    }

    private void changeRoomTransition() {
        final Rectangle node = new Rectangle(explorationPane.getWidth(), explorationPane.getHeight());
        node.widthProperty().bind(explorationPane.widthProperty());
        node.heightProperty().bind(explorationPane.heightProperty());
        mainPane.getChildren().add(node);

        final int transitionTime = 1;
        final TranslateTransition tt = new TranslateTransition(Duration.seconds(transitionTime), node);
        tt.fromXProperty().bind(explorationPane.widthProperty());
        tt.toXProperty().bind(explorationPane.widthProperty().multiply(-1));
        tt.setOnFinished(e -> {
            mainPane.getChildren().remove(node);
        });
        tt.playFromStart();
        setNewBackgroundImage();
    }

    private String iapTooltip(final InteractableActionPerformer roomEvent) {
        if (roomEvent.getName().equals("Trap") || roomEvent.getName().equals("Treasure Chest")) {
            return "A treasure chest";
        } else {
            return roomEvent.getAvailableActionsList().get(0).getDescription();
        }
    }

    private Image iapImage(final InteractableActionPerformer roomEvent) {
        if (roomEvent.getName().equals("Trap") || roomEvent.getName().equals("Treasure Chest")) {
            return imgLoader.loadSingleImage(DirectoryPicker.INTERACTABLE_ACTION_PERFORMER, "treasure_chest");
        } else {
            return imgLoader.loadSingleImage(DirectoryPicker.INTERACTABLE_ACTION_PERFORMER, roomEvent.getName());
        }
    }

    private String stairsTooltip(final int position) {
        final FloorDetails fd = this.getController().getStairsOptions().get(position);
        return "Next floor:\n" 
               + "Difficulty: " + fd.getDifficult() + "\n"
               + "Number of enemies: " + fd.getNumberOfEnemies() + "\n" 
               + "Number of treasures: " + fd.getNumberOfTreasures() + "\n"; 
    }

    private Image backgroundImage(final boolean isBossRoom) {
        if (isBossRoom) {
            return imgLoader.loadSingleImage(DirectoryPicker.BACKGROUND, "boss_background");
        } else {
            final int numOfBG = 2;
            final Random r = new Random();
            return imgLoader.loadSingleImage(DirectoryPicker.BACKGROUND, "background" + r.nextInt(numOfBG));
        }
    }

    private LoggerImpl generateLog() {
        final LoggerImpl log = new LoggerImpl();
        final int two = 2;
        final int six = 6;
        final int bottomPadding = 10;

        log.setVisible(false);
        log.setText("");
        log.getWidthProperty().bind(explorationPane.widthProperty().divide(two));
        log.getHeightProperty().bind(explorationPane.heightProperty().divide(six));
        log.translateYProperty().bind(explorationPane.heightProperty().subtract(log.getHeightProperty().add(bottomPadding)).divide(two));
        mainPane.getChildren().add(log);
        return log;
    }

    private void displayStatistics(final Optional<Pair<Boolean, Pair<PartyType, Integer>>> message) {
        if (message.get().getRight().getLeft() == PartyType.ALLIED && message.get().getRight().getRight() == 0) {
            this.getController().updateStatistics(this.getController().getPlayer());
        } else if (message.get().getRight().getLeft() == PartyType.ENEMY) {
            final CombatEvent ce = (CombatEvent) this.getController().getRoomEvents().stream().filter(re -> re.getType() == RoomEventType.COMBAT_EVENT).findFirst().get();
            this.getController().updateStatistics((BasicCharacter) ce.getHostileEncounter().getNPCs().stream().collect(Collectors.toList()).get(message.get().getRight().getRight()));
        }
    }
}
