package thedd.view.extensions;

import javafx.beans.NamedArg;
import javafx.scene.control.Label;

/**
 * This class extends {@link javafx.scene.control.Label}, introducing the
 * possibility to resize automatically its font.
 */
public class AdaptiveFontLabel extends Label implements AdaptiveFontComponent {

    private final int ratio;

    /**
     * Initialize a Label that has the property of auto-resizable font.
     * 
     * @param ratio the ratio of the resize.
     */
    public AdaptiveFontLabel(@NamedArg("ratio") final int ratio) {
        super();
        this.ratio = ratio;
        this.setFontRatio(ratio, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRatio() {
        return this.ratio;
    }
}
