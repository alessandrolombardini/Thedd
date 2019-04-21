package thedd.view;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import thedd.controller.Controller;
import thedd.controller.ControllerImpl;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.result.ActionResult;
import thedd.model.combat.actor.ActionActor;
import thedd.view.controller.MainGameViewController;
import thedd.view.controller.interfaces.GameView;
import thedd.view.explorationpane.confirmationdialog.OptionDialog;
import thedd.view.nodewrapper.ViewNodeWrapper;

/**
 * Implementation of {@link View}.
 */
public class ViewImpl extends Application implements View {

    private static final String ERROR_STAGEUNSETTED = "Stage is not setted";

    private static final String GAME_NAME = "The dark destruction";
    private static final double WIDTH = 16;
    private static final double HEIGHT = 9;
    private static final double STAGE_WIDTH = Screen.getPrimary().getBounds().getWidth() / WIDTH * HEIGHT;
    private static final double STAGE_HEIGHT = Screen.getPrimary().getBounds().getHeight() / WIDTH * HEIGHT;
    private static final ApplicationViewState FIRST_APP_STATE = ApplicationViewState.MENU;

    private final Controller controller;
    private Optional<Stage> stage;
    private Optional<ViewNodeWrapper> actualScene;
    private Optional<ApplicationViewState> actualViewState;
    private boolean viewStarted;

    /**
     * Create a new View instance.
     */
    public ViewImpl() {
        super();
        this.controller = new ControllerImpl(this);
        this.stage = Optional.empty();
        this.actualScene = Optional.empty();
        this.actualViewState = Optional.empty();
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
        this.stage.get().centerOnScreen();
        this.initView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setState(final ApplicationViewState state) {
        Objects.requireNonNull(state);
        if (!this.stage.isPresent()) {
            throw new IllegalStateException(ERROR_STAGEUNSETTED);
        }
        this.actualScene = Optional.of(ViewNodeWrapper.createViewNodeWrapper(state.getViewNode(), 
                                                                                    this.controller, 
                                                                                    this));
        this.actualViewState = Optional.of(state);
        this.actualScene.get().getController().init(this, this.controller);
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

    @Override
    public final void showInventory() {
        if (this.getGameViewController().isPresent()) {
            this.getGameViewController().get().showInventory();
        }
    }

    @Override
    public final void showActionSelector() {
        if (this.getGameViewController().isPresent()) {
            this.getGameViewController().get().showActionSelector();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showActionTargets(final List<ActionActor> targets, final List<ActionActor> alliedParty,
                                        final List<ActionActor> enemyParty, final Action action) {
        if (this.getGameViewController().isPresent()) {
            this.getGameViewController().get().showTargets(targets, alliedParty, enemyParty, action);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void resetActionTargets() {
        if (this.getGameViewController().isPresent()) {
            this.getGameViewController().get().hideTargets();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showActionEffect(final ActionResult result) {
        if (this.getGameViewController().isPresent()) {
            this.getGameViewController().get().visualizeAction(result);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showActionResult(final List<ActionResult> result) {
        if (this.getGameViewController().isPresent()) {
            this.getGameViewController().get().logAction(result);
        }
    }

    private Optional<GameView> getGameViewController() {
        if (this.actualViewState.isPresent() && this.actualViewState.get() == ApplicationViewState.GAME
                && this.actualScene.get().getController() instanceof MainGameViewController) {
            final GameView gameView = (GameView) (this.actualScene.get().getController());
            return Optional.of(gameView);
        }
        return Optional.empty();
    }

    private void initView() {
        if (!this.stage.isPresent()) {
            throw new IllegalStateException(ERROR_STAGEUNSETTED);
        }
        final Stage stage = this.stage.get();
        stage.setTitle(GAME_NAME);
        stage.setHeight(STAGE_HEIGHT);
        stage.setWidth(STAGE_WIDTH);
        stage.setResizable(true);
        setState(FIRST_APP_STATE);
    }

    @Override
    public final void showMessage(final String text) {
        this.getGameViewController().ifPresent(c -> c.showUserMessage(text));
    }

    @Override
    public final void hideMessage() {
        this.getGameViewController().ifPresent(c -> c.hideUserMessage());
    }

}
