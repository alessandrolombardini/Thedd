package thedd.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import thedd.view.ApplicationViewState;

/**
 * View controller of the new game scene.
 */
public class NewGameController extends SubViewControllerImpl {

    private static final String ERROR_TITLE_INPUTERROR = "Input error";
    private static final String ERROR_TEXT_NULL = "Nope, you have to insert number of rooms and floors";
    private static final String ERROR_TEXT_NONVALIDVALUE = "Nope, the number of rooms and/or floors is not acceptable";

    @FXML
    private TextField playerNameField;

    @FXML
    private TextField numberOfRoomsField;

    @FXML
    private TextField numberOfFloorsField;

    /**
     *Start new game.
     */
    @FXML
    protected void handlePlayButtonAction() {
        if (this.numberOfRoomsField.getText().isEmpty() || this.numberOfFloorsField.getText().isEmpty()) {
            this.getDialogFactory().createErrorDialog(ERROR_TITLE_INPUTERROR, ERROR_TEXT_NULL).show();
        } else if (!this.getController().newGame(this.playerNameField.getText(),
                                                 this.numberOfFloorsField.getText(), 
                                                 this.numberOfFloorsField.getText())) {
            this.getDialogFactory().createErrorDialog(ERROR_TITLE_INPUTERROR, ERROR_TEXT_NONVALIDVALUE).show();
        } else {
            this.getView().setScene(ApplicationViewState.GAME_OVER);
        }
    }
}
