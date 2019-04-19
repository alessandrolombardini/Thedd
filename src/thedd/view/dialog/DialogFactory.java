package thedd.view.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Factory of {@link Dialog}.
 */
public final class DialogFactory {

    private DialogFactory() {  }

    /**
     * Create an error {@link Dialog}.
     * 
     * @param errorTitle
     *          title text of the Dialog
     * @param errorText
     *          content text of the Dialog
     * @return 
     *          new Dialog
     */
    public static Dialog createErrorDialog(final String errorTitle, final String errorText, final Stage stage) {

        return new Dialog() {

            private final Alert alert = new Alert(AlertType.ERROR);

            @Override
            public void show() {
                alert.setTitle(errorTitle);
                alert.initModality(Modality.WINDOW_MODAL);
                final DialogPane root = alert.getDialogPane();
                root.getStylesheets().add("styles/menu_style.css");
                root.getStyleClass().add("myDialog");
                System.out.println(stage.getWidth());
                System.out.println(stage.getHeight());
                root.setMinWidth(stage.getWidth() / 9 * 2);
                root.setMinHeight(stage.getWidth() / 16 * 2);
                root.setMaxWidth(stage.getWidth() / 9 * 2);
                root.setMaxHeight(stage.getWidth() / 16 * 2);
                alert.setDialogPane(root);
                String version = System.getProperty("java.version");
                String content = String.format("Java: %s.\n" + errorText + ".", version);
                System.out.println(alert.getWidth());
                alert.setHeaderText(content);
                final double x = (stage.getX() + (stage.getWidth()/2)) - (stage.getWidth() / 9 * 4)/2; 
                alert.setX(x);
                alert.setY(stage.getY());
                
////                final Stage dialogStage = new Stage(StageStyle.UTILITY);
////                root.getScene().setRoot(new Group());
////                final Scene scene = new Scene(root);
////                dialogStage.setScene(scene);
////                dialogStage.setResizable(false);
////                dialogStage.showAndWait();
                alert.showAndWait();
            }
        };
    }

}
