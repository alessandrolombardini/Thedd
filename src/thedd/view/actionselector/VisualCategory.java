package thedd.view.actionselector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;
import thedd.view.imageloader.DirectoryPicker;
import thedd.view.imageloader.ImageLoaderImpl;

/**
 * A class used to display information about the category of a group of {@link Action}s.<br>
 * It holds an image associated with the action and specifies the formatting
 * of the text.
 */
public class VisualCategory {

    private final String name;
    private final List<VisualAction> actions = new ArrayList<>();
    private final Image image;

    /**
     * @param name the name of the category
     * @param actions the list of associated actions
     */
    public VisualCategory(final String name, final List<VisualAction> actions) {
        this.name = name;
        this.actions.addAll(actions);
        this.image = new ImageLoaderImpl().loadSingleImage(DirectoryPicker.ACTION_CATEGORIES, name);
    }

    /**
     * Gets the name of the category.
     * @return the name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of {@link VisualAction}s associated with the category.
     * @return the list of actions link to the category
     */
    public List<VisualAction> getActions() {
        return Collections.unmodifiableList(actions);
    }

    /**
     * Gets an image representing the category.
     * @return the image linked to the category
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gets a text describing the category to be displayed to the player.
     * @return the description of the category
     */
    public String getDescription() {
        final String s = "Category name: " + getName();
        final StringBuilder sb = new StringBuilder(s);
        if (!getActions().isEmpty()) {
            sb.append("\n\nActions in this category: ");
            getActions().forEach(a -> sb.append("\n\t").append(a.getName()));
        }
        return sb.toString();
    }

}
