package thedd.view.controller;

import thedd.controller.Controller;
import thedd.view.View;
import thedd.view.dialog.DialogFactory;

/**
 * Interface describing a SubViewController.
 */
public interface SubViewController {

    /**
     * Setter of {@link DialogFactory}.
     * 
     * @param dialogFactory
     *          dialogFactory reference
     */
    void setDialogFactory(DialogFactory dialogFactory);

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
     * 
     * Should be overrided if the view controller want to do something to refresh it self
     * when it's called the refresh of the view.
     */
    void update();
}
