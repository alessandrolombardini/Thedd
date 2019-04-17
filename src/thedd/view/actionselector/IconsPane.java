package thedd.view.actionselector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import thedd.utils.observer.Observable;
import thedd.utils.observer.Observer;

/**
 * The pane that holds the icons for the actions.
 */
public class IconsPane extends Pane implements Observable<Command> {

    private static final double TRANSLATE_DURATION_MS = 200;
    private static final double SELECTED_ITEM_SIZE_PERC = .8d;
    private static final double DEFAULT_ITEM_SIZE_PERC = .5d;
    private final DoubleProperty selectedItemSizeByWidth = new SimpleDoubleProperty();
    private final DoubleProperty selectedItemSizeByHeight = new SimpleDoubleProperty();
    private final DoubleProperty defaultItemSizeByWidth = new SimpleDoubleProperty();
    private final DoubleProperty defaultItemSizeByHeight = new SimpleDoubleProperty();
    private final List<TranslateTransition> currentTransitions = new ArrayList<>();
    private final List<Button> buttons = new ArrayList<>();
    private final List<Observer<Command>> listeners = new ArrayList<>();
    private DoubleProperty oldSelectedSize = getSelectedSize();
    private int selected;

    /**
     * Public constructor.<br>
     * Initializes the structure of the pane and sets the bindings.
     */
    public IconsPane() {
        selectedItemSizeByWidth.bind(this.widthProperty().multiply(SELECTED_ITEM_SIZE_PERC));
        defaultItemSizeByWidth.bind(this.widthProperty().multiply(DEFAULT_ITEM_SIZE_PERC));
        selectedItemSizeByHeight.bind(this.heightProperty().multiply(SELECTED_ITEM_SIZE_PERC));
        defaultItemSizeByHeight.bind(this.heightProperty().multiply(DEFAULT_ITEM_SIZE_PERC));

        setBackground(new Background(new BackgroundFill(Paint.valueOf("red"), null, null)));

        this.setOnScroll(e -> {
            if (e.getDeltaY() < 0) {
                scrollDown();
            } else {
                scrollUp();
            }
        });

        this.widthProperty().addListener(l -> {
            if (!oldSelectedSize.equals(getSelectedSize())) {
                oldSelectedSize = getSelectedSize();
                buttons.forEach(b -> setBindings(b));
            }
        });
        this.heightProperty().addListener(l -> {
            if (!oldSelectedSize.equals(getSelectedSize())) {
                oldSelectedSize = getSelectedSize();
                buttons.forEach(b -> setBindings(b));
            }
        });

    }

    /**
     * Gets the current selected index.
     * @return the index of the current highlighted item
     */
    public int getSelectedIndex() {
        return selected;
    }

    /**
     * Gets the number of currently displayed items.
     * @return the number of displayer items
     */
    public int getItemsCount() {
        return buttons.size();
    }

    /**
     * Passes the Images that will appear as the background of the buttons.
     * @param items the list of images to set as backgrounds
     */
    public void passItems(final List<Image> items) {
        selected = 0;
        getChildren().removeAll(buttons);
        buttons.clear();
        items.forEach(i -> {
            final Button b = new Button();
            final BackgroundSize size = new BackgroundSize(1.0, 1.0, true, true, false, false);
            final BackgroundImage image = new BackgroundImage(i, 
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              size);
            b.setBackground(new Background(image));
            b.setMinSize(0, 0);
            b.setOnAction(l -> {
                selected = buttons.indexOf(b);
                emit();
                translateItems();
            });
            buttons.add(b);
            getChildren().add(b);
            setBindings(b);
        });
    }

    /**
     * Highlights the previous item (if there's any).
     */
    public void scrollUp() {
        if (selected > 0) {
            selected--;
            translateItems();
            emit();
        }
    }

    /**
     * Highlights the next item (if there's any).
     */
    public void scrollDown() {
        if (selected < buttons.size() - 1) {
            selected++;
            translateItems();
            emit();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindObserver(final Observer<Command> newObserver) {
        listeners.add(newObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(final Observer<Command> observer) {
        listeners.remove(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void emit() {
        listeners.forEach(l -> l.trigger(Optional.of(Command.UPDATE)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Observer<Command>> getRegisteredObservers() {
        return Collections.unmodifiableList(listeners);
    }

    private void translateItems() {
        currentTransitions.forEach(Transition::stop);
        currentTransitions.clear();

        buttons.forEach(b -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(TRANSLATE_DURATION_MS), b);
            int index = buttons.indexOf(b);
            int direction = index == selected ? 0 : index > selected ? 1 : -1;
            int offset = index == selected ? 0 :  Math.abs(index - selected);
            b.layoutXProperty().unbind();
            b.translateYProperty().unbind();

            double endPoint = getNewDefaultY(b, direction, offset, getSelectedSize()).get();
            tt.setToY(endPoint);
            currentTransitions.add(tt);
            tt.play();
            tt.setOnFinished(l -> setBindings(b));
        });
    }

    private void setBindings(final Button button) {
        final int index = buttons.indexOf(button);
        final int offset = Math.abs(index - selected);
        final int direction = index == selected ? 0 : index > selected ? 1 : -1;
        button.translateXProperty().bind(getCenterX(button));

        if (buttons.indexOf(button) == selected) {
            button.prefWidthProperty().bind(getSelectedSize());
            button.prefHeightProperty().bind(getSelectedSize());
            button.translateYProperty().bind(getNewSelectedY(button));
        } else {
            button.prefWidthProperty().bind(getDefaultSize());
            button.prefHeightProperty().bind(getDefaultSize()); 
            button.translateYProperty().bind(getNewDefaultY(button, direction, offset, getSelectedSize()));
        }
    }

    private DoubleProperty getSelectedSize() {
        return this.getWidth() > this.getHeight() 
                ? selectedItemSizeByHeight : selectedItemSizeByWidth;
    }

    private DoubleProperty getDefaultSize() {
        return this.getWidth() > this.getHeight() 
                ? defaultItemSizeByHeight : defaultItemSizeByWidth;
    }

    private DoubleBinding getNewSelectedY(final Button button) {
        return this.heightProperty()
                    .divide(2)
                    .subtract(button.prefHeightProperty()
                    .divide(2));
    }

    private DoubleBinding getNewDefaultY(final Button button, final int direction, final double offset, final DoubleProperty selectedSize) {
        return this.heightProperty()
                   .divide(2)
                   .subtract(button.heightProperty().divide(2))
                   .add(selectedSize.multiply(direction).multiply(offset));
    }

    private DoubleBinding getCenterX(final Button button) {
        return this.widthProperty()
                .divide(2)
                .subtract(button.widthProperty().divide(2));
    }

}
