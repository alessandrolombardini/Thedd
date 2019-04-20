package thedd.view.explorationpane;

import java.util.Objects;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectExpression;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import thedd.view.explorationpane.confirmationdialog.OptionDialog;

/**
 * A {@link StackPane} which can run any function based on the response of a modal dialog it can create and show.
 *
 */
public class TopStackPane extends StackPane implements DialogResponseManager, ModalDialogViewer {

    private final OptionDialog bop = new OptionDialog();
    private Runnable onDialogAccept = (() -> {
        return;
    });
    private Runnable onDialogDecline = (() -> {
        return;
    });

    private final EventHandler<MouseEvent> filter = new EventHandler<MouseEvent>() {
        @Override
        public void handle(final MouseEvent event) {
            if (event.getX() < bop.translateXProperty().doubleValue()
                    || event.getX() > bop.translateXProperty().add(bop.getWidthProperty()).doubleValue()
                    || event.getY() < bop.translateYProperty().doubleValue()
                    || event.getY() > bop.translateYProperty().add(bop.getHeightProperty()).doubleValue()) {
                     event.consume();
                 }
        }
    };

    /**
     * TopStackPane contructor.
     * It also creates the dialog.
     */
    public TopStackPane() {
        final double half = 0.5;
        this.setAlignment(Pos.CENTER);

        final int twentyFive = 25;
        final int fifteen = 15;
        final int five = 5;
        final int two = 2;

        final ObjectExpression<Font> bi = Bindings.createObjectBinding(
              () -> Font.font((bop.getWidthProperty().add(bop.getHeightProperty()).doubleValue()) / (twentyFive * two)), bop.getWidthProperty(),
              bop.getHeightProperty());

        final Button accept = new Button("Accept");
        accept.fontProperty().bind(bi);
        accept.prefWidthProperty().bind(bop.getWidthProperty().multiply(getPercentageAsDecimal(twentyFive)));
        accept.prefHeightProperty().bind(bop.getHeightProperty().multiply(getPercentageAsDecimal(fifteen)));
        accept.setOnAction(e -> onDialogAccept.run());

        final Button cancel = new Button("Cancel");
        cancel.fontProperty().bind(bi);
        cancel.prefWidthProperty().bind(bop.getWidthProperty().multiply(getPercentageAsDecimal(twentyFive)));
        cancel.prefHeightProperty().bind(bop.getHeightProperty().multiply(getPercentageAsDecimal(fifteen)));
        cancel.setOnAction(e -> onDialogDecline.run());

        bop.getButtonDistance().bind(bop.getWidthProperty().multiply(getPercentageAsDecimal(five)));
        bop.addButton(accept);
        bop.addButton(cancel);

        bop.translateXProperty().bind(this.widthProperty().multiply(half).subtract(bop.getWidthProperty().multiply(half)));
        bop.translateYProperty().bind(this.heightProperty().multiply(half).subtract(bop.getHeightProperty().multiply(half)));
        bop.getWidthProperty().bind(this.widthProperty().multiply(half));
        bop.getHeightProperty().bind(this.heightProperty().multiply(half));
    }

    @Override
    public final void showDialog(final String newText) {
        bop.setText(newText);
        this.getChildren().add(bop);
        this.addEventFilter(MouseEvent.ANY, filter);
    }

    @Override
    public final void hideDialog() {
        this.getChildren().remove(bop);
        this.removeEventFilter(MouseEvent.ANY, filter);
    }

    @Override
    public final void setDialogAccepted(final Runnable onAccept) {
        onDialogAccept = Objects.requireNonNull(onAccept);
    }

    @Override
    public final void setDialogDeclined(final Runnable onDecline) {
        onDialogDecline = Objects.requireNonNull(onDecline);
    }

    private double getPercentageAsDecimal(final int percentage) {
        return percentage / 100.0;
    }

}
