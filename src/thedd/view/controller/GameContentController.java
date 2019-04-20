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
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import thedd.model.character.BasicCharacter;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.Statistic;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.result.ActionResult;
import thedd.model.combat.actor.ActionActor;
import thedd.model.roomevent.RoomEventType;
import thedd.model.roomevent.combatevent.CombatEvent;
import thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer;
import thedd.model.world.floor.FloorDetails.FloorDetails;
import thedd.utils.observer.Observer;
import thedd.view.controller.interfaces.ExplorationView;
import thedd.view.explorationpane.ExplorationPaneImpl;
import thedd.view.explorationpane.TopStackPane;
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
public class GameContentController extends ViewNodeControllerImpl implements Observer<Pair<PartyType, Integer>>, ExplorationView {

    @FXML
    private TopStackPane mainPane;

    private final ExplorationPaneImpl explorationPane = new ExplorationPaneImpl();
    private final LoggerImpl log = new LoggerImpl();
    private TargetSelectionState state;
    private Optional<Action> performing = Optional.empty();
    private final List<ActionActor> alliedParty = new ArrayList<>();
    private final List<ActionActor> enemyParty = new ArrayList<>();
    private final ImageLoader imgLoader = new ImageLoaderImpl();
    private Image currentBackgroundImage; 

    @Override
    public final void update() {
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
            this.getView().showActionSelector();
        } else {
            this.getView().showInventory();
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
        state = TargetSelectionState.EXPLORATION;
        mainPane.setDialogAccepted(() -> continueInput());
        mainPane.setDialogDeclined(() -> cancelInput()); 
        mainPane.getChildren().add(explorationPane);
        explorationPane.getRoomAdvancer().setOnMouseClicked(e -> {
            changeRoomTransition();
            this.getController().nextRoom();
            this.getView().update();
        });
        explorationPane.setActorViewerObserver(this);
        explorationPane.prefWidthProperty().bind(mainPane.widthProperty());
        explorationPane.prefHeightProperty().bind(mainPane.heightProperty());
        explorationPane.autosize();

        final int two = 2;
        final int six = 6;
        final int bottomPadding = 10;

        log.setVisible(false);
        log.setText("");
        log.getWidthProperty().bind(explorationPane.widthProperty().divide(two));
        log.getHeightProperty().bind(explorationPane.heightProperty().divide(six));
        log.translateYProperty().bind(explorationPane.heightProperty().subtract(log.getHeightProperty().add(bottomPadding)).divide(two));
        mainPane.getChildren().add(log);
        mainPane.autosize();
        this.getController().nextRoom();
        setNewBackgroundImage();
        update();
    }

    @Override
    public final void trigger(final Optional<Pair<PartyType, Integer>> message) {
        if (!Objects.requireNonNull(message).isPresent()) {
            throw new IllegalArgumentException("Message is empty and a non-empty message was expected");
        }
        switch (state) {
            case EXPLORATION:
                if (message.get().getLeft() == PartyType.ENEMY) {
                    if (!this.getController().isCurrentLastRoom()) {
                        performing = Optional.of(this.getController().getRoomEvents().stream().filter(re -> re.getType() == RoomEventType.INTERACTABLE_ACTION_PERFORMER && !re.isCompleted())
                                                                                             .map(rm -> (InteractableActionPerformer) rm)
                                                                                             .collect(Collectors.toList()).get(message.get().getRight()).getAvailableActionsList().get(0));
                        mainPane.showDialog("Do you want to interact with this object?");
                    } else {
                        this.getController().nextFloor(this.getController().getStairsOptions().get(message.get().getRight()));
                    }
                }
                break;
            case COMBAT_INFORMATION:
                if (message.get().getLeft() == PartyType.ALLIED && message.get().getRight() == 0) {
                    this.getController().updateStatistics(this.getController().getPlayer());
                } else if (message.get().getLeft() == PartyType.ENEMY) {
                    final CombatEvent ce = (CombatEvent) this.getController().getRoomEvents().stream().filter(re -> re.getType() == RoomEventType.COMBAT_EVENT).findFirst().get();
                    this.getController().updateStatistics((BasicCharacter) ce.getHostileEncounter().getNPCs().stream().collect(Collectors.toList()).get(message.get().getRight()));
                }
                break;
            case COMBAT_TARGET:
                final List<ActionActor> selectedParty = getSelectedParty(Objects.requireNonNull(message.get().getLeft()));
                this.getController().targetSelected(selectedParty.get(message.get().getRight()));
                break;
            case STAIRS:
                changeRoomTransition();
                this.getController().nextFloor(this.getController().getStairsOptions().get(message.get().getRight()));
                this.getController().nextRoom();
                setNewBackgroundImage();
                update();
                break;
            default:
                break;
        }
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
            });
            final LoggerManager lm = new LoggerManager(log, queue);
            log.setLoggerManager(lm);
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
            final String tooltip = bcTarget.getName() + '\n'
                                   + "HP: " + targetHP.getActual() + "/" + targetHP.getMax() + '\n'
                                   + (action.isPresent() ? "Chanche to hit: " + Objects.requireNonNull(action).get().getHitChance(target) + '\n' : "");
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

        final TranslateTransition tt = new TranslateTransition(Duration.seconds(2), node);
        tt.setFromX(explorationPane.getWidth());
        tt.setToX(-node.getWidth());
        tt.playFromStart();
        tt.setOnFinished(e -> mainPane.getChildren().remove(node));
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
}
