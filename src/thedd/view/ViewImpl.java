package thedd.view;

import java.util.Objects;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import thedd.controller.Controller;
import thedd.controller.ControllerImpl;
import thedd.view.dialog.DialogFactory;
import thedd.view.dialog.DialogFactoryImpl;

/**
 * Implementation of {@link View}.
 */
public class ViewImpl extends Application implements View {

    private static final String GAME_NAME = "The dark destruction";
    private static final double MIN_WIDTH = Screen.getPrimary().getBounds().getWidth() / 3;
    private static final double MIN_HEIGHT = Screen.getPrimary().getBounds().getHeight() / 4;
    private static final ApplicationState FIRST_APP_STATE = ApplicationState.MENU; 

    private final SceneWrapperFactory sceneFactory;
    private final DialogFactory dialogFactory;
    private Stage stage;
    private Controller controller;
    private SceneWrapper actualSubView;
    private boolean viewStarted;

    /**
     * Create a new View instance.
     */
    public ViewImpl() {
        this.controller = new ControllerImpl(this);
        this.sceneFactory = new SceneWrapperFactoryImpl(this, controller);
        this.dialogFactory = new DialogFactoryImpl();
        this.viewStarted = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start(final Stage primaryStage) throws Exception {
        Objects.requireNonNull(primaryStage);
        this.stage = primaryStage;
        this.initView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setView(final ApplicationState state) {
        Objects.requireNonNull(state);
        this.actualSubView = this.sceneFactory.getSubView(state);
        this.actualSubView.getControllers().forEach(c -> c.setDialogFactory(this.dialogFactory));
        final Scene newScene = this.actualSubView.getScene();
        final double width = this.stage.getWidth();
        final double height = this.stage.getHeight();
        this.stage.setScene(newScene);
        this.stage.setWidth(width);
        this.stage.setHeight(height);
        if (!this.viewStarted) {
            this.stage.show();
            this.viewStarted = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update() {
        this.actualSubView.getControllers().forEach(c -> c.update());
    }

    private void initView() {
        this.stage.setTitle(GAME_NAME);
        this.stage.setMinHeight(MIN_HEIGHT);
        this.stage.setMinWidth(MIN_WIDTH);
        this.stage.setResizable(true);
        //this.stage.setMaximized(true);
        this.setView(FIRST_APP_STATE);
    }

}
