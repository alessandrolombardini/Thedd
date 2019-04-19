package thedd.view;

/**
 * Enum descibing possible application status.
 */
public enum ApplicationViewState {

    /**
     * Menu state.
     */
    MENU(ViewNode.MENU),

    /**
     * State where the user set values of new game session.
     */
    NEW_GAME(ViewNode.NEW_GAME),

    /**
     * Game state.
     */
    GAME(ViewNode.GAME),

    /**
     * Game over state.
     */
    END_GAME(ViewNode.END_GAME);

    private final ViewNode gameSubView;

    ApplicationViewState(final ViewNode subView) {
        this.gameSubView = subView;
    }

    /**
     * Allows to get the view node that describe this view state.
     * 
     * @return ViewNode of this view state
     */
    public ViewNode getViewNode() {
        return this.gameSubView;
    }
}
