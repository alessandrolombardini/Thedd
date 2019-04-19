package thedd.view.imageloader;

/**
 * This enum contains directory choice inside res/images/ package where to pick
 * specified images.
 */
public enum DirectoryPicker {

    /**
     * Directory where all the statistics images are located.
     */
    STATISTICS_PROFILES("statistics");

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
