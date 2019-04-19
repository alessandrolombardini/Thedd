package thedd.view.extensions;

import javafx.beans.NamedArg;
import javafx.scene.control.Button;

/**
 * This class extends {@link javafx.scene.control.Label}, introducing the
 * possibility to resize automatically its font.
 */
public class AdaptiveFontButton extends Button implements AdaptiveFontComponent {

    private final int ratio;

    /**
     * Initialize a Label that has the property of auto-resizable font.
     * 
     * @param ratio the ratio of the resize.
     */
    public AdaptiveFontButton(@NamedArg("ratio") final int ratio) {
        super();
        this.ratio = ratio;
        this.setFontRatioFromSameObject(ratio, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRatio() {
        return this.ratio;
    }
}
