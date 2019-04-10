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
 * Implementation of {@link ViewNodeWrapperFactory}.
 */
public class ViewNodeWrapperFactoryImpl implements ViewNodeWrapperFactory {

    private static final String ERROR_FXMLNOTFOUND = "FXML not found";

    private final View view;
    private final Controller controller;

    /**
     * Create new instance of ViewNodeWrapperFactory.
     * 
     * @param view       view reference
     * @param controller controller reference
     */
    public ViewNodeWrapperFactoryImpl(final View view, final Controller controller) {
        Objects.requireNonNull(view);
        Objects.requireNonNull(controller);
        this.view = view;
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ViewNodeWrapper getNode(final ViewNode viewNode) {
        return this.loadNode(viewNode);
    }

    private ViewNodeWrapper loadNode(final ViewNode nodeIdentifier) {
        Objects.requireNonNull(nodeIdentifier);
        try {
            final FXMLLoader loader = new FXMLLoader();
            final URL location = ClassLoader.getSystemClassLoader().getResource(nodeIdentifier.getFXMLPath());
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
