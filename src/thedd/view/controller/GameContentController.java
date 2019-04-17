package thedd.view.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.result.ActionResult;
import thedd.model.combat.actor.ActionActor;
import thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer;
import thedd.utils.observer.Observer;
import thedd.view.explorationpane.ExplorationPaneImpl;
import thedd.view.explorationpane.TopStackPane;
import thedd.view.explorationpane.enums.PartyType;
import thedd.view.explorationpane.enums.TargetSelectionState;

public class GameContentController extends ViewNodeControllerImpl implements Observer<Pair<PartyType, Integer>>, ControllerShowMethods {

    @FXML
    private TopStackPane mainPane;

    private final ExplorationPaneImpl explorationPane = new ExplorationPaneImpl();
    private TargetSelectionState state;
    private Optional<InteractableActionPerformer> performer = Optional.empty();

    @Override
    public final void update() {
        explorationPane.setRoomAdvancerVisible(state == TargetSelectionState.EXPLORATION);
    }

    @Override
    protected final void initView() {
        state = TargetSelectionState.EXPLORATION;
        mainPane.setDialogAccepted(() -> continueInput());
        mainPane.setDialogDeclined(() -> cancelInput()); 
        mainPane.getChildren().add(explorationPane);
        explorationPane.changeBackgroundImage(new Image(ClassLoader.getSystemResourceAsStream("bgimg.jpg")));
        explorationPane.getRoomAdvancer().setOnMouseClicked(e -> mainPane.showDialog("Cambi stanza?"));
    }

    @Override
    public final void trigger(final Optional<Pair<PartyType, Integer>> message) {
        switch (state) {
            case EXPLORATION:
                //prendo la contraption in posizione message.get().getRight()
                //la salvo in una variabile optional
                mainPane.showDialog("Do you want to interact with this object?"); //faccio aprire il dialog
                break;
            case COMBAT_INFORMATION:
                //prendo il personaggio in posizione x
                //dico alla view delle statistiche di aggiornarsi con le statistiche per pg selezionato
                break;
            case COMBAT_TARGET:
                //prendo il personaggio in posizione x
                //dico al ActionExecutor che è stato selezionato come bersaglio
                break;
            default:
                break;
        }
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hideTargets() {
        //mostra tutti come targettablili
    }

    @Override
    public void logAction(final ActionResult result) {
        // TODO Auto-generated method stub
        
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


}
