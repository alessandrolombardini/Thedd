package thedd.view.scenewrapper;

import java.awt.geom.IllegalPathStateException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import thedd.controller.Controller;
import thedd.view.ApplicationViewState;
import thedd.view.View;
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
    public final SceneWrapper getSubView(final ApplicationViewState state) {
        Objects.requireNonNull(state);
        return state.isOneNode() ? this.getOneNodeView(state.getSubViews().stream().findFirst().get())
                                 : this.getThreeNodeView(state.getSubViews());
    }

    private SceneWrapper getOneNodeView(final GameSubView subView) {
        Objects.requireNonNull(subView);
        final List<SubViewControllerImpl> controllers = new ArrayList<>();
        final NodeWrapper content = this.loadNode(subView);
        controllers.add(content.getController());
        return new SceneWrapperImpl(new Scene((Parent) content.getNode()), controllers);
    }

    private SceneWrapper getThreeNodeView(final List<GameSubView> subViews) {
        /** 
         * Da rifare per due motivi:
         * - 1 Non so come gestire la suddivisione dello schermo 
         * - 2 Non so come 
         */
        Objects.requireNonNull(subViews);
        final List<SubViewControllerImpl> controllers = new ArrayList<>();
        final BorderPane border = new BorderPane();
        NodeWrapper content = this.loadNode(subViews.get(0));
        border.setCenter(content.getNode());
        controllers.add(content.getController());
        content = this.loadNode(subViews.get(1));
        border.setBottom(content.getNode());
        controllers.add(content.getController());
        content = this.loadNode(subViews.get(2));
        border.setTop(content.getNode());
        controllers.add(content.getController());
        return new SceneWrapperImpl(new Scene(border), controllers);
    }

    private NodeWrapper loadNode(final GameSubView nodeIdentifier) {
        Objects.requireNonNull(nodeIdentifier);
        try {
            final FXMLLoader loader = new FXMLLoader();
            final URL location = ClassLoader.getSystemClassLoader().getResource(nodeIdentifier.getFXMLPath());
            loader.setLocation(location);
            final Node node = (Node) loader.load();
            final SubViewControllerImpl subViewController = loader.getController();
            subViewController.init(view, controller);
            return new NodeWrapper(node, subViewController);
        } catch (IOException e) {
            throw new IllegalPathStateException(ERROR_FXMLNOTFOUND);
        }
    }

    private class NodeWrapper {

        private final Node node;
        private final SubViewControllerImpl controller;

        NodeWrapper(final Node node, final SubViewControllerImpl subVieController) {
            Objects.requireNonNull(node);
            Objects.requireNonNull(subVieController);
            this.node = node;
            this.controller = subVieController;
        }

        public Node getNode() {
            return this.node;
        }

        public SubViewControllerImpl getController() {
            return this.controller;
        }
    }

}
