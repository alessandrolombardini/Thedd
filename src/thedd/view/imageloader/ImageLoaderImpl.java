package thedd.view.imageloader;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * implementation of {@link ImageLoader}.
 */
public final class ImageLoaderImpl implements ImageLoader {
    private final Image defaultImage = new Image("images/default.png");
    private static final Map<String, Image> IMAGE_CACHE = new HashMap<>();
    private static final String EXTENSION = ".png";
    private static final String SPACE = " ";
    private static final String UNDERSCORE = "_";

    /**
     * {@inheritDoc}
     */
    @Override
    public Image loadSingleImage(final DirectoryPicker directory, final String objname) {
        final String fileName = objname.toLowerCase().replace(SPACE, UNDERSCORE).concat(EXTENSION);
        final URL path = this.getClass().getResource(directory.getDirectory() + fileName);
        if (path == null) {
            System.err.println("Image " + fileName + " not found.");
            return this.defaultImage;
        }
        if (!IMAGE_CACHE.containsKey(path.toString())) {
            IMAGE_CACHE.put(path.toString(), new Image(path.toString()));
        }
        return IMAGE_CACHE.get(path.toString());
    }

}
