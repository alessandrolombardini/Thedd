package thedd.view.controller;

import java.util.Objects;
import java.util.Optional;

import thedd.controller.Controller;
import thedd.view.View;
import thedd.view.dialog.DialogFactory;

/**
 * Implementation of the {@link SubViewController}.
 */
public class SubViewControllerImpl implements SubViewController {

    private static final String ERROR_ALREDYEXIST = "Has been alredy setted";
    private static final String ERROR_NOSETTED = "Component not yet setted";

    private Optional<View> view;
    private Optional<Controller> controller;
    private Optional<DialogFactory> dialogFactory;

    /**
     * Constructor of SubViewControllerImpl.
     */
    protected SubViewControllerImpl() { 
        this.view = Optional.empty();
        this.controller = Optional.empty();
        this.dialogFactory = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void init(final View view, final Controller controller) {
        Objects.requireNonNull(view);
        Objects.requireNonNull(controller);
        if (this.view.isPresent() || this.controller.isPresent()) {
            throw new IllegalStateException(ERROR_ALREDYEXIST);
        }
        this.view = Optional.of(view);
        this.controller = Optional.of(controller);
        this.startController();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setDialogFactory(final DialogFactory dialogFactory) {
        Objects.requireNonNull(dialogFactory);
        this.dialogFactory = Optional.of(dialogFactory);
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
     *
     * @throws IllegalStateException
     *          if the controller hasn't been setted
     */
    protected Controller getController() {
        if (!this.controller.isPresent()) {
            throw new IllegalStateException(ERROR_NOSETTED);
        }
        return this.controller.get();
    }

    /**
     * Getter of the View.
     * 
     * @return
     *          the view
     *
     * @throws IllegalStateException
     *          if the view hasn't been setted
     */
    protected View getView() {
        if (!this.view.isPresent()) {
            throw new IllegalStateException(ERROR_NOSETTED);
        }
        return this.view.get();
    }

    /**
     * Getter of the DialogFactory.
     * 
     * @return 
     *          the dialogFactory
     *
     * @throws IllegalStateException
     *          if the dialog hasn't been setted
     */
    protected DialogFactory getDialogFactory() {
        if (!this.dialogFactory.isPresent()) {
            throw new IllegalStateException(ERROR_NOSETTED);
        }
        return this.dialogFactory.get();
    }

}
