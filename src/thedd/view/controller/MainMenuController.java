package thedd.view.controller;

import javafx.fxml.FXML;
import thedd.view.ApplicationViewState;

/**
 * View controller of scene menu.
 */
public class MainMenuController extends SubViewControllerImpl {

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

}
