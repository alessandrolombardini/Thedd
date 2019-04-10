package thedd.view.controller;

import javafx.fxml.FXML;
import thedd.view.ApplicationViewState;

/**
 * View controller of game over scene.
 */
public class GameOverController extends ViewNodeControllerImpl {

    /**
     * Back to menu scene.
     */
    @FXML
    protected final void handleBackToMenuButtonAction() {
        this.getView().setScene(ApplicationViewState.MENU);
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
        /* This class has nothing to do when it's initialized */
    }

}
