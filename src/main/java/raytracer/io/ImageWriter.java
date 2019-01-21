package raytracer.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Writes images to disc.
 */
public class ImageWriter {

    /**
     * Writes a BufferedImage to disc.
     * @param image the image to write
     * @param format the image format to write to
     * @param name the file name of the written image
     */
    public static void writeImage(BufferedImage image, String format, String name) {
        Path currentDirectory = Paths.get("").toAbsolutePath();
        writeImage(image, format, currentDirectory, name);
    }

    /**
     * Writes a BufferedImage to disc.
     * @param image the image to write
     * @param format the image format
     * @param target the location to write to
     * @param name the file name of the written image
     */
    public static void writeImage(BufferedImage image, String format, Path target, String name) {
        try {
            ImageIO.write(
                    image,
                    format,
                    new File(target + File.separator + name + "." + format)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
