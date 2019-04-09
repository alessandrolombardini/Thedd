package thedd.view.controller;

import javafx.fxml.FXML;
import thedd.view.ApplicationState;
import thedd.view.SubViewControllerImpl;

/**
 * 
 */
public class SettingsController extends SubViewControllerImpl {

    /**
     * Load menu scene.
     */
    @FXML
    protected void handleBackButtonAction() {
        this.getView().setView(ApplicationState.MENU);
    }

}
