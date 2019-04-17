package thedd.view.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import thedd.view.ApplicationViewState;

/**
 * View controller of game over scene.
 */
public class GameOverController extends ViewNodeControllerImpl {


    private static final double BUTTON_SPACING = 30;
    private static final double BUTTON_HEIGHT_RATE = 0.08;
    private static final double BUTTON_WIDTH_RATE = 0.20;

    @FXML
    private VBox vbox;

    @FXML
    private Button menuButton;

    @FXML
    private Button exitButton;

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
        final List<Control> buttons = new ArrayList<>(Arrays.asList(this.menuButton, this.exitButton));
        vbox.setSpacing(BUTTON_SPACING);
        buttons.forEach(button -> this.setBind(button, vbox, BUTTON_HEIGHT_RATE, BUTTON_WIDTH_RATE));
    }

}
