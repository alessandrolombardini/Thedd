package thedd.view.nodewrapper;

import java.awt.geom.IllegalPathStateException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import thedd.controller.Controller;
import thedd.view.View;
import thedd.view.ViewNode;
import thedd.view.controller.ViewNodeController;
import thedd.view.controller.ViewNodeControllerImpl;

/**
 * Implementations of {@link ViewNodeWrapper}.
 */
public final class ViewNodeWrapper {

    private static final String ERROR_FXMLNOTFOUND = "FXML can't be load, some problem in the .fxml or view controller";

    private final ViewNodeController subViewController;
    private final Node node;

    /**
     * Create a new instance of ViewNodeWrapper.
     * @param node
     *          node
     * @param viewController
     *          controller
     */
    private ViewNodeWrapper(final Node node, final ViewNodeController viewController) {
        Objects.requireNonNull(node);
        Objects.requireNonNull(viewController);
        this.node = node;
        this.subViewController = viewController;
    }


    /**
     * Getter of ViewNodeController.
     * 
     * @return ViewNodeController of Node of ViewNode alredy loaded.
     */
    public ViewNodeController getController() {
        return this.subViewController;
    }


    /**
     * Getter of Node.
     * 
     * @return Node of ViewNode 
     */
    public Node getNode() {
        return this.node;
    }

    /**
     * Method to get a node with its controller.
     * 
     * @param viewNode   view node of application
     * @param controller controller reference
     * @param view       view reference
     * @return the node and its view controller
     */
    public static ViewNodeWrapper createViewNodeWrapper(final ViewNode viewNode,
                                                        final Controller controller, final View view) {
        Objects.requireNonNull(viewNode);
        try {
            final FXMLLoader loader = new FXMLLoader();
            final URL location = ClassLoader.getSystemClassLoader().getResource(viewNode.getFXMLPath());
            loader.setLocation(location);
            final Node node = (Node) loader.load();
            final ViewNodeControllerImpl subViewController = loader.getController();
            subViewController.init(view, controller);
            return new ViewNodeWrapper(node, subViewController);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalPathStateException(ERROR_FXMLNOTFOUND + e.toString());
        }
    }

}
