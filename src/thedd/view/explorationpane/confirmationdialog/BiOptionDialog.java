package thedd.view.explorationpane.confirmationdialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import thedd.utils.observer.Observable;
import thedd.utils.observer.Observer;
import thedd.view.extensions.AdaptiveFontLabel;

/**
 * 
 * 
 */
public final class BiOptionDialog extends Pane implements Observable<DialogResponse> {

    private static final double DEFAULT_WIDTH = 200;
    private static final double DEFAULT_HEIGHT = 100;
    private static final int TEXT_RATIO = 25;
    private static final double FIVE_PERCENT = 0.05;
    private static final double FIFTEEN_PERCENT = 0.15;
    private static final double TWENTYFIVE_PERCENT = 0.25;
    private static final double HALF = 0.5;

    private final List<Observer<DialogResponse>> observers;
    private Optional<DialogResponse> response;
    private final Rectangle r = new Rectangle();
    private final AdaptiveFontLabel l = new AdaptiveFontLabel(TEXT_RATIO);
    private final GridPane buttons = new GridPane();

    private final DoubleProperty widthProperty = r.widthProperty();
    private final DoubleProperty heightProperty = r.heightProperty();

    /**
     * 
     * @param text
     */
    public BiOptionDialog() {
        observers = new ArrayList<>();
        response = Optional.empty();

        r.setWidth(DEFAULT_WIDTH);
        r.setHeight(DEFAULT_HEIGHT);
        r.setFill(Color.web("#040404"));

        l.setTextFill(Color.WHITE);
        l.setAlignment(Pos.CENTER);
        l.prefWidthProperty().bind(getWidthProperty());
        l.prefHeightProperty().bind(getHeightProperty().subtract(this.getHeightProperty().multiply(HALF)));

        final ObjectExpression<Font> bi = Bindings.createObjectBinding(
                () -> Font.font((this.getWidthProperty().add(this.getHeightProperty()).doubleValue()) / (TEXT_RATIO * 2)), this.getWidthProperty(),
                this.getHeightProperty());

        final Button accept = new Button("Accept");
        accept.fontProperty().bind(bi);
        accept.prefWidthProperty().bind(this.getWidthProperty().multiply(TWENTYFIVE_PERCENT));
        accept.prefHeightProperty().bind(this.getHeightProperty().multiply(FIFTEEN_PERCENT));

        accept.setOnAction(e -> {
            response = Optional.of(DialogResponse.ACCEPTED); 
            this.emit();
        });
        final Button cancel = new Button("Cancel");
        cancel.fontProperty().bind(bi);
        cancel.prefWidthProperty().bind(this.getWidthProperty().multiply(TWENTYFIVE_PERCENT));
        cancel.prefHeightProperty().bind(this.getHeightProperty().multiply(FIFTEEN_PERCENT));
        cancel.setOnAction(e -> {
            response = Optional.of(DialogResponse.DECLINED); 
            this.emit();
        });

        buttons.hgapProperty().bind(this.getWidthProperty().multiply(FIVE_PERCENT));
        buttons.add(accept, 0, 0);
        buttons.add(cancel, 2, 0);
        buttons.translateXProperty().bind(this.getWidthProperty().subtract(buttons.widthProperty()).multiply(HALF));
        buttons.translateYProperty().bind(this.getHeightProperty().subtract(buttons.heightProperty().add(this.getHeightProperty().multiply(FIVE_PERCENT))));

        this.getChildren().add(r);
        this.getChildren().add(l);
        this.getChildren().add(buttons);
    }

    @Override
    public void bindObserver(final Observer<DialogResponse> newObserver) {
        observers.add(Objects.requireNonNull(newObserver));
    }

    @Override
    public void removeObserver(final Observer<DialogResponse> observer) {
        observers.remove(Objects.requireNonNull(observer));
    }

    @Override
    public void emit() {
        observers.forEach(o -> o.trigger(response));
    }

    @Override
    public Collection<Observer<DialogResponse>> getRegisteredObservers() {
        return Collections.unmodifiableList(observers);
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
}
