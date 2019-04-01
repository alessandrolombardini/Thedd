package thedd.view.dialog;

/**
 * Inteface describing a factory of {@link Dialog}.
 */
public interface DialogFactory {

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
    Dialog createErrorDialog(String errorTitle, String errorText);
}
