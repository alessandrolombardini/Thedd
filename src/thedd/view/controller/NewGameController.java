package thedd.view.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import thedd.view.ApplicationViewState;
import thedd.view.extensions.AdaptiveFontLabel;

/**
 * View controller of the new game scene.
 */
public class NewGameController extends ViewNodeControllerImpl {

    private static final String ERROR_TITLE_INPUTERROR = "Input error";
    private static final String ERROR_TEXT_NULL = "Nope, you have to insert number of rooms and floors";
    private static final String ERROR_TEXT_NONVALIDVALUE = "Nope, the number of rooms and/or floors is not acceptable";
    private static final String NEW_GAME_TITLE_URL = "images" + System.getProperty("file.separator") + "titles"
                                                      + System.getProperty("file.separator") + "gameOver_title.png";
    private static final double TITLE_HEIGHT_PERC = 1.0;
    private static final double TITLE_WIDTH_PERC = 1.0;

    @FXML
    private TextField playerNameTextField;

    @FXML
    private TextField numberOfRoomsTextField;

    @FXML
    private TextField numberOfFloorsTextField;

    @FXML
    private AdaptiveFontLabel playerNameLabel;

    @FXML
    private AdaptiveFontLabel numberOfRoomsLabel;

    @FXML
    private AdaptiveFontLabel numberOfFloorsLabel;

    @FXML
    private AnchorPane newGameTitleImage;


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
            this.getView().setState(ApplicationViewState.GAME);
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
        final Image imageNewGame = new Image(NEW_GAME_TITLE_URL);
        final BackgroundImage backgroundGameOver = new BackgroundImage(imageNewGame, BackgroundRepeat.NO_REPEAT, 
                                                               BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                                                               new BackgroundSize(TITLE_HEIGHT_PERC, TITLE_WIDTH_PERC, 
                                                               true, true, true, false));
        this.newGameTitleImage.setBackground(new Background(backgroundGameOver));
        this.playerNameLabel.setAlignment(Pos.CENTER_RIGHT);
        this.numberOfRoomsLabel.setAlignment(Pos.CENTER_RIGHT);
        this.numberOfFloorsLabel.setAlignment(Pos.CENTER_RIGHT);
    }
}
