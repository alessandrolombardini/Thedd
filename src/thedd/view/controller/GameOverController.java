package thedd.view.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import thedd.view.ApplicationViewState;

/**
 * View controller of game over scene.
 */
public class GameOverController extends ViewNodeControllerImpl {

    @FXML
    private AnchorPane gameOverTitleImage;

    private static final String GAME_OVER_TITLE_URL = "images" + System.getProperty("file.separator") + "titles"
                                                       + System.getProperty("file.separator") + "gameOver_title.png";
    private static final double TITLE_HEIGHT_PERC = 1.0;
    private static final double TITLE_WIDTH_PERC = 1.0;

    /**
     * Back to menu scene.
     */
    @FXML
    protected final void handleBackToMenuButtonAction() {
        this.getView().setState(ApplicationViewState.MENU);
    }

    /**
     * Close the application.
     */
    @FXML
    protected void handleCloseButtonAction() {
        this.getController().closeApplication();
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
        final Image imageGameOver = new Image(GAME_OVER_TITLE_URL);
        final BackgroundImage backgroundGameOver = new BackgroundImage(imageGameOver, BackgroundRepeat.NO_REPEAT, 
                                                               BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                                                               new BackgroundSize(TITLE_HEIGHT_PERC, TITLE_WIDTH_PERC, 
                                                               true, true, true, false));
        this.gameOverTitleImage.setBackground(new Background(backgroundGameOver));
    }

}
