package thedd;

import javafx.application.Application;
import thedd.view.ViewImpl;

/**
 * The class containing the main method to start the application.
 */
public final class Main {

    private Main() { }

    /**
     * The entry point of application.
     * 
     * @param args passed
     */
    public static void main(final String[] args) {
        Application.launch(ViewImpl.class, args);
    }

}
