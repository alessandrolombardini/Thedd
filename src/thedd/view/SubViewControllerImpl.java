package thedd.view;

import java.util.Objects;

import thedd.controller.Controller;
import thedd.view.dialog.DialogFactory;

/**
 * Implementation of the {@link SubViewController}.
 */
public class SubViewControllerImpl {

    private View view;
    private Controller controller;
    private DialogFactory dialogFactory;

    /**
     * Constructor of SubViewControllerImpl.
     */
    protected SubViewControllerImpl() { }

    /**
     * Initialize the scene controller.
     * 
     * @param view
     *          view reference
     * @param controller
     *          controller reference
     */
    public void init(final View view, final Controller controller) {
        Objects.requireNonNull(view);
        Objects.requireNonNull(controller);
        this.view = view;
        this.controller = controller;
    }

    /**
     * Setter of {@link DialogFactory}.
     * 
     * @param dialogFactory
     *          dialogFactory reference
     */
    public final void setDialogFactory(final DialogFactory dialogFactory) {
        Objects.requireNonNull(dialogFactory);
        this.dialogFactory = dialogFactory;
    }

    /**
     * Getter of the Controller.
     * 
     * @return
     *          the controller
     */
    protected Controller getController() {
        return this.controller;
    }

    /**
     * Getter of the View.
     * 
     * @return
     *          the view
     */
    protected View getView() {
        return this.view;
    }

    /**
     * Getter of the DialogFactory.
     * 
     * @return 
     *          the dialogFactory
     */
    protected DialogFactory getDialogFactory() {
        return this.dialogFactory;
    }

    /**
     * This method could be overrided by under class to set what they have to do 
     * when the view require to be updated.
     */
    protected void update() { }
}
