package thedd.view.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import thedd.view.ApplicationViewState;

/**
 * View controller of scene menu.
 */
public class MenuController extends ViewNodeControllerImpl {

    private static final double BUTTON_SPACING = 35;
    private static final double BUTTON_HEIGHT_RATE = 0.08;
    private static final double BUTTON_WIDTH_RATE = 0.12;

    @FXML
    private BorderPane menu;

    @FXML
    private VBox vbox;

    @FXML
    private Button play;

    @FXML
    private Button exit;

    /**
     * Go to new game scene.
     */
    @FXML
    protected final void handleNewGameButtonAction() {
        this.getView().setScene(ApplicationViewState.NEW_GAME);
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
        final List<Control> buttons = new ArrayList<>(Arrays.asList(play, exit));
        buttons.forEach(b -> this.setBind(b, menu, BUTTON_HEIGHT_RATE, BUTTON_WIDTH_RATE));
        vbox.setSpacing(BUTTON_SPACING);
    }

}
