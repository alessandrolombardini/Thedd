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

    private static final String NEW_GAME_TITLE_URL = "images" + System.getProperty("file.separator") + "titles"
                                                     + System.getProperty("file.separator") + "gameOver_title.png";
    private static final double TITLE_HEIGHT_PERC = 1.0;
    private static final double TITLE_WIDTH_PERC = 1.0;
    private static final String ERROR_UNVALIDVALUE = "Should be greater than 0.";
    private static final String ERROR_UNPRESENTVALUE = "Should be present.";
    private static final String ERROR_VOID = " ";

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
    private AdaptiveFontLabel errorNumberOfRooms;

    @FXML
    private AdaptiveFontLabel errorNumberOfFloors;

    @FXML
    private AnchorPane newGameTitleImage;

    /**
     *Start new game.
     */
    @FXML
    protected void handlePlayButtonAction() {
        this.errorNumberOfFloors.setText(ERROR_VOID);
        this.errorNumberOfRooms.setText(ERROR_VOID);
        if (this.numberOfFloorsTextField.getText().isEmpty()) {
            this.errorNumberOfFloors.setText(ERROR_UNPRESENTVALUE);
        } else if (!this.getController().isValidNumberOfFloors(this.numberOfFloorsTextField.getText())) {
            this.errorNumberOfFloors.setText(ERROR_UNVALIDVALUE);
        }
        if (this.numberOfRoomsTextField.getText().isEmpty()) {
            this.errorNumberOfRooms.setText(ERROR_UNPRESENTVALUE);
        } else if (!this.getController().isValidNumberOfRooms(this.numberOfRoomsTextField.getText())) {
            this.errorNumberOfRooms.setText(ERROR_UNVALIDVALUE);
        }
        if (this.getController().newGame(this.playerNameTextField.getText(),
                                         this.numberOfFloorsTextField.getText(), 
                                         this.numberOfFloorsTextField.getText())) {
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
