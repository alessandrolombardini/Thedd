package thedd.view;

/**
 * Interface describing a {@link SceneWrapper} factory. 
 */
public interface SceneWrapperFactory {

    /**
     * Method to get a scene with its controllers.
     * 
     * @param state
     *          state of application
     * @return
     *          the scene representing the application state 
     */
    SceneWrapper getSubView(ApplicationState state);
}
