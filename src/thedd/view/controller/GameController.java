package thedd.view.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import thedd.view.ViewNode;
import thedd.view.scenewrapper.ViewNodeWrapper;
import thedd.view.scenewrapper.ViewNodeWrapperFactory;
import thedd.view.scenewrapper.ViewNodeWrapperFactoryImpl;

/**
 * View controller of game scene.
 */
public class GameController extends SubViewControllerImpl {

    @FXML
    private AnchorPane inventoryContent;

    @FXML
    private AnchorPane statisticsContent;

    @FXML
    private AnchorPane gameContent;

    private static final ViewNode NODE_UP = ViewNode.GAME_OVER;
    private static final ViewNode NODE_DOWN_SX = ViewNode.MENU;
    private static final ViewNode NODE_DOWN_DX = ViewNode.MENU;

    private final List<SubViewController> viewControllers;
    private Optional<ViewNodeWrapperFactory> factory;

    /**
     * GameController constructor.
     */
    public GameController() {
        super();
        this.viewControllers = new ArrayList<>();
        this.factory = Optional.empty();
    }

    @Override
    public final void update() {
        this.viewControllers.forEach(c -> c.update());
    }

    @Override
    protected final void initView() {
        this.factory = Optional.of(new ViewNodeWrapperFactoryImpl(this.getView(), this.getController()));
        ViewNodeWrapper node = factory.get().getNode(NODE_UP);
        this.gameContent.getChildren().add(node.getNode());
        this.viewControllers.add(node.getController());
        node = factory.get().getNode(NODE_DOWN_SX);
        this.inventoryContent.getChildren().add(node.getNode());
        this.viewControllers.add(node.getController());
        node = factory.get().getNode(NODE_DOWN_DX);
        this.statisticsContent.getChildren().add(node.getNode());
        this.viewControllers.add(node.getController());
    }
}
