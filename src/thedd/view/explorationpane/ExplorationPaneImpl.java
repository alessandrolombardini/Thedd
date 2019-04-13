package thedd.view.explorationpane;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import thedd.view.explorationpane.enums.PartyType;
import thedd.view.explorationpane.enums.TargetSelectionState;

/**
 * 
 *
 */
public final class ExplorationPaneImpl extends BorderPane implements ExplorationPane {

    private TargetSelectionState viewState;
    private static final double SPACING_VALUE = 2.5; 

    private final HBox enemyParty;
    private final HBox alliedParty;
    private final HBox enemiesAndNext;
    private final ImageView roomAdvancer;

    public ExplorationPaneImpl(final Image backgroundImage) {
        super();

        viewState = TargetSelectionState.INACTIVE;
        enemyParty = new HBox(SPACING_VALUE);
        alliedParty = new HBox(SPACING_VALUE);
        enemiesAndNext = new HBox(SPACING_VALUE);
        roomAdvancer = new ImageView();

        enemiesAndNext.getChildren().add(enemyParty);
        enemiesAndNext.getChildren().add(roomAdvancer);

        roomAdvancer.setImage(new Image(ClassLoader.getSystemResourceAsStream("bianco.png")));
        roomAdvancer.setPreserveRatio(true);
        roomAdvancer.setOnMouseClicked(e -> System.out.println("Prossima stanza"));
        

        enemiesAndNext.setAlignment(Pos.CENTER_RIGHT);

        this.setRight(enemiesAndNext);
        this.setLeft(alliedParty);

        this.widthProperty().addListener(list -> {
            alliedParty.getChildren().forEach(c -> {
                ((ActorViewerImpl) c).setFitWidth(this.getScene().getWidth() / 8);
                ((ActorViewerImpl) c).setFitHeight(this.getScene().getHeight()); 
            });
            enemyParty.getChildren().forEach(c -> {
                ((ActorViewerImpl) c).setFitWidth(this.getScene().getWidth() / 8);
                ((ActorViewerImpl) c).setFitHeight(this.getScene().getHeight());
            });
            roomAdvancer.setFitWidth((roomAdvancer.isVisible() ? 1.0 : Double.MIN_NORMAL) * this.getScene().getWidth() / 8);
        });

        this.heightProperty().addListener(list -> {
            alliedParty.getChildren().forEach(c -> {
                ((ActorViewerImpl) c).setFitWidth(this.getScene().getWidth() / 8);
                ((ActorViewerImpl) c).setFitHeight(this.getScene().getHeight()); 
            });
            enemyParty.getChildren().forEach(c -> {
                ((ActorViewerImpl) c).setFitWidth(this.getScene().getWidth() / 8);
                ((ActorViewerImpl) c).setFitHeight(this.getScene().getHeight()); 
            });
            roomAdvancer.setFitWidth((roomAdvancer.isVisible() ? 1.0 : Double.MIN_NORMAL) * this.getScene().getWidth() / 8);
        });
        changeBackgroundImage(backgroundImage);
    }

    @Override
    public void trigger(final Optional<Pair<PartyType, Integer>> message) {
        System.out.println("Position " + message.get().getRight() + " at " + message.get().getLeft() + " activated");
        
    }

    @Override
    public void setTargetSelectionState(final TargetSelectionState newState) {
        this.viewState = Objects.requireNonNull(newState);
        roomAdvancer.setVisible(this.viewState == TargetSelectionState.EXPLORATION);
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
    public void setEnemyImages(final List<Image> images) {
        enemyParty.getChildren().clear();
        IntStream.range(0, Objects.requireNonNull(images).size()).forEach(i -> enemyParty.getChildren().add(new ActorViewerImpl(PartyType.ENEMY, i, Objects.requireNonNull(images.get(i)))));
        enemyParty.getChildren().forEach(c -> ((ActorViewerImpl) c).bindObserver(this));
    }

    @Override
    public void setAllyImages(final List<Image> images) {
        alliedParty.getChildren().clear();
        IntStream.range(0, Objects.requireNonNull(images).size()).forEach(i -> alliedParty.getChildren().add(new ActorViewerImpl(PartyType.ALLIED, i, Objects.requireNonNull(images.get(i)))));
        alliedParty.getChildren().forEach(c -> ((ActorViewerImpl) c).bindObserver(this));
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

}
