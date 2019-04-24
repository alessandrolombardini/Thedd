package thedd.view.actionselector;

import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.image.Image;
import thedd.model.combat.action.Action;
import thedd.view.imageloader.DirectoryPicker;
import thedd.view.imageloader.ImageLoaderImpl;

/**
 * A class which encapsulates an {@link Action} and exposes methods
 * used for displaying it in the view.<br>
 * It holds an image associated with the action and specifies the formatting
 * of the text.
 */
public class VisualAction {

    private final Action action;
    private final Image image;

    /**
     * @param action the action to show.
     */
    public VisualAction(final Action action) {
        this.action = action;
        this.image = new ImageLoaderImpl().loadSingleImage(DirectoryPicker.ACTIONS, action.getName());
    }

    /**
     * Gets a copy of the associated {@link Action}.
     * @return the associated action
     */
    public Action getAction() {
        return action.getCopy();
    }

    /**
     * Gets an image representing the associated {@link Action}.
     * @return the image linked to the action
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Gets whether the action can be selected.
     * @return true if the action is selectable, false otherwise
     */
    public boolean canSelect() {
        return action.getRequirements().stream().allMatch(r -> r.isFulfilled(action));
    }

    /**
     * Gets the name of the associated {@link Action}.
     * @return the name of the action
     */
    public String getName() {
        return action.getName();
    }

    /**
     * Gets a formatted description of the associated {@link Action}
     * to be shown to the player.<br>
     * Note that the message returned from this method is not necessarily
     * the same obtainable via {@link Action#getDescription()}
     * @return a description of the action
     */
    public String getDescription() {
        final StringBuilder sb = new StringBuilder(getName());
        sb.append("\n\n");
        if (!getTags().isEmpty()) {
            sb.append(getTags())
              .append('\n');
        }
        sb.append(action.getDescription())
          .append("\n\n")
          .append(getEffectsPreview())
          .append("Base hitchance: ")
          .append(getBaseHitChance())
          .append('%');
        if (!action.getRequirements().isEmpty()) {
            sb.append("\n\nRequirements: ");
            action.getRequirements().forEach(r -> sb.append('\n')
                                                    .append(r));
        }
        return sb.toString();
    }

    private List<String> getTags() {
        return action.getTags().stream()
                               .filter(t -> !t.isHidden())
                               .map(t -> t.getLiteral()).collect(Collectors.toList());
    }

    private String getEffectsPreview() {
        return action.getEffectsPreview(null);
    }

    private double getBaseHitChance() {
        return action.getBaseHitChance() * 100;
    }

}

