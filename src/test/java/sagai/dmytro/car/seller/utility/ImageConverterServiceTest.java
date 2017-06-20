package sagai.dmytro.car.seller.utility;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 09.06.2017
 */
public class ImageConverterServiceTest {
    private ImageConverterService imageConverterService = new ImageConverterService();

    @Test
    public void whenResizePictureThenReceivePictureWithNewDimensions() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("test/Desert.jpg");
        int height = 120;
        int width = 100;

        BufferedImage bufferedImage = ImageIO.read(in);

        BufferedImage resizedImage = this.imageConverterService.getResizedImage(bufferedImage,
                height, width);

        assertThat(resizedImage.getHeight(), is(height));
        assertThat(resizedImage.getWidth(), is(width));
    }

    @Test
    public void whenChangeOnlyHeightThenAspectRatioPreserved() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("test/Desert.jpg");
        BufferedImage originalImage = ImageIO.read(in);
        ByteArrayOutputStream imageByteRepresentation = new ByteArrayOutputStream();
        ImageIO.write(originalImage,"JPG", imageByteRepresentation);
        float aspectRatio = Math.round((float)originalImage.getHeight() / originalImage.getWidth() * 100);

        byte[] resizedImageByteRepresentation = this.imageConverterService
                .getResizedImage(ImageConverterService.StandardVerticalDimensions.Large,
                imageByteRepresentation.toByteArray());
        BufferedImage newImage = ImageIO.read(new ByteArrayInputStream(resizedImageByteRepresentation));
        float newImageAspectRatio = Math.round((float)newImage.getHeight() / newImage.getWidth() * 100);
        assertThat(newImageAspectRatio, is(aspectRatio));


    }
}