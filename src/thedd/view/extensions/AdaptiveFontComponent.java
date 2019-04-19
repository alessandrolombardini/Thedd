package thedd.view.extensions;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectExpression;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.text.Font;

/**
 * This interface give method to bind font property of specified objects.
 */
public interface AdaptiveFontComponent {

    /**
     * Sets into a Labeled or TextInputControl component the font resize property
     * binding the size of its container.
     * 
     * @param ratio the ratio applied to the font size.
     * @param node  the node where this ratio is applied.
     */
    default void setFontRatioFromSameObject(final int ratio, final Control node) {
        if (ratio > 0) {
            final ObjectExpression<Font> bi = Bindings.createObjectBinding(
                    () -> Font.font((node.getWidth() + node.getHeight()) / ratio), node.widthProperty(),
                    node.heightProperty());
            if (node instanceof Labeled) {
                final Labeled obj = (Labeled) node;
                obj.fontProperty().bind(bi);
            } else if (node instanceof TextInputControl) {
                final TextInputControl obj = (TextInputControl) node;
                obj.fontProperty().bind(bi);
            }
        }
        node.autosize();
        node.setMinSize(0, 0);
    }

    /**
     * Sets into a Label component the font resize property binding the size of
     * ScrollPane component, that contains it.
     * 
     * @param ratio     the ratio applied to the font size.
     * @param content   the node where this ratio in applied.
     * @param container the node where the content is located into.
     */
    default void setFontRatioFromOtherObject(final int ratio, final Label content, final ScrollPane container) {
        if (ratio > 0) {
            ObjectExpression<Font> bi = Bindings.createObjectBinding(
                    () -> Font.font((container.getWidth() + container.getHeight()) / ratio), container.widthProperty(),
                    container.heightProperty());
            content.fontProperty().bind(bi);
        }
        container.setMinSize(0, 0);
//        content.setMinSize(0, 0);
    }

    /**
     * This method returns the ratio.
     * 
     * @return a int value.
     */
    int getRatio();
}
