package thedd.view.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import thedd.model.character.BasicCharacter;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.Statistic;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.result.ActionResult;
import thedd.model.combat.actor.ActionActor;
import thedd.model.roomevent.RoomEventType;
import thedd.model.roomevent.combatevent.CombatEvent;
import thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer;
import thedd.utils.observer.Observer;
import thedd.view.controller.interfaces.ExplorationView;
import thedd.view.controller.interfaces.GameView;
import thedd.view.explorationpane.ExplorationPaneImpl;
import thedd.view.explorationpane.TopStackPane;
import thedd.view.explorationpane.enums.PartyType;
import thedd.view.explorationpane.enums.TargetSelectionState;
import thedd.view.explorationpane.logger.LoggerImpl;
import thedd.view.explorationpane.logger.LoggerManager;

public class GameContentController extends ViewNodeControllerImpl implements Observer<Pair<PartyType, Integer>>, ExplorationView, GameView {

    @FXML
    private TopStackPane mainPane;

    private final ExplorationPaneImpl explorationPane = new ExplorationPaneImpl();
    private final LoggerImpl log = new LoggerImpl();
    private TargetSelectionState state;
    private Optional<InteractableActionPerformer> performer = Optional.empty();
    private final List<ActionActor> alliedParty = new ArrayList<>();
    private final List<ActionActor> enemyParty = new ArrayList<>();

    @Override
    public final void update() {
        state = (this.getController().isCombatActive() ? TargetSelectionState.COMBAT_INFORMATION : TargetSelectionState.EXPLORATION); 
        explorationPane.setRoomAdvancerVisible(state == TargetSelectionState.EXPLORATION);
        
    }

    @Override
    protected final void initView() {
        state = TargetSelectionState.EXPLORATION;
        mainPane.setDialogAccepted(() -> continueInput());
        mainPane.setDialogDeclined(() -> cancelInput()); 
        mainPane.getChildren().add(explorationPane);
        explorationPane.changeBackgroundImage(new Image(ClassLoader.getSystemResourceAsStream("bgimg.jpg")));
        explorationPane.getRoomAdvancer().setOnMouseClicked(e -> provaLog()/*mainPane.showDialog("Cambi stanza?")*/);
        explorationPane.autosize();

        final int two = 2;
        final int six = 6;
        final int bottomPadding = 10;

        log.setVisible(false);
        log.getWidthProperty().bind(explorationPane.widthProperty().divide(two));
        log.getHeightProperty().bind(explorationPane.heightProperty().divide(six));
        log.translateYProperty().bind(explorationPane.heightProperty().subtract(log.getHeightProperty().add(bottomPadding)).divide(two));
        mainPane.getChildren().add(log);
    }

    @Override
    public final void trigger(final Optional<Pair<PartyType, Integer>> message) {
        if (!Objects.requireNonNull(message).isPresent()) {
            throw new IllegalArgumentException("Message is empty and a non-empty message was expected");
        }
        switch (state) {
            case EXPLORATION:
                //prendo la contraption in posizione message.get().getRight()
                //la salvo in una variabile optional
                mainPane.showDialog("Do you want to interact with this object?"); //faccio aprire il dialog
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
            default:
                break;
        }
    }

    private void provaLog() {
        final LinkedList<String> queue = new LinkedList<>();
        queue.add("Prova1");
        queue.add("Prova2");
        final LoggerManager lm = new LoggerManager(log, queue);
        log.setLoggerManager(lm);
        final Thread t = new Thread(lm);
        t.setDaemon(true);
        t.start();
    }

    private void continueInput() {
        performer.ifPresent(p -> p.getSelectedAction().get().applyEffects(null /*MainCharacter*/));
    }

    private void cancelInput() {
        performer = Optional.empty();
    }

    @Override
    public void showTargets(final List<ActionActor> targetables, 
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
    public void hideTargets() {
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
    public void logAction(final ActionResult result) {
        
    }

    @Override
    public void visualizeAction(final ActionResult result) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void showInventory() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void showActionSelector() {
        // TODO Auto-generated method stub
        
    }

    private void updateSingleTarget(final ActionActor target, final Pair<PartyType, Integer> position, final Optional<Action> action) {
        if (Objects.requireNonNull(target) instanceof BasicCharacter) {
            final BasicCharacter bcTarget = (BasicCharacter) target;
            final StatValues targetHP = bcTarget.getStat(Statistic.HEALTH_POINT);
            final String tooltip = bcTarget.getName() + '\n'
                                   + "HP: " + targetHP.getActual() + "/" + targetHP.getMax() + '\n'
                                   + (action.isPresent() ? "Chanche to hit: " + Objects.requireNonNull(action).get().getHitChance(target) + '\n' : "");
            explorationPane.updatePositionTooltip(Objects.requireNonNull(position), tooltip);
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

}
