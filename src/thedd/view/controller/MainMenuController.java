package thedd.view.controller;

import javafx.fxml.FXML;
import thedd.view.ApplicationViewState;
import thedd.view.SubViewControllerImpl;

/**
 * View controller of scene menu.
 */
public class MainMenuController extends SubViewControllerImpl {

    /**
     * Go to new game scene.
     */
    @FXML
    protected final void handleNewGameButtonAction() {
        this.getView().setView(ApplicationViewState.NEW_GAME);
    }

    /**
     * Close the application.
     */
    @FXML
    protected void handleExitButtonAction() {
        this.getController().closeApplication();
    }

}
