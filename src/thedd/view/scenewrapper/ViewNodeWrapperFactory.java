package thedd.view.scenewrapper;

import thedd.view.ViewNode;

/**
 * Interface describing a {@link ViewNodeWrapper} factory. 
 */
public interface ViewNodeWrapperFactory {

    /**
     * Method to get a node with its controller.
     * 
     * @param viewNode
     *          view node of application
     * @return
     *          the node and its view controller
     */
    ViewNodeWrapper createViewNodeWrapper(ViewNode viewNode);
}
