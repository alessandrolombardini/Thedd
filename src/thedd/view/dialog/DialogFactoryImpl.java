package thedd.view.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;

/**
 * Concrete implementation of {@link DialogFactory}.
 */
public class DialogFactoryImpl implements DialogFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Dialog createErrorDialog(final String errorTitle, final String errorText) {

        return new Dialog() {

            private final Alert alert = new Alert(AlertType.ERROR);

            @Override
            public void show() {
                alert.setTitle(errorTitle);
                alert.setHeaderText(errorText);
                alert.initModality(Modality.WINDOW_MODAL);
                alert.showAndWait();
            }
        };
    }

}
