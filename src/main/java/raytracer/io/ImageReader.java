package raytracer.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageReader {

    /**
     * Reads an image from filesystem and returns it as BufferedImage.
     * @param path The (absolute) file path to the image.
     * @return The image.
     */
    public static BufferedImage readImage(String path) {
        File file = new File(path);
        // check if this image exists
        if(!file.exists() || file.isDirectory()) {
            throw new RuntimeException("Image does not exist: " + file.getAbsolutePath());
        }

        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not load image: " + file.getAbsolutePath());
        }
    }

}
