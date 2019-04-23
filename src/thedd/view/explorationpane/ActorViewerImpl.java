package thedd.view.explorationpane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import thedd.utils.observer.Observable;
import thedd.utils.observer.Observer;
import thedd.view.explorationpane.enums.PartyType;

/**
 * {@link thedd.utils.observer.Observable} {@link thedd.view.explorationpane.ActorViewer}.
 * It uses an {@link javafx.scene.image.ImageView} to visualize the actor.
 */
public class ActorViewerImpl extends ImageView implements Observable<Pair<Boolean, Pair<PartyType, Integer>>>, ActorViewer {

    private final PartyType partySide;
    private final int partyPosition;
    private final List<Observer<Pair<Boolean, Pair<PartyType, Integer>>>> registeredObservers;
    private final Tooltip tooltip;
    private Optional<Pair<Boolean, Pair<PartyType, Integer>>> message;

    /**
     * Create a new instance of ActorViewer which visualize an actor of the party partySide, 
     * in position partyPosition, using the initialImage.
     * @param partySide
     *          the party of the Actor visualized
     * @param partyPosition
     *          the position in the party of the actor
     * @param initialImage
     *          the image to show
     */
    public ActorViewerImpl(final PartyType partySide, final int partyPosition, final Image initialImage) {
        super(Objects.requireNonNull(initialImage));
        this.partySide = Objects.requireNonNull(partySide);
        this.partyPosition = partyPosition;
        registeredObservers = new ArrayList<>();
        this.setPreserveRatio(true);
        this.setPickOnBounds(true);

        tooltip = new Tooltip();
        this.setOnMouseClicked(e -> {
            tooltip.hide();
            message = Optional.of(new ImmutablePair<>(true, new ImmutablePair<>(partySide, partyPosition)));
            this.emit();
        });
        this.setOnMouseEntered(e -> {
            if (!this.isDisabled()) {
                message = Optional.of(new ImmutablePair<>(false, new ImmutablePair<>(partySide, partyPosition)));
                this.emit();
                /* Snippet code taken from 
                   https://stackoverflow.com/questions/13049362/javafx-how-to-set-correct-tooltip-position
                   and modified to support needs.
                */
                final Point2D p = this.localToScreen(0.0, 0.0);
                final int direction = partySide == PartyType.ALLIED ? 1 : -1;
                tooltip.show(this, 0, 0);
                tooltip.hide();
                tooltip.show(this,
                             p.getX() + tooltip.getWidth() * direction,
                             p.getY() + tooltip.getHeight() / 2);
            }
        });
        this.setOnMouseExited(e -> {
            if (!this.isDisabled()) {
                message = Optional.of(new ImmutablePair<>(false, new ImmutablePair<>(PartyType.ALLIED, 0)));
                this.emit();
            }
            tooltip.hide();
        });
    }

    @Override
    public final void emit() {
        registeredObservers.forEach(o -> o.trigger(message));
    }

    @Override
    public final void bindObserver(final Observer<Pair<Boolean, Pair<PartyType, Integer>>> newObserver) {
        registeredObservers.add(Objects.requireNonNull(newObserver));
    }

    @Override
    public final void removeObserver(final Observer<Pair<Boolean, Pair<PartyType, Integer>>> observer) {
        if (!registeredObservers.remove(Objects.requireNonNull(observer))) {
            throw new IllegalStateException("The observer was not in the list");
        }
    }

    @Override
    public final Collection<Observer<Pair<Boolean, Pair<PartyType, Integer>>>> getRegisteredObservers() {
        return Collections.unmodifiableList(registeredObservers);
    }

    @Override
    public final PartyType getPartySide() {
        return partySide;
    }

    @Override
    public final int getPartyPosition() {
        return partyPosition;
    }

    @Override
    public final void updateTooltipText(final String newText) {
        tooltip.setText(Objects.requireNonNull(newText));
    }


}
