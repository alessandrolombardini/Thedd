package thedd.view.scenewrapper;

import java.util.Objects;

import javafx.scene.Node;
import thedd.view.controller.SubViewController;

/**
 * Implementations of {@link ViewNodeWrapper}.
 */
public class ViewNodeWrapperImpl implements ViewNodeWrapper {

    private final SubViewController subViewController;
    private final Node node;

    /**
     * Create a new instance of ViewNodeWrapper.
     * @param node
     *          node
     * @param viewController
     *          controller
     */
    public ViewNodeWrapperImpl(final Node node, final SubViewController viewController) {
        Objects.requireNonNull(node);
        Objects.requireNonNull(viewController);
        this.node = node;
        this.subViewController = viewController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubViewController getController() {
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
