package thedd.view;

/**
 * Interface describing the view of the pattern MVC of this application.
 *
 */
public interface View {

    /**
     * Set scene of a given state.
     * 
     * @param state
     *          the state of application of the wanted view
     */
    void setScene(ApplicationViewState state);

    /**
     * Update the view.
     */
    void update();

}
