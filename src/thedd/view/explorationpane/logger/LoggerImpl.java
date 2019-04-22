package thedd.view.explorationpane.logger;

import java.util.Objects;

import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import thedd.view.extensions.AdaptiveFontLabel;

/**
 * {@link GridPane} which acts as {@link Logger}.
 */
public class LoggerImpl extends GridPane implements Logger {

    private static final int FONT_RATIO = 40;
    private static final Duration FADE_DURATION = Duration.millis(500);
    private static final double TEXT_WIDTH_PERC = 90;
    private static final double BTN_WIDTH_PERC = 10;
    private static final double ROW_HEIGHT_PERC = 50;
    private static final double PADDING = 5;

    private final AdaptiveFontLabel text = new AdaptiveFontLabel(FONT_RATIO);

    /**
     * 
     */
    public LoggerImpl() {
        final ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(TEXT_WIDTH_PERC);
        final ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(BTN_WIDTH_PERC);
        this.getColumnConstraints().addAll(column1, column2);

        final RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(ROW_HEIGHT_PERC);
        final RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(ROW_HEIGHT_PERC);
        this.getRowConstraints().addAll(row1, row2);

        this.setPadding(new Insets(PADDING));
        this.setBackground(new Background(new BackgroundFill(Color.web("#040404"), null, null)));

        text.setTextFill(Color.WHITE);
        //text.setWrapText(true);
        text.prefWidthProperty().bind(this.getWidthProperty().multiply(TEXT_WIDTH_PERC / 100));
        text.prefHeightProperty().bind(this.getHeightProperty());
        text.setAlignment(Pos.CENTER);
        this.add(text, 0, 0, 2, 2);

        //loggerManager = Optional.empty();

        /*final ImageLoader imgl = new ImageLoaderImpl();
        final Button close = new Button();
        close.setPickOnBounds(true);
        close.setTranslateY(PADDING);
        close.prefWidthProperty().bind(this.getWidthProperty().multiply(BTN_WIDTH_PERC / 100));
        close.prefHeightProperty().bind(this.getHeightProperty().multiply(ROW_HEIGHT_PERC / 100));
        close.setBackground(new Background(
                              new BackgroundImage(
                                imgl.loadSingleImage(DirectoryPicker.ICON, "closelogger"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0,  1.0,  true, true, true, false))));
        close.setOnAction(e -> loggerManager.ifPresent(lm -> lm.cancel()));
        this.add(close, 1, 0, 1, 1);*/
    }

    @Override
    public final void setVisibility(final boolean isVisible) {
        final FadeTransition fade = new FadeTransition(FADE_DURATION, this);
        fade.setFromValue(isVisible ? 0.0 : 1.0);
        fade.setToValue(isVisible ? 1.0 : 0.0);
        if (!isVisible) {
            fade.setOnFinished(e -> setVisible(isVisible));
        } else {
            setVisible(isVisible);
        }
        fade.playFromStart();
    }

    @Override
    public final void setText(final String text) {
        this.text.setText(Objects.requireNonNull(text));
    }

    @Override
    public final void setLoggerManager(final LoggerManager logMan) {
        //loggerManager = Optional.of(Objects.requireNonNull(logMan));
    }

    @Override
    public final DoubleProperty getWidthProperty() {
        return maxWidthProperty();
    }

    @Override
    public final DoubleProperty getHeightProperty() {
        return maxHeightProperty();
    }

}
