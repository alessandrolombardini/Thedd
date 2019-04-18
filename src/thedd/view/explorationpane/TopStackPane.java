package thedd.view.explorationpane;

import java.util.Objects;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import thedd.utils.observer.Observer;
import thedd.view.explorationpane.confirmationdialog.BiOptionDialog;
import thedd.view.explorationpane.confirmationdialog.DialogResponse;

/**
 * A {@link StackPane} which observes a {@link DialogResponse} 
 * and can run any function based on the response of a modal dialog it can create and show.
 *
 */
public class TopStackPane extends StackPane implements Observer<DialogResponse>, DialogResponseManager, ModalDialogViewer {

    private final BiOptionDialog bop = new BiOptionDialog();
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
        bop.translateXProperty().bind(this.widthProperty().multiply(half).subtract(bop.getWidthProperty().multiply(half)));
        bop.translateYProperty().bind(this.heightProperty().multiply(half).subtract(bop.getHeightProperty().multiply(half)));
        bop.getWidthProperty().bind(this.widthProperty().multiply(half));
        bop.getHeightProperty().bind(this.heightProperty().multiply(half));
        bop.bindObserver(this);
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
    public final void trigger(final Optional<DialogResponse> message) {
        if (!Objects.requireNonNull(message).isPresent()) {
            throw new IllegalArgumentException("Message expected. None arrived.");
        } else {
            hideDialog();
            if (message.get() == DialogResponse.ACCEPTED) {
                onDialogAccept.run();
            } else {
                onDialogDecline.run();
            }
        }
    }

    @Override
    public final void setDialogAccepted(final Runnable onAccept) {
        onDialogAccept = Objects.requireNonNull(onAccept);
    }

    @Override
    public final void setDialogDeclined(final Runnable onDecline) {
        onDialogDecline = Objects.requireNonNull(onDecline);
    }

}
