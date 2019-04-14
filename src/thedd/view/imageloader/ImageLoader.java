package thedd.view.imageloader;

import javafx.scene.image.Image;

/**
 * Contains path for the specified images and load them into a {@link Image}.
 */
public enum ImageLoader {

    /**
     * Profile image of Player character.
     */
    PLAYERCHARACTER_PROFILE("/profiles/player.png"),

    /**
     * Profile picture of Headless character.
     */
    HEADLESSNPC_PROFILE("/profiles/headless.png"),

    /**
     * Profile picture of Dark Destructor character.
     */
    DARKDESTRUCTORNPC_PROFILE("/profiles/dark_destructor.png"),

    /**
     * Profile picture of Goblin character.
     */
    GOBLINNPC_PROFILE("/profiles/dark_destructors.png");

    private static final String LOCATION = "images/";
    private final String path;

    ImageLoader(final String path) {
        this.path = path;
    }

    /**
     * Return the path of the image as a String.
     * 
     * @return a {@link String}
     */
    public String getPath() {
        return LOCATION + path;
    }

    /**
     * Return the loaded image of the specified path.
     * 
     * @return a {@link Image}
     */
    public Image getImage() {
        return new Image(LOCATION + path);
    }
}
