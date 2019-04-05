package thedd.view.scenewrapper;

import java.util.List;

import javafx.scene.Scene;
import thedd.view.controller.SubViewControllerImpl;

/**
 * Interface describing a wrapper of scene and view controllers of its components.
 */
public interface SceneWrapper {

    /**
     * Getter of view controllers of scene components.
     * 
     * @return 
     *          list of view controllers
     */
    List<SubViewControllerImpl> getControllers();

    /**
     * Getter of the scene.
     * 
     * @return
     *          the scene
     */
    Scene getScene();

}
