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
import thedd.view.imageloader.DirectoryPicker;
import thedd.view.imageloader.ImageLoaderImpl;

/**
 * View controller of scene menu.
 */
public class MenuController extends ViewNodeControllerImpl {

    private static final String TITLE_IMAGE_NAME = "title";
    private static final double TITLE_HEIGHT_PERC = 1.0;
    private static final double TITLE_WIDTH_PERC = 1.0;

    @FXML
    private AnchorPane titleImage;

    /**
     * Go to new game scene.
     */
    @FXML
    protected final void handleNewGameButtonAction() {
        this.getView().setState(ApplicationViewState.NEW_GAME);
    }

    /**
     * Close the application.
     */
    @FXML
    protected void handleExitButtonAction() {
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
        final Image imageMenu = new ImageLoaderImpl().loadSingleImage(DirectoryPicker.TITLES, TITLE_IMAGE_NAME);
        final BackgroundImage backgroundMenu = new BackgroundImage(imageMenu, BackgroundRepeat.NO_REPEAT, 
                                                               BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                                                               new BackgroundSize(TITLE_HEIGHT_PERC, TITLE_WIDTH_PERC, 
                                                               true, true, true, false));
        this.titleImage.setBackground(new Background(backgroundMenu));
    }

}
