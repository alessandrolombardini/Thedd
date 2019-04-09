package thedd.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import thedd.view.ApplicationState;
import thedd.view.SubViewControllerImpl;

/**
 * View controller of the new game scene.
 */
public class PlayMenuController extends SubViewControllerImpl {

    private static final String ERROR_TITLE_INPUTERROR = "Input error";
    private static final String ERROR_TEXT_NULL = "Nope, you have to insert number of rooms and floors";
    private static final String ERROR_TEXT_NONVALIDVALUE = "Nope, values of rooms and floors not acceptable";
    private static final ApplicationState NEXT_SCENE = ApplicationState.MENU;

    @FXML
    private TextField playerNameField;

    @FXML
    private TextField numberOfRoomsField;

    @FXML
    private TextField numberOfFloorsField;

    @FXML
    private Label errorLabel;

    /**
     * 
     * @param event
     */
    @FXML
    protected void handlePlayButtonAction() {
        if (this.numberOfRoomsField.getText().isEmpty() || this.numberOfFloorsField.getText().isEmpty()) {
            this.getDialogFactory().createErrorDialog(ERROR_TITLE_INPUTERROR, ERROR_TEXT_NULL).show();
            return;
        }
        final boolean isGoodValues = this.getController().newGame(this.playerNameField.getText(),
                                                                  this.numberOfFloorsField.getText(), 
                                                                  this.numberOfFloorsField.getText());
        if (!isGoodValues) {
            this.getDialogFactory().createErrorDialog(ERROR_TITLE_INPUTERROR, ERROR_TEXT_NONVALIDVALUE).show();
            return;
        }
        this.getView().setView(NEXT_SCENE);
    }
}
