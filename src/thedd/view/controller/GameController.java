package thedd.view.controller;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import thedd.view.ViewNode;
import thedd.view.nodewrapper.ViewNodeWrapper;
import thedd.view.nodewrapper.ViewNodeWrapperFactory;

/**
 * View controller of game scene.
 */
public class GameController extends ViewNodeControllerImpl {

    @FXML
    private AnchorPane mainWindow;

    @FXML
    private AnchorPane inventoryContent;

    @FXML
    private AnchorPane statisticsContent;

    @FXML
    private AnchorPane gameContent;

    private static final ViewNode INIT_NODE_UP = ViewNode.TOP_PANE;
    private static final ViewNode INIT_NODE_DOWN_SX = ViewNode.INVENTORY;
    private static final ViewNode INIT_NODE_DOWN_DX = ViewNode.STATISTICS;

    private final List<ViewNodeController> viewControllers;

    /**
     * GameController constructor.
     */
    public GameController() {
        super();
        this.viewControllers = new ArrayList<>();
    }

    @Override
    public final void update() {
        this.viewControllers.forEach(c -> c.update());
    }

    @Override
    protected final void initView() {
        ViewNodeWrapper node = ViewNodeWrapperFactory.createViewNodeWrapper(INIT_NODE_UP, this.getController(), 
                                                                            this.getView());
        this.gameContent.getChildren().add(node.getNode());
        this.viewControllers.add(node.getController());
        node = ViewNodeWrapperFactory.createViewNodeWrapper(INIT_NODE_DOWN_SX, this.getController(), this.getView());
        this.inventoryContent.getChildren().add(node.getNode());
        this.viewControllers.add(node.getController());
        node = ViewNodeWrapperFactory.createViewNodeWrapper(INIT_NODE_DOWN_DX, this.getController(), this.getView());
        this.statisticsContent.getChildren().add(node.getNode());
        this.viewControllers.add(node.getController());
    }
}
