package thedd.view.controller;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import thedd.view.ViewNode;
import thedd.view.scenewrapper.ViewNodeWrapper;
import thedd.view.scenewrapper.ViewNodeWrapperFactory;

/**
 * View controller of game scene.
 */
public class GameController extends ViewNodeControllerImpl {

    @FXML
    private AnchorPane inventoryContent;

    @FXML
    private AnchorPane statisticsContent;

    @FXML
    private AnchorPane gameContent;

    private static final ViewNode NODE_UP = ViewNode.INVENTORY;
    private static final ViewNode NODE_DOWN_SX = ViewNode.INVENTORY;
    private static final ViewNode NODE_DOWN_DX = ViewNode.STATISTICS;

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
        ViewNodeWrapper node = ViewNodeWrapperFactory.createViewNodeWrapper(NODE_UP, this.getController(), 
                                                                            this.getView());
        this.gameContent.getChildren().add(node.getNode());
        this.viewControllers.add(node.getController());
        node = ViewNodeWrapperFactory.createViewNodeWrapper(NODE_DOWN_SX, this.getController(), this.getView());
        this.inventoryContent.getChildren().add(node.getNode());
        this.viewControllers.add(node.getController());
        node = ViewNodeWrapperFactory.createViewNodeWrapper(NODE_DOWN_DX, this.getController(), this.getView());
        this.statisticsContent.getChildren().add(node.getNode());
        this.viewControllers.add(node.getController());
    }
}
