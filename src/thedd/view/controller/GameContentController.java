package thedd.view.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import thedd.view.explorationpane.ExplorationPaneImpl;

public class GameContentController extends ViewNodeControllerImpl {

    @FXML
    private StackPane mainPane;

    @FXML
    private ExplorationPaneImpl pane;

    @Override
    public void update() {

    }

    @Override
    protected void initView() {
        pane.changeBackgroundImage(new Image(ClassLoader.getSystemResourceAsStream("bgimg.jpg")));

    }

}
