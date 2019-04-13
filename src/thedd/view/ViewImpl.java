package thedd.view;

import java.util.Objects;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import thedd.controller.Controller;
import thedd.controller.ControllerImpl;
import thedd.view.dialog.DialogFactory;
import thedd.view.dialog.DialogFactoryImpl;
import thedd.view.scenewrapper.ViewNodeWrapper;
import thedd.view.scenewrapper.ViewNodeWrapperFactory;

/**
 * Implementation of {@link View}.
 */
public class ViewImpl extends Application implements View {

    private static final String ERROR_STAGEUNSETTED = "Stage is not setted";

    private static final String GAME_NAME = "The dark destruction";
    private static final double STAGE_WIDTH = Screen.getPrimary().getBounds().getWidth() / 4 * 3;
    private static final double STAGE_HEIGHT = Screen.getPrimary().getBounds().getHeight() / 4 * 3;
    private static final double STAGE_MIN_WIDTH = Screen.getPrimary().getBounds().getWidth() / 4 * 2;
    private static final double STAGE_MIN_HEIGHT = Screen.getPrimary().getBounds().getHeight() / 4 * 2;
    private static final ApplicationViewState FIRST_APP_STATE = ApplicationViewState.MENU;

    private final DialogFactory dialogFactory;
    private final Controller controller;
    private Optional<Stage> stage;
    private Optional<ViewNodeWrapper> actualScene;
    private boolean viewStarted;

    /**
     * Create a new View instance.
     */
    public ViewImpl() {
        super();
        this.controller = new ControllerImpl(this);
        this.dialogFactory = new DialogFactoryImpl();
        this.stage = Optional.empty();
        this.actualScene = Optional.empty();
        this.viewStarted = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start(final Stage primaryStage) throws Exception {
        Objects.requireNonNull(primaryStage);
        if (this.stage.isPresent()) {
            throw new IllegalStateException(ERROR_STAGEUNSETTED);
        }
        this.stage = Optional.of(primaryStage);
        this.initView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setScene(final ApplicationViewState state) {
        Objects.requireNonNull(state);
        if (!this.stage.isPresent()) {
            throw new IllegalStateException(ERROR_STAGEUNSETTED);
        }
        this.actualScene = Optional.of(ViewNodeWrapperFactory.createViewNodeWrapper(state.getViewNode(), 
                                                                                    this.controller, 
                                                                                    this));
        this.actualScene.get().getController().setDialogFactory(this.dialogFactory);
        this.actualScene.get().getController().update();
        final Scene newScene = new Scene((Parent) this.actualScene.get().getNode());
        final Stage stage = this.stage.get();
        final double width = stage.getWidth();
        final double height = stage.getHeight();
        stage.setScene(newScene);
        stage.setWidth(width);
        stage.setHeight(height);
        if (!this.viewStarted) {
            stage.show();
            this.viewStarted = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update() {
        if (this.actualScene.isPresent()) {
            this.actualScene.get().getController().update();
        }
    }

    private void initView() {
        if (!this.stage.isPresent()) {
            throw new IllegalStateException(ERROR_STAGEUNSETTED);
        }
        final Stage stage = this.stage.get();
        stage.setTitle(GAME_NAME);
        stage.setMinHeight(STAGE_MIN_HEIGHT);
        stage.setMinWidth(STAGE_MIN_WIDTH);
        stage.setHeight(STAGE_HEIGHT);
        stage.setWidth(STAGE_WIDTH);
        stage.setResizable(true);
        setScene(FIRST_APP_STATE);
    }

}
