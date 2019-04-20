package thedd.view.explorationpane;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import thedd.utils.observer.Observer;
import thedd.view.explorationpane.enums.PartyType;
import thedd.view.imageloader.DirectoryPicker;
import thedd.view.imageloader.ImageLoader;
import thedd.view.imageloader.ImageLoaderImpl;

/**
 * 
 *
 */
public final class ExplorationPaneImpl extends BorderPane implements ExplorationPane {

    private static final double SPACING_VALUE = 2.5;
    private static final double IMMAGINARY_COLUMNS = 6;
    private static final double PADDING = 10;

    private final HBox enemyParty;
    private final HBox alliedParty;
    private final ImageView roomAdvancer;
    private Observer<Pair<PartyType, Integer>> observer;

    /**
     * Create a new Pane.
     */
    public ExplorationPaneImpl() {
        super();
        final HBox enemiesAndNext = new HBox(SPACING_VALUE);
        enemyParty = new HBox(SPACING_VALUE);
        alliedParty = new HBox(SPACING_VALUE);
        roomAdvancer = new ImageView();

        alliedParty.setAlignment(Pos.BOTTOM_LEFT);
        enemyParty.setAlignment(Pos.BOTTOM_RIGHT);

        enemiesAndNext.getChildren().add(enemyParty);
        enemiesAndNext.getChildren().add(roomAdvancer);
        enemiesAndNext.setAlignment(Pos.BOTTOM_RIGHT);

        final ImageLoader imgl = new ImageLoaderImpl();
        roomAdvancer.setImage(imgl.loadSingleImage(DirectoryPicker.ROOM_CHANGER, "door"));
        roomAdvancer.setPreserveRatio(true);
        roomAdvancer.setPickOnBounds(true);

        this.setRight(enemiesAndNext);
        this.setLeft(alliedParty);
        this.setPadding(new Insets(PADDING));

        this.widthProperty().addListener(list -> resizeAllComponents());
        this.heightProperty().addListener(list -> resizeAllComponents());
    }

    @Override
    public void changeBackgroundImage(final Image newBackground) {
        this.setBackground(new Background(new BackgroundImage(Objects.requireNonNull(newBackground), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false))));
    }

    @Override
    public void changePositionImage(final PartyType partySide, final int position, final Image newImage) {
        final HBox partySelected = getPartyBox(Objects.requireNonNull(partySide));
        ((ActorViewerImpl) Objects.requireNonNull(partySelected).getChildren().get(position)).setImage(Objects.requireNonNull(newImage));
    }

    @Override
    public void updatePartyTooltip(final PartyType partySide, final List<String> newTooltips) {
        final HBox partySelected = getPartyBox(Objects.requireNonNull(partySide));
        if (partySelected.getChildren().size() != Objects.requireNonNull(newTooltips).size()) {
            throw new IllegalArgumentException("The size of the tooltip list is not equal the size of the party targeted");
        }
        IntStream.range(0, newTooltips.size()).forEach(i -> ((ActorViewerImpl) Objects.requireNonNull(partySelected).getChildren().get(i)).updateTooltipText(Objects.requireNonNull(newTooltips.get(i))));
    }

    @Override
    public void updatePositionTooltip(final Pair<PartyType, Integer> position, final String newTooltip) {
        final HBox partySelected = getPartyBox(position.getLeft());
        if (Objects.requireNonNull(position).getRight() < partySelected.getChildren().size()) {
            ((ActorViewerImpl) partySelected.getChildren().get(position.getRight())).updateTooltipText(Objects.requireNonNull(newTooltip));
        } else {
            throw new IllegalArgumentException("The position passed does not exist.");
        }
    }

    @Override
    public void setEnemyImages(final List<Image> images) {
        enemyParty.getChildren().clear();
        IntStream.range(0, Objects.requireNonNull(images).size()).forEach(i -> enemyParty.getChildren().add(new ActorViewerImpl(PartyType.ENEMY, i, Objects.requireNonNull(images.get(i)))));
        enemyParty.getChildren().forEach(c -> ((ActorViewerImpl) c).bindObserver(observer));
        resizeAllComponents();
    }

    @Override
    public void setAllyImages(final List<Image> images) {
        alliedParty.getChildren().clear();
        IntStream.range(0, Objects.requireNonNull(images).size()).forEach(i -> alliedParty.getChildren().add(new ActorViewerImpl(PartyType.ALLIED, i, Objects.requireNonNull(images.get(i)))));
        alliedParty.getChildren().forEach(c -> ((ActorViewerImpl) c).bindObserver(observer));
        resizeAllComponents();
    }

    @Override
    public void setAllImages(final List<Image> allyImages, final List<Image> enemyImages) {
        setAllyImages(allyImages);
        setEnemyImages(enemyImages);
    }

    @Override
    public void setTargetablePositions(final List<Pair<PartyType, Integer>> targetableList,
                                       final List<Pair<PartyType, Integer>> allActors) {
        enemyParty.getChildren().forEach(c -> c.setDisable(true));
        alliedParty.getChildren().forEach(c -> c.setDisable(true));
        Objects.requireNonNull(allActors).stream()
                 .filter(a -> targetableList.contains(Objects.requireNonNull(a)))
                 .forEach(a -> getPartyBox(Objects.requireNonNull(a.getLeft())).getChildren().get(a.getRight()).setDisable(false));
    }

    @Override
    public void setAllAsTargetable() {
        enemyParty.getChildren().forEach(c -> c.setDisable(false));
        alliedParty.getChildren().forEach(c -> c.setDisable(false));
    }


    @Override
    public Node getRoomAdvancer() {
        return roomAdvancer;
    }

    @Override
    public void setActorViewerObserver(final Observer<Pair<PartyType, Integer>> newObserver) {
        this.observer = Objects.requireNonNull(newObserver);
    }

    @Override
    public void setRoomAdvancerVisible(final boolean isVisible) {
        roomAdvancer.setVisible(isVisible);
    }

    private HBox getPartyBox(final PartyType partySide) {
        switch (partySide) {
        case ALLIED:
            return alliedParty;
        case ENEMY:
            return enemyParty;
        default:
            return null;
        }
    }

    private void resizeAllComponents() {
        alliedParty.getChildren().forEach(c -> {
            ((ActorViewerImpl) c).setFitWidth(this.widthProperty().doubleValue() / IMMAGINARY_COLUMNS);
            ((ActorViewerImpl) c).setFitHeight(this.heightProperty().doubleValue()); 
        });
        enemyParty.getChildren().forEach(c -> {
            ((ActorViewerImpl) c).setFitWidth(this.widthProperty().doubleValue() / IMMAGINARY_COLUMNS);
            ((ActorViewerImpl) c).setFitHeight(this.heightProperty().doubleValue());
        });
        roomAdvancer.setFitWidth((roomAdvancer.isVisible() ? 1.0 : Double.MIN_NORMAL) * this.widthProperty().doubleValue() / IMMAGINARY_COLUMNS);
    }
}
