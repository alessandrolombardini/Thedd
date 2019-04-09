package thedd.view.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import thedd.view.ApplicationState;
import thedd.view.SubViewControllerImpl;

/**
 * View controller of scene menu.
 */
public class MainMenuController extends SubViewControllerImpl {

    /**
     * Load new game scene.
     */
    @FXML
    protected final void handleNewGameButtonAction() {
        this.getView().setView(ApplicationState.NEW_GAME);
    }

    /**
     * Open setting scene.
     */
    @FXML
    protected void handleSettingsButtonAction() {
        this.getView().setView(ApplicationState.SETTINGS);
    }

    /**
     * Close application.
     */
    @FXML
    protected void handleExitButtonAction() {
        Platform.exit();
    }
}
