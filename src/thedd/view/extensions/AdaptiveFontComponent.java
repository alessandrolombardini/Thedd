package thedd.view.extensions;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectExpression;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
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
    default void setFontRatio(final int ratio, final Control node) {
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
     * This method returns the ratio.
     * 
     * @return a int value.
     */
    int getRatio();
}
