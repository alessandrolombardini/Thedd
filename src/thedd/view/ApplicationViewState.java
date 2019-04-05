package thedd.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import thedd.view.scenewrapper.GameSubView;

/**
 * Enum descibing possible application status.
 */
public enum ApplicationViewState {

    /**
     * Menu state.
     */
    MENU(GameSubView.MENU),

    /**
     * State where the user set values of new game session.
     */
    NEW_GAME(GameSubView.NEW_GAME),

    /**
     * Game state.
     */
    GAME(Arrays.asList(GameSubView.MENU, GameSubView.MENU, GameSubView.MENU)),

    /**
     * Game over state.
     */
    GAME_OVER(GameSubView.GAME_OVER);

    private final List<GameSubView> gameSubViews;
    private final boolean isOneNode;

    ApplicationViewState(final List<GameSubView> subViews) {
        this.gameSubViews = Collections.unmodifiableList(subViews);
        this.isOneNode = false;
    }

    ApplicationViewState(final GameSubView subView) {
        this.gameSubViews = Arrays.asList(subView);
        this.isOneNode = true;
    }

    /**
     * Allows to get all one that makes the entire view state.
     * 
     * @return ordered list of all GameSubView nodes of this view state start from
     *         up-sx to down-dx.
     */
    public List<GameSubView> getSubViews() {
        return Collections.unmodifiableList(this.gameSubViews);
    }

    /**
     * Allows to know if this view state is made up by one node or more (three in
     * generaly).
     * 
     * @return true is this view state is made up by one node
     */
    public boolean isOneNode() {
        return this.isOneNode;
    }
}
