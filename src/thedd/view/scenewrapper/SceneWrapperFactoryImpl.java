package thedd.view.scenewrapper;

import java.awt.geom.IllegalPathStateException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import thedd.controller.Controller;
import thedd.view.ApplicationViewState;
import thedd.view.View;
import thedd.view.ViewNode;
import thedd.view.controller.SubViewControllerImpl;

/**
 * Implementation of {@link SceneWrapperFactory}.
 */
public class SceneWrapperFactoryImpl implements SceneWrapperFactory {

    private static final String ERROR_FXMLNOTFOUND = "FXML not found";

    private final View view;
    private final Controller controller;

    /**
     * Create new instance of SceneWrapperFactory.
     * 
     * @param view       view reference
     * @param controller controller reference
     */
    public SceneWrapperFactoryImpl(final View view, final Controller controller) {
        Objects.requireNonNull(view);
        Objects.requireNonNull(controller);
        this.view = view;
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SceneWrapper getScene(final ApplicationViewState state) {
        return this.loadNode(state.getViewNode());
    }

    private SceneWrapper loadNode(final ViewNode nodeIdentifier) {
        Objects.requireNonNull(nodeIdentifier);
        try {
            final FXMLLoader loader = new FXMLLoader();
            final URL location = ClassLoader.getSystemClassLoader().getResource(nodeIdentifier.getFXMLPath());
            loader.setLocation(location);
            final Node node = (Node) loader.load();
            final SubViewControllerImpl subViewController = loader.getController();
            subViewController.init(view, controller);
            return new SceneWrapperImpl(new Scene((Parent) node), subViewController);
        } catch (IOException e) {
            throw new IllegalPathStateException(ERROR_FXMLNOTFOUND);
        }
    }

}
