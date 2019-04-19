package thedd.view.imageloader;

import java.net.URL;

import javafx.scene.image.Image;

/**
 * implementation of {@link ImageLoader}.
 */
public final class ImageLoaderImpl implements ImageLoader {
    private final Image defaultImage = new Image("images/default.png");

    /**
     * {@inheritDoc}
     */
    @Override
    public Image loadSingleImage(final DirectoryPicker directory, final String objname) {
        final String fileName = objname.toLowerCase().replace(" ", "_").concat(".png");
        final URL path = this.getClass().getResource(directory.getDirectory() + fileName);
        if (path == null) {
            return this.defaultImage;
        }
        return new Image(path.toString());
    }

}
