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

    private static final ViewNode INIT_NODE_UP = ViewNode.TOP_PANE;
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
        getController().nextRoom();
        update();
        this.gameContent.toFront();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showTargets(final List<ActionActor> targetables, final List<ActionActor> alliedParty,
                                  final List<ActionActor> enemyParty, final Action action) {
        this.getExplorationPaneController().ifPresent(c -> c.showTargets(targetables, alliedParty, enemyParty, action));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void hideTargets() {
        this.getExplorationPaneController().ifPresent(c -> c.hideTargets());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void logAction(final ActionResult result) {
        this.getExplorationPaneController().ifPresent(c -> c.logAction(result));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visualizeAction(final ActionResult result) {
        this.getExplorationPaneController().ifPresent(c -> c.visualizeAction(result));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showInventory() {
        this.showNode(inventoryContent, ViewNode.INVENTORY, Position.DOWN_SX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showActionSelector() {
        this.showNode(inventoryContent, ViewNode.ACTION_SELECTOR, Position.DOWN_SX);
    }

    private void showNode(final AnchorPane pane, final ViewNode typeOfNode, final Position pos) {
        final ViewNodeWrapper node = ViewNodeWrapper.createViewNodeWrapper(typeOfNode, this.getController(),
                                                                           this.getView());
        node.getController().init(this.getView(), this.getController());
        final int nodeToChange = 0;
        if (pane.getChildren().isEmpty()) {
            pane.getChildren().add(nodeToChange, node.getNode());
        } else {
            pane.getChildren().set(nodeToChange, node.getNode());
        }
        this.nodeControllers.put(pos, Optional.of(node.getController()));
    }

    private Optional<ExplorationView>  getExplorationPaneController() {
        return this.nodeControllers.values().stream().filter(c -> c.isPresent())
                                                     .map(c -> c.get())
                                                     .filter(c -> c instanceof ExplorationView)
                                                     .map(c -> (ExplorationView) c)
                                                     .findAny();
    }

    @Override
    public final void showUserMessage(final String text) {
        getExplorationPaneController().ifPresent(c -> c.showUserMessage(text));
    }

    @Override
    public final void hideUserMessage() {
        getExplorationPaneController().ifPresent(c -> c.hideUserMessage());
    }

    /**
     * Updates the statistic node.
     */
    public void partialUpdate() {
        nodeControllers.get(Position.DOWN_DX).ifPresent(n -> n.update());
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public void disableInteraction() {
        nodeControllers.values().forEach(c -> c.ifPresent(a -> a.disableInteraction()));
    }

}

