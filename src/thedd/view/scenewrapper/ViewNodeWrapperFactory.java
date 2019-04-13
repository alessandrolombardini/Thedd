package thedd.view.scenewrapper;

import java.awt.geom.IllegalPathStateException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import thedd.controller.Controller;
import thedd.view.View;
import thedd.view.ViewNode;
import thedd.view.controller.ViewNodeControllerImpl;

/**
 * Interface describing a {@link ViewNodeWrapper} factory.
 */
public final class ViewNodeWrapperFactory {

    private static final String ERROR_FXMLNOTFOUND = "FXML can't be load, some problem in the .fxml or view controller";

    private ViewNodeWrapperFactory() { }

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
            return new ViewNodeWrapperImpl(node, subViewController);
        } catch (IOException e) {
            throw new IllegalPathStateException(ERROR_FXMLNOTFOUND);
        }
    }
}
