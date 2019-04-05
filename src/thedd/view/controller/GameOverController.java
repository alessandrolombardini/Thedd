package thedd.view.controller;

import javafx.fxml.FXML;
import thedd.view.ApplicationViewState;

/**
 * View controller of game over scene.
 */
public class GameOverController extends SubViewControllerImpl {

    /**
     * Back to menu scene.
     */
    @FXML
    protected final void handleBackToMenuButtonAction() {
        this.getView().setView(ApplicationViewState.MENU);
    }

    /**
     * Close the application.
     */
    @FXML
    protected void handleCloseButtonAction() {
        this.getController().closeApplication();
    }

}
