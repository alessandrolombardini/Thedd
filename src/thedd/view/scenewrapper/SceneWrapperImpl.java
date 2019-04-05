package thedd.view.scenewrapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javafx.scene.Scene;
import thedd.view.controller.SubViewControllerImpl;

/**
 * Implementations of {@link SceneWrapper}.
 */
public class SceneWrapperImpl implements SceneWrapper {

    private static final String ERROR_UNVALIDCONTROLLERS = "Number of view controller is not enought";
    private static final int MIN_NUM_OF_VIEW_CONTROLLERS = 1;

    private final List<SubViewControllerImpl> subViewController;
    private final Scene scene;

    /**
     * Create a new instance of SceneWrapper.
     * @param scene
     *          scene
     * @param viewControllers
     *          list of view controllers
     */
    public SceneWrapperImpl(final Scene scene, final List<SubViewControllerImpl> viewControllers) {
        Objects.requireNonNull(scene);
        Objects.requireNonNull(viewControllers);
        if (viewControllers.size() < MIN_NUM_OF_VIEW_CONTROLLERS) {
            throw new IllegalArgumentException(ERROR_UNVALIDCONTROLLERS);
        }
        this.scene = scene;
        this.subViewController = Collections.unmodifiableList(viewControllers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<SubViewControllerImpl> getControllers() {
        return Collections.unmodifiableList(this.subViewController);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Scene getScene() {
        return this.scene;
    }

}
