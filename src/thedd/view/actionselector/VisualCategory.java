package thedd.view.actionselector;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;

public class VisualCategory {

    private final String name;
    private final List<VisualAction> actions = new ArrayList<>();
    private final Image image;

    public VisualCategory(final String name, final List<VisualAction> actions) {
        this.name = name;
        this.actions.addAll(actions);
        final String fileName = name.toLowerCase() + ".png";
        final URL url = this.getClass().getResource("/actions/categories/" + fileName);
        if (url != null) {
            this.image = new Image(url.toString());
        } else {
            System.err.println("Image for category " + fileName + " not found");
            this.image = new Image(this.getClass().getResourceAsStream("/actions/not_found.png"));
        }
    }

    public String getName() {
        return name;
    }

    public List<VisualAction> getActions() {
        return Collections.unmodifiableList(actions);
    }

    public Image getImage() {
        return image;
    }

}
