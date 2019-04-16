package thedd.view.extensions;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectExpression;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.text.Font;

/**
 * This interface give method to bind font property of specified objects.
 */
public interface AdaptiveFontComponent {

    /**
     * Initialize a Labeled component that has the property of auto-resizable font.
     * 
     * @param ratio the ratio applied to the font size.
     * @param node  the node where this ratio in applied.
     */
    default void setFontRatio(final int ratio, final Labeled node) {
        if (ratio > 0) {
            ObjectExpression<Font> bi = Bindings.createObjectBinding(
                    () -> Font.font((node.getWidth() + node.getHeight()) / ratio), node.widthProperty(),
                    node.heightProperty());
            node.fontProperty().bind(bi);
        }
    }

    /**
     * Initialize a TextInputControl component that has the property of
     * auto-resizable font.
     * 
     * @param ratio the ratio applied to the font size.
     * @param node  the node where this ratio in applied.
     */
    default void setFontRatio(final int ratio, final TextInputControl node) {
        if (ratio > 0) {
            ObjectExpression<Font> bi = Bindings.createObjectBinding(
                    () -> Font.font((node.getWidth() + node.getHeight()) / ratio), node.widthProperty(),
                    node.heightProperty());
            node.fontProperty().bind(bi);
        }
    }

    /**
     * This method returns the ratio.
     * 
     * @return a int value.
     */
    int getRatio();
}
