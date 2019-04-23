package thedd.view.actionselector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import thedd.utils.observer.Observable;
import thedd.utils.observer.Observer;
import thedd.view.extensions.AdaptiveFontButton;
import thedd.view.extensions.AdaptiveFontScrollableText;

/**
 * The pane that holds the description of the current action and the navigation bar. 
 */
public class DescriptionPane extends Pane implements Observable<Command> {

    private static final double UPPER_AREA_HEIGHT_PERC = 0.8;
    private static final double PADDING_PERC = 0.02;
    private static final int N_COLS = 6;
    private final List<Observer<Command>> listeners = new ArrayList<>();
    private final AdaptiveFontScrollableText scrollableText = new AdaptiveFontScrollableText("[Description is missing]");
    private final DoubleProperty upperAreaHeight = new SimpleDoubleProperty();
    private final DoubleProperty lowerAreaHeight = new SimpleDoubleProperty();
    private final DoubleProperty buttonSizeByWidth = new SimpleDoubleProperty();
    private final DoubleProperty padding = new SimpleDoubleProperty();
    private final List<Button> buttons = Arrays.asList(new MyButton(1, "<", Command.PREVIOUS, true),
                                                       new MyButton(2, "Select", Command.SELECT, false),
                                                       new MyButton(3, ">", Command.NEXT, true),
                                                       new MyButton(5, "Back", Command.RETURN, false));
    private Command currentCommand;


    /**
     * Public constructor, sets up the structure of the pane.
     */
    public DescriptionPane() {
        super();
        upperAreaHeight.bind(this.heightProperty().multiply(UPPER_AREA_HEIGHT_PERC));
        lowerAreaHeight.bind(this.heightProperty().subtract(upperAreaHeight));
        padding.bind(this.widthProperty().multiply(PADDING_PERC));
        buttonSizeByWidth.bind(this.widthProperty().subtract(padding.multiply(2)).divide(N_COLS));
        this.getStyleClass().add("background");
        this.getChildren().add(scrollableText);
        scrollableText.prefWidthProperty().bind(this.widthProperty().subtract(padding.multiply(2)));
        scrollableText.layoutXProperty().bind(padding);
        scrollableText.prefHeightProperty().bind(upperAreaHeight.subtract(padding));
        scrollableText.layoutYProperty().bind(padding);
        buttons.forEach(button -> {
            button.getStyleClass().add("action-selector-button");
        });
        getChildren().addAll(buttons);
    }

    /**
     * Displays information about the newly selected action.
     * @param action the action to display
     * @param selectedIndex the index of the action
     * @param numItems the total number of available actions in the current category
     */
    public void showAction(final VisualAction action, final int selectedIndex, final int numItems) {
        final boolean disableForward = selectedIndex >= numItems - 1;
        final boolean disableBackwards = selectedIndex <= 0;

        scrollableText.setText(action.getDescription());
        buttons.get(0).setDisable(disableBackwards);
        buttons.get(1).setDisable(false);
        buttons.get(2).setDisable(disableForward);
        buttons.get(3).setDisable(false);
    }

    /**
     * Displays information about the newly selected category.
     * @param category the category to display
     * @param selectedIndex the index of the category
     * @param numItems the total number of available categories
     */
    public void showCategory(final VisualCategory category, final int selectedIndex, final int numItems) {
        final boolean disableForward = selectedIndex >= numItems - 1;
        final boolean disableBackwards = selectedIndex <= 0;

        scrollableText.setText(category.getDescription());
        buttons.get(0).setDisable(disableBackwards);
        buttons.get(1).setDisable(false);
        buttons.get(2).setDisable(disableForward);
        buttons.get(3).setDisable(true);
    }

    /**
     * Enables or disables the "previous item", "next item" and "select item"
     * buttons.
     * @param disabled true to disable the components, false otherwise
     */
    public void setSelectionAndMovement(final boolean disabled) {
        buttons.get(0).setDisable(disabled);
        buttons.get(1).setDisable(disabled);
        buttons.get(2).setDisable(disabled);
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
        listeners.forEach(l -> l.trigger(Optional.of(currentCommand)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Observer<Command>> getRegisteredObservers() {
        return Collections.unmodifiableList(listeners);
    }

    /**
     *  The custom button used in this pane. 
     *  When clicked, it signals to its listeners with a provided command.
     *  In can be either set to fire the event one time per mouse click or
     *  as long as the mouse button is held.
     */
    private class MyButton extends AdaptiveFontButton {

        private static final double PRESS_INTERVAL_MS = 250;
        private static final int FONT_RATIO = 8;

        /**
         * 
         * @param column the column of the gridPane in which this button will appear
         * @param text the text to display in the button
         * @param onClickCommand the {@link Command} to send to the observers
         * @param hold whether the signal to the observers should be sent as long as the button is pressed
         */
        MyButton(final int column, final String text, final Command onClickCommand, final boolean hold) {
            super(FONT_RATIO);
            this.setMinSize(0, 0);
            this.setFocusTraversable(false);
            this.setText(text);
            this.prefWidthProperty().bind(buttonSizeByWidth);
            this.prefHeightProperty().bind(lowerAreaHeight.subtract(padding.multiply(2)));
            this.layoutXProperty().bind(padding.add(buttonSizeByWidth.multiply(column)));
            this.layoutYProperty().bind(upperAreaHeight.add(padding));

            if (!hold) {
                this.setOnMouseClicked(e -> listeners.forEach(l -> {
                    currentCommand = onClickCommand;
                    emit();
                }));
            } else {
                //Code taken and modified from https://stackoverflow.com/questions/33157671/javafx-how-to-repeat-action-as-long-as-button-is-pressed
                final AnimationTimer timer = new AnimationTimer() {
                    private long lastUpdate;

                    @Override
                    public void handle(final long time) {
                        final long timeStampMs = time / 1000000;
                        if (Math.abs(timeStampMs - lastUpdate) >= PRESS_INTERVAL_MS) {
                            currentCommand = onClickCommand;
                            emit();
                            this.lastUpdate = timeStampMs;
                        }
                    }
                };

                this.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                            timer.start();
                        } else {
                            timer.stop();
                        }
                    }
                });
            }
        }
    }


}
