package thedd.view.extensions;

import javafx.beans.NamedArg;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;

/**
 * This class extends {@link ScrollPane} adding a {@link Text} into it.
 */
public class ScrollableText extends ScrollPane {
    private static final Label TEXT = new Label();
    private static final String CUSTOM_CSS = "-fx-background-color:#111; -fx-text-fill: #fff";
    private static final int SCROLLBAR_WIDTH = 10;
    /**
     * ScrollableText constructor.
     * 
     * @param value is the content of the Text.
     */
    public ScrollableText(@NamedArg("text") final String value) {
        super(TEXT);
        TEXT.setFocusTraversable(false);
        TEXT.setWrapText(true);
        this.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    event.consume();
                }
            }
        });
        this.setMinSize(0, 0);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setFocusTraversable(false);
        TEXT.minWidthProperty().bind(this.widthProperty().subtract(SCROLLBAR_WIDTH));
        TEXT.minHeightProperty().bind(this.heightProperty());
//        widthProperty().addListener(l -> System.out.println(widthProperty().get()));
//        TEXT.wrappingWidthProperty().bind(this.widthProperty());
//        TEXT.prefWidthProperty().bind(this.widthProperty());
//        TEXT.prefHeightProperty().bind(this.heightProperty());
        if (value == null) {
            TEXT.setText("");
        } else {
            TEXT.setText(value);
        }
        this.setFitToWidth(true);
        TEXT.setStyle(CUSTOM_CSS);
    }

    /**
     * This method add the specified String into the Text.
     * 
     * @param value the String.
     */
    public void setText(final String value) {
        TEXT.setText(value);
    }
}
