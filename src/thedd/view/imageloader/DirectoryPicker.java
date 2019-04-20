package thedd.view.imageloader;

/**
 * This enum contains directory choice inside res/images/ package where to pick
 * specified images.
 */
public enum DirectoryPicker {

    /**
     * Directory where all the statistics images are located.
     */
    STATISTICS_PROFILES("statistics/characters"),

    /**
     * Directory where all the statistics categories images are located.
     */
    STATISTICS_CATEGORIES("statistics/categories"),

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
