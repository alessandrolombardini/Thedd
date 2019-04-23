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
import thedd.view.nodewrapper.ViewNodeWrapper;

/**
 * Implementation of {@link View}.
 */
public class ViewImpl extends Application implements View {

    private static final String ERROR_STAGEUNSETTED = "Stage is not setted";
    private static final String GAME_NAME = "The dark destruction";
    private static final double WIDTH = 16;
    private static final double HEIGHT = 9;
    private static final double MIN_WIDTH = Screen.getPrimary().getBounds().getWidth() / WIDTH *  3;
    private static final double MIN_HEIGHT = Screen.getPrimary().getBounds().getHeight() / HEIGHT * 3;
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
    public final void showMessage(final String text) {
        this.getGameViewController().ifPresent(c -> c.showUserMessage(text));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void hideMessage() {
        this.getGameViewController().ifPresent(c -> c.hideUserMessage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void partialUpdate() {
        getGameViewController().ifPresent(c -> ((MainGameViewController) c).partialUpdate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update() {
        this.actualScene.ifPresent(c -> c.getController().update());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showInventory() {
        this.getGameViewController().ifPresent(c -> c.showInventory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showActionSelector() {
        this.getGameViewController().ifPresent(c -> c.showActionSelector());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showActionTargets(final List<ActionActor> targets, final List<ActionActor> alliedParty,
                                        final List<ActionActor> enemyParty, final Action action) {
        this.getGameViewController().ifPresent(c -> c.showTargets(targets, alliedParty, enemyParty, action));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void resetActionTargets() {
        this.getGameViewController().ifPresent(c -> c.hideTargets());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showActionEffect(final ActionResult result) {
        this.getGameViewController().ifPresent(c -> c.visualizeAction(result));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showActionResult(final ActionResult result) {
        this.getGameViewController().ifPresent(c -> this.getGameViewController().get().logAction(result));
    }

    /**
     * {@inheritDoc}.
     */
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
        stage.setResizable(true);
        stage.setHeight(STAGE_HEIGHT);
        stage.setWidth(STAGE_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
        stage.centerOnScreen();
        stage.heightProperty().addListener(l -> {
            stage.setWidth(stage.getHeight() * (WIDTH / HEIGHT));
        });
        stage.widthProperty().addListener(l -> {
            stage.setHeight(stage.getWidth() / (WIDTH / HEIGHT));
        });
        setState(FIRST_APP_STATE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void disableInteraction() {
        this.actualScene.ifPresent(s -> s.getController().disableInteraction());
    }


}
