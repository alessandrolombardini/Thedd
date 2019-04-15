package thedd.view.explorationpane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

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
public class ActorViewerImpl extends ImageView implements Observable<Pair<PartyType, Integer>>, ActorViewer {

    private final PartyType partySide;
    private final int partyPosition;
    private final List<Observer<Pair<PartyType, Integer>>> registeredObservers;
    private final Tooltip tooltip;

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
        this.setOnMouseClicked(e -> this.emit());

        tooltip = new Tooltip();
        Tooltip.install(this, tooltip);
        this.setOnMouseEntered(e -> tooltip.show(this, 0.0, 0.0));
        this.setOnMouseExited(e -> tooltip.hide());
    }

    @Override
    public final void emit() {
        registeredObservers.forEach(o -> o.trigger(Optional.of(Pair.of(partySide, partyPosition))));
    }

    @Override
    public final void bindObserver(final Observer<Pair<PartyType, Integer>> newObserver) {
        registeredObservers.add(Objects.requireNonNull(newObserver));
    }

    @Override
    public final void removeObserver(final Observer<Pair<PartyType, Integer>> observer) {
        if (!registeredObservers.remove(Objects.requireNonNull(observer))) {
            throw new IllegalStateException("The observer was not in the list");
        }
    }

    @Override
    public final Collection<Observer<Pair<PartyType, Integer>>> getRegisteredObservers() {
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
