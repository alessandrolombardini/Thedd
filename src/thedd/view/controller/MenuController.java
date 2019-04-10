package thedd.view.controller;

import javafx.fxml.FXML;
import thedd.view.ApplicationViewState;

/**
 * View controller of scene menu.
 */
public class MenuController extends SubViewControllerImpl {

    /**
     * Go to new game scene.
     */
    @FXML
    protected final void handleNewGameButtonAction() {
        this.getView().setScene(ApplicationViewState.NEW_GAME);
    }

    /**
     * Close the application.
     */
    @FXML
    protected void handleExitButtonAction() {
        this.getController().closeApplication();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        /* This class has nothing to update */
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initView() {
        /* This class has nothing to do when it's initialized */
    }

}
