package thedd.view.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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

    @FXML
    private BorderPane newGame;

    private static final double LABEL_HEIGHT_RATE = 0.10;
    private static final double LABEL_WIDTH_RATE = 0.20;
    private static final double TEXT_HEIGHT_RATE = 0.10;
    private static final double TEXT_WIDTH_RATE = 0.20;
    private static final double BUTTON_HEIGHT_RATE = 0.08;
    private static final double BUTTON_WIDTH_RATE = 0.12;

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
        final List<Control> labels = new ArrayList<>();
        final List<Control> texts = new ArrayList<>();
        labels.addAll(Arrays.asList(numberOfFloorsLabel, numberOfRoomsLabel, playerNameLabel));
        texts.addAll(Arrays.asList(numberOfFloorsTextField, numberOfRoomsTextField, playerNameTextField));
        labels.forEach(c -> this.setBind(c, this.newGame, LABEL_HEIGHT_RATE, LABEL_WIDTH_RATE));
        texts.forEach(c -> this.setBind(c, this.newGame, TEXT_HEIGHT_RATE, TEXT_WIDTH_RATE));
        this.setBind(this.playButton, this.newGame, BUTTON_HEIGHT_RATE, BUTTON_WIDTH_RATE);
        this.numberOfFloorsTextField.setAlignment(Pos.CENTER);
        this.numberOfRoomsTextField.setAlignment(Pos.CENTER);
        this.playerNameTextField.setAlignment(Pos.CENTER);
    }
}
