package thedd.view.controller;

import java.util.Objects;
import java.util.Optional;

import javafx.scene.control.Control;
import javafx.scene.layout.Region;
import thedd.controller.Controller;
import thedd.view.View;
import thedd.view.dialog.DialogFactory;

/**
 * Implementation of the {@link ViewNodeController}.
 */
public abstract class ViewNodeControllerImpl implements ViewNodeController {

    private static final String ERROR_ALREDYEXIST = "Has been alredy setted";
    private static final String ERROR_NOSETTED = "Component not yet setted";

    private Optional<View> view;
    private Optional<Controller> controller;
    private Optional<DialogFactory> dialogFactory;

    /**
     * Constructor of SubViewControllerImpl.
     */
    protected ViewNodeControllerImpl() { 
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
        this.initView();
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
    public abstract void update();

    /**
     * This method is called when the controller is initialized.
     * Should be used to do everything necessary to set correctly the view.
     */
    protected abstract void initView();

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

    /**
     * This method allows to bind a Control element to a Region element.
     * 
     * @param element component that has to be binded
     * @param node component Region
     * @param heightRate rate value of height
     * @param widthRate rate value of width
     */
    protected void setBind(final Control element, final Region node, final double heightRate, final double widthRate) {
        element.prefHeightProperty().bind(node.heightProperty().multiply(heightRate));
        element.prefWidthProperty().bind(node.widthProperty().multiply(widthRate));
    }


}
