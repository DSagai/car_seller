package sagai.dmytro.car.seller.utility;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Class provides functionality for resizing
 * and recoding images.
 *
 * @author dsagai
 * @version 1.00
 * @since 09.06.2017
 */
@Component("imageConverterService")
public class ImageConverterService {

    /**
     * method returns resized image converted into JPG format.
     * Aspect ratio is preserved.
     * @param size StandardVerticalDimensions size of vertical dimension.
     * @param in byte[]
     * @return byte[]
     */
    public byte[] getResizedImage(StandardVerticalDimensions size, byte[] in) throws IOException {
        final String OUTPUT_FORMAT = "JPG";
        return getResizedImage(size, in, OUTPUT_FORMAT);
    }

    /**
     * Method returns resized image, converted into requested format.
     * Aspect ratio is preserved.
     * @param size
     * @param in
     * @param format
     * @return
     * @throws IOException
     */
    public byte[] getResizedImage(StandardVerticalDimensions size, byte[] in, String format) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(in);
        BufferedImage originalImage = ImageIO.read(byteArrayInputStream);
        int horizontalSize = (int)((float)originalImage.getWidth()/originalImage.getHeight() * size.getPixels());

        BufferedImage resizedImage = getResizedImage(originalImage, size.getPixels(), horizontalSize);

        ImageIO.write(resizedImage, format, out);

        return out.toByteArray();
    }

    /**
     * Method resize image into any new size with no connection to the previous dimensions.
     * @param originalImage BufferedImage image, that has to be resized.
     * @param verticalSize int new vertical size in pixels.
     * @param horizontalSize int new horizontal size in pixels.
     * @return BufferedImage.
     */
    public BufferedImage getResizedImage(BufferedImage originalImage,
                                         int verticalSize, int horizontalSize) {

        int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage resizedImage = new BufferedImage(horizontalSize, verticalSize, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, horizontalSize, verticalSize, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }




    /**
     * enum describes standard dimensions for
     * image converter
     */
    public enum StandardVerticalDimensions {
        Large(600),
        Small(200);

        private final int pixels;

        StandardVerticalDimensions(int pixels) {
            this.pixels = pixels;
        }

        public int getPixels() {
            return pixels;
        }
    }
}
