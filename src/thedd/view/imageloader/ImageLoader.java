package thedd.view.imageloader;

import javafx.scene.image.Image;

/**
 * Factory Of Images.
 */
public interface ImageLoader {

    /**
     * This method returns an image loaded from specified directory and with
     * specified String objname. If the image isn't found inside the directory, then
     * a default image will be loaded.
     * 
     * @param directory a DirectoryPicker value, represents the directory where the
     *                  specified image with objname is searched.
     * @param objname   a String representation of the image that will be loaded.
     *                  This value could be the name of the object.
     * @return an Image
     */
    Image loadSingleImage(DirectoryPicker directory, String objname);

}
