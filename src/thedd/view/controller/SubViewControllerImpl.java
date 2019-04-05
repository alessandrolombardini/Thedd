package thedd.view.controller;

import java.util.Objects;

import thedd.controller.Controller;
import thedd.view.View;
import thedd.view.dialog.DialogFactory;

/**
 * Implementation of the {@link SubViewController}.
 */
public class SubViewControllerImpl implements SubViewController {

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
    @Override
    public final void init(final View view, final Controller controller) {
        Objects.requireNonNull(view);
        Objects.requireNonNull(controller);
        this.view = view;
        this.controller = controller;
        this.startController();
    }

    /**
     * Setter of {@link DialogFactory}.
     * 
     * @param dialogFactory
     *          dialogFactory reference
     */
    @Override
    public final void setDialogFactory(final DialogFactory dialogFactory) {
        Objects.requireNonNull(dialogFactory);
        this.dialogFactory = dialogFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() { }

    /**
     * This method is called when the controller is initialized.
     * Should be overrided if the view controller need to do something when it's initialized.
     */
    protected void startController() { }

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

}
