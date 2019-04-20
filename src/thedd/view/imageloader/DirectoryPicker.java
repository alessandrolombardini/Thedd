package thedd.view.imageloader;

/**
 * This enum contains directory choice inside res/images/ package where to pick
 * specified images.
 */
public enum DirectoryPicker {

    /**
     * Directory where all the statistics images are located.
     */
    STATISTICS_PROFILES("statistics"),

    /**
     * Directory where all the images relative to allies are located.
     */
    ALLY_BATTLE("ally/battle"),

    /**
     * Directory where all the images relative to ally portraits are located.
     */
    ALLY_CLOSEUP("ally/closeup"),

    /**
     * Directory where images relative to enemies in battle are located.
     */
    ENEMY_BATTLE("enemy/battle"),

    /**
     * Directory where images relative to enemy portraits are located.
     */
    ENEMY_CLOSEUP("enemy/closeup"),

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
