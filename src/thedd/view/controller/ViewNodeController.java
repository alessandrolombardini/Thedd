package thedd.view.controller;

import thedd.controller.Controller;
import thedd.view.View;

/**
 * Interface describing a SubViewController.
 */
public interface ViewNodeController {

    /**
     * Initialize the view controller.
     * 
     * @param view
     *          view reference
     * @param controller
     *          controller reference
     * @throws IllegalStateException
     *          if view or controller has been alredy setted
     */
    void init(View view, Controller controller);

    /**
     * This method allows to update the view controlled by view controller.
     */
    void update();

    /**
     * This method allows to disable the interaction.
     */
    void disableInteraction();

    /**
     * This method allows to enable the interaction.
     */
    void enableInteraction();

}
