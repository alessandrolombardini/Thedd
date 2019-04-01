package thedd.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Enum descibing possible application status. 
 */
public enum ApplicationState {

    /**
     * Initial state.
     */
    PRE_MENU(GameSubView.PRE_MENU), 

    /**
     * Menu state.
     */
    MENU(GameSubView.MENU), 

    /**
     * Settings state. 
     */
    SETTINGS(GameSubView.SETTING), 

    /**
     * State where the user set values of new game session. 
     */
    NEW_GAME(GameSubView.NEW_GAME), 

    /**
     * Game state.
     */
    GAME(Arrays.asList(GameSubView.MENU, GameSubView.MENU, GameSubView.MENU));

    private final List<GameSubView> subViews;

    ApplicationState(final List<GameSubView> subViews) {
        this.subViews = Collections.unmodifiableList(subViews);
    }

    ApplicationState(final GameSubView subViews) {
        this.subViews = Arrays.asList(subViews);
    }

    /**
     * Getter of sub views of given application status.
     * 
     * @return list of GameSubView
     */
    public List<GameSubView> getSubViews() {
        return Collections.unmodifiableList(this.subViews);
    }
}
