package thedd.view.explorationpane;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import thedd.utils.observer.Observer;
import thedd.view.explorationpane.confirmationdialog.BiOptionDialog;
import thedd.view.explorationpane.confirmationdialog.DialogResponse;

public class TopStackPane extends StackPane implements Observer<DialogResponse> {

    private final ExplorationPaneImpl ep;
    private final BiOptionDialog bop = new BiOptionDialog();

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

    public TopStackPane() {
        ep = new ExplorationPaneImpl(this);
        this.setAlignment(Pos.CENTER);
        ep.changeBackgroundImage(new Image(ClassLoader.getSystemResourceAsStream("bgimg.jpg")));
        this.getChildren().add(ep);
        
        bop.translateXProperty().bind(this.widthProperty().divide(2).subtract(bop.getWidthProperty().divide(2)));
        bop.translateYProperty().bind(this.heightProperty().divide(2).subtract(bop.getHeightProperty().divide(2)));
        bop.getWidthProperty().bind(this.widthProperty().divide(2));
        bop.getHeightProperty().bind(this.heightProperty().divide(2));
        
        bop.bindObserver(this);
        
        //showDialog("Continui?");
    }

    /**
     * 
     * @param newText
     */
    void showDialog(final String newText) {
        bop.setText(newText);
        this.getChildren().add(bop);
        this.addEventFilter(MouseEvent.ANY, filter);
    }

    /**
     * 
     */
    void hideDialog() {
        this.getChildren().remove(bop);
        this.removeEventFilter(MouseEvent.ANY, filter);
    }

    @Override
    public final void trigger(final Optional<DialogResponse> message) {
        if (!message.isPresent()) {
            throw new IllegalArgumentException("Message expected. None arrived.");
        } else {
            hideDialog();
            if (message.get() == DialogResponse.ACCEPTED) {
                
            }
        }
    }



}
