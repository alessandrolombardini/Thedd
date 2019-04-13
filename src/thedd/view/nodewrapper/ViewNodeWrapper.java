package thedd.view.nodewrapper;

import javafx.scene.Node;
import thedd.view.controller.ViewNodeController;

/**
 * Interface describing a wrapper of node and its view controllers.
 */
public interface ViewNodeWrapper {

    /**
     * Getter of view controller of node.
     * 
     * @return 
     *          controller
     */
    ViewNodeController getController();

    /**
     * Getter of the node.
     * 
     * @return
     *          the node.
     */
    Node getNode();

}
