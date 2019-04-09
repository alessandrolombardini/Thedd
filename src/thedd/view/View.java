package thedd.view;

/**
 * Interface describing the view of the pattern MVC of this application.
 *
 */
public interface View {

    /**
     * Set view of a given state.
     * 
     * @param state
     *          the state of application of the wanted view
     */
    void setView(ApplicationState state);

    /**
     * Update the view.
     */
    void update();

}
