package thedd.view.extensions;

import javafx.beans.NamedArg;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectExpression;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

/**
 * This class extends {@link ScrollPane} adding a Text into it.
 */
public final class AdaptiveFontScrollableText extends ScrollPane implements AdaptiveFontComponent {
    private final Label text = new Label();
    private static final String STYLESHEET = "styles/scrollable_text_style.css";
    private static final int PROPORTIONAL_DIVIDER = 30;

    /**
     * AdaptiveFontScrollableText's constructor.
     * 
     * @param value is the content of the node.
     */
    public AdaptiveFontScrollableText(@NamedArg("text") final String value) {
        super();
        this.setContent(text);
        text.setFocusTraversable(false);
        text.setWrapText(true);
        this.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    event.consume();
                }
            }
        });
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setFocusTraversable(false);
        if (value == null) {
            text.setText("");
        } else {
            text.setText(value);
        }
        this.setFitToWidth(true);
        this.getStylesheets().add(ClassLoader.getSystemClassLoader().getResource(STYLESHEET).toExternalForm());
        if (PROPORTIONAL_DIVIDER > 0) {
            final ObjectExpression<Font> bi = Bindings.createObjectBinding(
                    () -> Font.font((this.getWidth() + this.getHeight()) / PROPORTIONAL_DIVIDER), this.widthProperty(),
                    this.heightProperty());
            text.fontProperty().bind(bi);
        }
        this.setMinSize(0, 0);
    }

    /**
     * This method add the specified String into the Text.
     * 
     * @param value the String.
     */
    public void setText(final String value) {
        text.setText(value);
    }

    @Override
    public int getRatio() {
        return PROPORTIONAL_DIVIDER;
    }
}
