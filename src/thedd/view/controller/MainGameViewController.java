package thedd.view.controller;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.result.ActionResult;
import thedd.model.combat.actor.ActionActor;
import thedd.view.ViewNode;
import thedd.view.controller.interfaces.ExplorationView;
import thedd.view.controller.interfaces.GameView;
import thedd.view.nodewrapper.ViewNodeWrapper;
import thedd.view.nodewrapper.ViewNodeWrapperFactory;

/**
 * View controller of game scene.
 */
public class MainGameViewController extends ViewNodeControllerImpl implements GameView {

    private enum Position {
        UP, DOWN_SX, DOWN_DX;
    };

    @FXML
    private AnchorPane inventoryContent;

    @FXML
    private AnchorPane statisticsContent;

    @FXML
    private AnchorPane gameContent;

    private static final ViewNode INIT_NODE_UP = ViewNode.STATISTICS;
    private static final ViewNode INIT_NODE_DOWN_SX = ViewNode.INVENTORY;
    private static final ViewNode INIT_NODE_DOWN_DX = ViewNode.STATISTICS;

    private final EnumMap<Position, Optional<ViewNodeController>> nodeControllers;

    /**
     * GameController constructor.
     */
    public MainGameViewController() {
        super();
        this.nodeControllers = new EnumMap<Position, Optional<ViewNodeController>>(Position.class);
        this.nodeControllers.put(Position.UP, Optional.empty());
        this.nodeControllers.put(Position.DOWN_SX, Optional.empty());
        this.nodeControllers.put(Position.DOWN_DX, Optional.empty());
//        this.nodeControllers.values().forEach(c -> c.get().setDialogFactory(this.getDialogFactory()));
    }

    @Override
    public final void update() {
        this.nodeControllers.values().stream().filter(c -> c.isPresent())
                                              .forEach(c -> c.get().update());
    }

    @Override
    protected final void initView() {
        this.showNode(gameContent, INIT_NODE_UP, Position.UP);
        this.showNode(inventoryContent, INIT_NODE_DOWN_SX, Position.DOWN_SX);
        this.showNode(statisticsContent, INIT_NODE_DOWN_DX, Position.DOWN_DX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showTargets(final List<ActionActor> targetables, final List<ActionActor> alliedParty,
            final List<ActionActor> enemyParty, final Action action) {
        if (this.getExplorationPaneController().isPresent()) {
            this.getExplorationPaneController().get().showTargets(targetables, alliedParty, enemyParty, action);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void hideTargets() {
        if (this.getExplorationPaneController().isPresent()) {
            this.getExplorationPaneController().get().hideTargets();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void logAction(final ActionResult result) {
        if (this.getExplorationPaneController().isPresent()) {
            this.getExplorationPaneController().get().logAction(result);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visualizeAction(final ActionResult result) {
        if (this.getExplorationPaneController().isPresent()) {
            this.getExplorationPaneController().get().visualizeAction(result);
        }
    }

    @Override
    public final void showInventory() {
        this.showNode(inventoryContent, ViewNode.INVENTORY, Position.DOWN_SX);
    }

    @Override
    public final void showActionSelector() {
        this.showNode(inventoryContent, ViewNode.ACTION_SELECTOR, Position.DOWN_SX);
    }

    private void showNode(final AnchorPane pane, final ViewNode typeOfNode, final Position pos) {
        final ViewNodeWrapper node = ViewNodeWrapperFactory.createViewNodeWrapper(typeOfNode, this.getController(),
                                                                                  this.getView());
        pane.getChildren().add(node.getNode());
        this.nodeControllers.put(pos, Optional.of(node.getController()));
    }

    private Optional<ExplorationView>  getExplorationPaneController() {
        return this.nodeControllers.values().stream().filter(c -> c.isPresent())
                                                     .map(c -> c.get())
                                                     .filter(c -> c instanceof ExplorationView)
                                                     .map(c -> (ExplorationView) c)
                                                     .findAny();
    }
}
