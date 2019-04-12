package thedd.view.controller;

import javafx.beans.NamedArg;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectExpression;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * This class extends {@link javafx.scene.control.Label}, introducing the
 * possibility to resize automatically his font.
 */
public class AdaptiveLabel extends Label {

    private final int ratio;

    /**
     * Initialize a Label that has the property of auto-resizable font.
     * 
     * @param ratio the ratio of the resize.
     */
    public AdaptiveLabel(@NamedArg("ratio") final int ratio) {
        super();
        this.ratio = ratio;
        if (ratio > 0) {
            ObjectExpression<Font> bi = Bindings.createObjectBinding(
                    () -> Font.font((this.getWidth() + this.getHeight()) / ratio), this.widthProperty(),
                    this.heightProperty());
            this.fontProperty().bind(bi);
        }
    }

    /**
     * This method returns the ratio.
     * 
     * @return a int value.
     */
    public int getRatio() {
        return this.ratio;
    }
}
