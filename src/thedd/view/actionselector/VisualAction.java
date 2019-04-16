package thedd.view.actionselector;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.image.Image;
import thedd.model.combat.action.Action;

public class VisualAction {

    private final Action action;
    private final Image image;

    public VisualAction(final Action action) {
        this.action = action;
        final String fileName = action.getName().toLowerCase().replace(' ', '_').concat(".png");
        final URL url = this.getClass().getResource("/actions/" + fileName);
        if (url != null) {
            this.image = new Image(url.toString());
        } else {
            System.err.println("Image for action " + fileName + " not found");
            this.image = new Image(this.getClass().getResourceAsStream("/actions/not_found.png"));
        }
    }

    public Image getImage() {
        return this.image;
    }

    public String getName() {
        return action.getName();
    }

    public String getDescription() {
        return action.getDescription();
    }

    public List<String> getTags() {
        return action.getTags().stream().map(t -> t.getLiteral()).collect(Collectors.toList());
    }

    public String getEffectsPreview() {
        return action.getEffectsPreview(null);
    }

}

