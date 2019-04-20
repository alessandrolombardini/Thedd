package thedd.view.imageloader;

/**
 * This enum contains directory choice inside res/images/ package where to pick
 * specified images.
 */
public enum DirectoryPicker {

    /**
     * Directory where all the statistics images are located.
     */
    CHARACTERS_CLOSEUP("characters/closeup"),

    /**
     * Directory where all the statistics categories images are located.
     */
    STATISTICS_CATEGORIES("statistics"),

    /**
     * Directory where all the images relative to allies are located.
     */
    ALLY_BATTLE("characters/battle/ally"),

    /**
     * Directory where images relative to enemies in battle are located.
     */
    ENEMY_BATTLE("characters/battle/enemy"),

    /**
     * Directory where images relative to ActionPerformers are located.
     */
    INTERACTABLE_ACTION_PERFORMER("iap"),

    /**
     * Directory where images relative to icons are located.
     */
    ICON("icons"),

    /**
     * Directory where images relative to room and floor changer are located.
     */
    ROOM_CHANGER("roomchanger"),

    /**
     * Directory where images relative to the background are located.
     */
    BACKGROUND("background"),

    /**
     * Directory where all titles images are located.
     */
    TITLES("titles"),

    /**
     * Directory where images relate to actions are located.
     */
    ACTIONS("actions"),

    /**
     * Directory where images relate to action categories are located.
     */
    ACTION_CATEGORIES("actions/categories");

    private static final String BASIC_DIR = "/images/";
    private final String directory;

    DirectoryPicker(final String directory) {
        this.directory = directory;
    }

    /**
     * This method returns a string representation of the selected directory.
     * 
     * @return a String
     */
    public String getDirectory() {
        return BASIC_DIR + this.directory + "/";
    }

}
