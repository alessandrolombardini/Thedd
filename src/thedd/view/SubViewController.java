package thedd.view;

import thedd.controller.Controller;
import thedd.view.dialog.DialogFactory;

/**
 * Interface describing a SubViewController.
 */
public interface SubViewController {

    /**
     * Setter of a dialog factory.
     * 
     * @param dialogFactory 
     *          dialog factory that has to be setted inside the view controller
     */
    void setDialogFactory(DialogFactory dialogFactory);

    /**
     * Initialize method of SubViewController.
     * 
     * @param view
     *          view reference
     * @param controller
     *          controller reference
     */
    void init(View view, Controller controller);

    /**
     * This method allows to update the view controlled by view controller.
     * 
     * Should be overrided if the view controller want to do something to refresh it self.
     */
    void update();
}
