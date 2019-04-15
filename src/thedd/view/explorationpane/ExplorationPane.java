package thedd.view.explorationpane;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import javafx.scene.image.Image;
import thedd.utils.observer.Observer;
import thedd.view.explorationpane.enums.PartyType;
import thedd.view.explorationpane.enums.TargetSelectionState;

/**
 * The game sub-scene with ActorViewer to show actors of the game.
 */
public interface ExplorationPane extends Observer<Pair<PartyType, Integer>> {

    /**
     * Set the new state to respond to triggers from {@link thedd.utils.observer Observable}s.
     * @param newState
     *          the new state of the target selection
     */
    void setTargetSelectionState(TargetSelectionState newState);

    /**
     * Changes the background image of the pane with a new image.
     * 
     * @param newBackground
     *          the new image to be used as background. It cannot be null.
     */
    void changeBackgroundImage(Image newBackground);

    /**
     * Changes the image of the {@link ActorViewer} in the specified party and position.
     * @param partySide
     *          the party in which the ActorViewer is present
     * @param position
     *          the position in the party of the ActorViewer
     * @param newImage
     *          the Image to display. It cannot be null
     */
    void changePositionImage(PartyType partySide, int position, Image newImage);

    /**
     * Create a new party of enemies with the images in the list.
     * The number of ActorViewer created is equal to the size of the list of Images. 
     * @param images
     *          the images to use in the new ActorViewer
     */
    void setEnemyImages(List<Image> images);

    /**
     * Create a new party of allies with the images in the list.
     * The number of ActorViewer created is equal to the size of the list of Images.
     * @param images
     *          the images to use in the new ActorViewer
     */
    void setAllyImages(List<Image> images);

    /**
     * Create new enemy and ally parties with the images in the corresponding list.
     * @param allyImages
     *          the images to use to create the new party of allies
     * @param enemyImages
     *          the images to use to create the new party of enemies
     */
    void setAllImages(List<Image> allyImages, List<Image> enemyImages);

    /**
     * Set the tooltips of all the ActorViewer in the specified party.
     * @throws IllegalArgumentException
     *          when the size of the list is not equal to the size of the party
     * @param partySide
     *          the party to change the tooltip of
     * @param newTooltips
     *          the list of the new tooltips
     */
    void updatePartyTooltip(PartyType partySide, List<String> newTooltips);

    /**
     * Enable the interaction with the ActorViewer specified in the list of possible targets.
     * The other ActorViewer are disabled until a new call of this method. 
     * @param targetableList
     *          the list of possible targets
     * @param allActors
     *          the list of all targets in the view
     */
    void setTargetablePositions(List<Pair<PartyType, Integer>> targetableList, List<Pair<PartyType, Integer>> allActors);

}
