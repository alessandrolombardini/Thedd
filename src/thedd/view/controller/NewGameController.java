package thedd.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import thedd.view.ApplicationViewState;

/**
 * View controller of the new game scene.
 */
public class NewGameController extends ViewNodeControllerImpl {

    private static final String ERROR_TITLE_INPUTERROR = "Input error";
    private static final String ERROR_TEXT_NULL = "Nope, you have to insert number of rooms and floors";
    private static final String ERROR_TEXT_NONVALIDVALUE = "Nope, the number of rooms and/or floors is not acceptable";

    @FXML
    private TextField playerNameTextField;

    @FXML
    private TextField numberOfRoomsTextField;

    @FXML
    private TextField numberOfFloorsTextField;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label numberOfRoomsLabel;

    @FXML
    private Label numberOfFloorsLabel;

    @FXML
    private Button playButton;

    //@FXML
    //private BorderPane newGame;


    /**
     *Start new game.
     */
    @FXML
    protected void handlePlayButtonAction() {
        if (this.numberOfRoomsTextField.getText().isEmpty() || this.numberOfFloorsTextField.getText().isEmpty()) {
            this.getDialogFactory().createErrorDialog(ERROR_TITLE_INPUTERROR, ERROR_TEXT_NULL).show();
        } else if (!this.getController().newGame(this.playerNameTextField.getText(),
                                                 this.numberOfFloorsTextField.getText(), 
                                                 this.numberOfFloorsTextField.getText())) {
            this.getDialogFactory().createErrorDialog(ERROR_TITLE_INPUTERROR, ERROR_TEXT_NONVALIDVALUE).show();
        } else {
            this.getView().setScene(ApplicationViewState.GAME);
        }
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
        /* This class has nothing to init */
    }
}
