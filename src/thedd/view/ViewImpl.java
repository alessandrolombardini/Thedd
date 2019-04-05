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
import thedd.view.scenewrapper.SceneWrapper;
import thedd.view.scenewrapper.SceneWrapperFactory;
import thedd.view.scenewrapper.SceneWrapperFactoryImpl;

/**
 * Implementation of {@link View}.
 */
public class ViewImpl extends Application implements View {

    private static final String GAME_NAME = "The dark destruction";
    private static final double STAGE_WIDTH = Screen.getPrimary().getBounds().getWidth() / 4 * 3;
    private static final double STAGE_HEIGHT = Screen.getPrimary().getBounds().getHeight() / 4 * 3;
    private static final double STAGE_MIN_WIDTH = Screen.getPrimary().getBounds().getWidth() / 4 * 2;
    private static final double STAGE_MIN_HEIGHT = Screen.getPrimary().getBounds().getHeight() / 4 * 2;
    private static final ApplicationViewState FIRST_APP_STATE = ApplicationViewState.MENU; 

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
    public final void setView(final ApplicationViewState state) {
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
        this.stage.setMinHeight(STAGE_MIN_HEIGHT);
        this.stage.setMinWidth(STAGE_MIN_WIDTH);
        this.stage.setHeight(STAGE_HEIGHT);
        this.stage.setWidth(STAGE_WIDTH);
        this.stage.setResizable(true);
        this.setView(FIRST_APP_STATE);
    }

}
