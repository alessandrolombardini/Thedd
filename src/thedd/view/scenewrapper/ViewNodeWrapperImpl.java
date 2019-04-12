package thedd.view.scenewrapper;

import java.util.Objects;

import javafx.scene.Node;
import thedd.view.controller.ViewNodeController;

/**
 * Implementations of {@link ViewNodeWrapper}.
 */
public class ViewNodeWrapperImpl implements ViewNodeWrapper {

    private final ViewNodeController subViewController;
    private final Node node;

    /**
     * Create a new instance of ViewNodeWrapper.
     * @param node
     *          node
     * @param viewController
     *          controller
     */
    public ViewNodeWrapperImpl(final Node node, final ViewNodeController viewController) {
        Objects.requireNonNull(node);
        Objects.requireNonNull(viewController);
        this.node = node;
        this.subViewController = viewController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewNodeController getController() {
        return this.subViewController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getNode() {
        return this.node;
    }

}
