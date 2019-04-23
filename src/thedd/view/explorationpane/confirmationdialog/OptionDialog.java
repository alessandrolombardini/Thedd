package thedd.view.explorationpane.confirmationdialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import thedd.view.extensions.AdaptiveFontLabel;

/**
 * A dialog which can have buttons.
 */
public final class OptionDialog extends Pane {

    private static final double DEFAULT_WIDTH = 200;
    private static final double DEFAULT_HEIGHT = 100;
    private static final int TEXT_RATIO = 25;
    private static final double FIVE_PERCENT = 0.05;
    private static final double HALF = 0.5;

    private final Rectangle r = new Rectangle();
    private final AdaptiveFontLabel l = new AdaptiveFontLabel(TEXT_RATIO);
    private final GridPane buttons = new GridPane();
    private final List<Button> buttonList;

    private final DoubleProperty widthProperty = r.widthProperty();
    private final DoubleProperty heightProperty = r.heightProperty();

    /**
     * Create a new Dialog and bind size of his component to his size.
     */
    public OptionDialog() {
        super();
        buttonList = new ArrayList<>();

        r.setWidth(DEFAULT_WIDTH);
        r.setHeight(DEFAULT_HEIGHT);
        r.setFill(Color.web("#040404"));

        l.setTextFill(Color.WHITE);
        l.setAlignment(Pos.CENTER);
        l.prefWidthProperty().bind(getWidthProperty());
        l.prefHeightProperty().bind(getHeightProperty().subtract(this.getHeightProperty().multiply(HALF)));

        buttons.translateXProperty().bind(this.getWidthProperty().subtract(buttons.widthProperty()).multiply(HALF));
        buttons.translateYProperty().bind(this.getHeightProperty().subtract(buttons.heightProperty().add(this.getHeightProperty().multiply(FIVE_PERCENT))));

        this.getChildren().add(r);
        this.getChildren().add(l);
        this.getChildren().add(buttons);
    }

    /**
     * Return the widthProperty of the component.
     * @return
     *          the width property of the component
     */
    public DoubleProperty getWidthProperty() {
        return widthProperty;
    }

    /**
     * Return the heightProperty of the component.
     * @return
     *          the height property of the component
     */
    public DoubleProperty getHeightProperty() {
        return heightProperty;
    }

    /**
     * Set the text of the dialog.
     * @param text
     *          the new text to show
     */
    public void setText(final String text) {
        l.setText(Objects.requireNonNull(text));
        l.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    /**
     * Return the property representing the horizontal gap between buttons.
     * @return
     *  the {@link DoubleProperty} of the horizontal gap
     */
    public DoubleProperty getButtonDistance() {
        return buttons.hgapProperty();
    }

    /**
     * Add a new button to the dialog, to the right of the existent buttons.
     * @param newButton
     *          the new buttons to add
     */
    public void addButton(final Button newButton) {
        buttonList.add(newButton);
        buttons.add(newButton, buttonList.size(), 0);
    }
}
