package thedd.view.scenewrapper;

import javafx.scene.Node;
import thedd.view.controller.SubViewController;

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
    SubViewController getController();

    /**
     * Getter of the node.
     * 
     * @return
     *          the node.
     */
    Node getNode();

}
