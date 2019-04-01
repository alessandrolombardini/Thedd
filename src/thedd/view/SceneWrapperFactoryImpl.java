package thedd.view;

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

/**
 * Implementation of {@link SceneWrapperFactory}.
 */
public class SceneWrapperFactoryImpl implements SceneWrapperFactory {

    private final EnumMap<ApplicationState, Supplier<SceneWrapper>> viewSupplier;
    private final View view;
    private final Controller controller;

    {
        viewSupplier = new EnumMap<ApplicationState, Supplier<SceneWrapper>>(ApplicationState.class);
        viewSupplier.put(ApplicationState.PRE_MENU, () -> this.getOneNodeView(GameSubView.PRE_MENU));
        viewSupplier.put(ApplicationState.MENU, () -> this.getOneNodeView(GameSubView.MENU));
        viewSupplier.put(ApplicationState.NEW_GAME, () -> this.getOneNodeView(GameSubView.NEW_GAME));
        viewSupplier.put(ApplicationState.SETTINGS, () -> this.getThreeNodeView(GameSubView.MENU, 
                                                                                GameSubView.MENU, 
                                                                                GameSubView.MENU));
    }

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
    public final SceneWrapper getSubView(final ApplicationState state) {
        Objects.requireNonNull(state);
        return viewSupplier.get(state).get();
    }

    private SceneWrapper getOneNodeView(final GameSubView scene) {
        Objects.requireNonNull(scene);
        final List<SubViewControllerImpl> controllers = new ArrayList<>();
        final NodeWrapper content = this.loadNode(scene);
        controllers.add(content.getController());
        return new SceneWrapperImpl(new Scene((Parent) content.getNode()), controllers);
    }

    private SceneWrapper getThreeNodeView(final GameSubView sceneUpSx, final GameSubView sceneUpDx,
                                          final GameSubView sceneDown) {
        Objects.requireNonNull(sceneUpSx);
        Objects.requireNonNull(sceneUpDx);
        Objects.requireNonNull(sceneDown);
        final List<SubViewControllerImpl> controllers = new ArrayList<>();
        final BorderPane border = new BorderPane();
        NodeWrapper content = this.loadNode(sceneUpSx);
        border.setCenter(content.getNode());
        controllers.add(content.getController());
        content = this.loadNode(sceneUpDx);
        border.setBottom(content.getNode());
        controllers.add(content.getController());
        content = this.loadNode(sceneDown);
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
            throw new IllegalStateException();
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
