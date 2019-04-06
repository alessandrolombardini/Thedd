package thedd.view.scenewrapper;

import javafx.scene.Scene;
import thedd.view.controller.SubViewController;

/**
 * Interface describing a wrapper of scene and view controllers of its components.
 */
public interface SceneWrapper {

    /**
     * Getter of view controller of scene component.
     * 
     * @return 
     *          list of view controllers
     */
    SubViewController getController();

    /**
     * Getter of the scene.
     * 
     * @return
     *          the scene
     */
    Scene getScene();

}
