package thedd.view.explorationpane;

/**
 * This component can show a modal dialog which blocks every mouse click outside the dialog itself.
 */
public interface ModalDialogViewer {

    /**
     * Show a modal dialog with a text inside.
     * @param newText
     *          the test to show in the dialog
     */
    void showDialog(String newText);

    /**
     * Hide the previously shown modal dialog.
     */
    void hideDialog();
}
