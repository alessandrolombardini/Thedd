package thedd.view.scenewrapper;

import java.util.Objects;

import javafx.scene.Scene;
import thedd.view.controller.SubViewController;

/**
 * Implementations of {@link SceneWrapper}.
 */
public class SceneWrapperImpl implements SceneWrapper {

    private final SubViewController subViewController;
    private final Scene scene;

    /**
     * Create a new instance of SceneWrapper.
     * @param scene
     *          scene
     * @param viewController
     *          list of view controllers
     */
    public SceneWrapperImpl(final Scene scene, final SubViewController viewController) {
        Objects.requireNonNull(scene);
        Objects.requireNonNull(viewController);
        this.scene = scene;
        this.subViewController = viewController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Scene getScene() {
        return this.scene;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubViewController getController() {
        return this.subViewController;
    }

}
