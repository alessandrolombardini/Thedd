package thedd.view.controller;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import thedd.view.ApplicationState;
import thedd.view.SubViewControllerImpl;
/**
 * 
 *
 */
public class OpenMenuController extends SubViewControllerImpl {
   /** 
    * 
    * @param event
    */
    @FXML
    public void handleOnKeyPressed(final KeyEvent event) {
        System.out.println("ciao");
        if (event.getCode() == KeyCode.ENTER) {
            this.getView().setView(ApplicationState.MENU);
        }
    }
}
