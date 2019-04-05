package thedd.view;

/**
 * Interface describing a {@link SceneWrapper} factory. 
 */
public interface SceneWrapperFactory {

    /**
     * Method to get a scene with its component's controllers.
     * 
     * @param state
     *          state of application
     * @return
     *          the scene and its view controllers representing the application state 
     */
    SceneWrapper getSubView(ApplicationViewState state);
}
