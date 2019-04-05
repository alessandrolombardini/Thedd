package thedd.view.scenewrapper;

import java.util.Objects;

/**
 * Enum representing the scene of application.
 */
public enum GameSubView {

    /**
     * Menu scene.
     */
    MENU("mainMenu_form.fxml"),

    /**
     * New game scene.
     */
    NEW_GAME("playMenu_form.fxml"),

    /**
     * Game over scene.
     */
    GAME_OVER("gameOver_form.fxml");

    /**
     * Use of the char '/' becouse it's used by istructions that required that type of char, doesn't 
     * matter if the application is used on linux, windows or os. 
     */
    private static final String FXML_PATH = "scenes/";

    private final String nameOfFxml;

    /**
     * Enum constructor.
     * 
     * @param nameOfFxml
     *          of fxml scene
     */
    GameSubView(final String nameOfFxml) {
        Objects.requireNonNull(nameOfFxml);
        this.nameOfFxml = nameOfFxml;
    }

    /**
     * Getter of fxml nameOfFxml with unix separator.
     * 
     * @return
     *          fxml nameOfFxml
     */
    public String getFXMLPath() {
        return FXML_PATH + this.nameOfFxml;
    }
}
