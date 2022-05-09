package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ImageDao;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.interfaces.exceptions.ImageConversionException;
import ar.edu.itba.paw.models.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.image.ToolkitImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public Optional<Image> getImage(int imageId) {
        return imageDao.getImage(imageId);
    }

    @Override
    public Optional<Image> getImage(String imagePath) throws ImageConversionException {
        try {
            final byte[] imageBytes = Files.readAllBytes(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource(imagePath)).toURI()));
            final Image image = new Image(0, imageBytes);
            return Optional.of(image);
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("Image conversion failed.");
            throw new ImageConversionException();
        }
    }

    @Transactional
    @Override
    public Image uploadImage(byte[] photoBlob) {
        return imageDao.uploadImage(photoBlob);
    }

    @Transactional
    @Override
    public Image uploadImage(byte[] photoBlob, int width, int height, String format) throws ImageConversionException {
        byte[] resizedImage = getScaledImage(photoBlob, width, height, format);
        return imageDao.uploadImage(resizedImage);
    }

    private byte[] getScaledImage(byte[] image, int width, int height, String format) throws ImageConversionException {
        byte[] resizedImage;
        ByteArrayInputStream bais = new ByteArrayInputStream(image);

        try {
            java.awt.Image tmpImage = ImageIO.read(bais);
            java.awt.Image scaled = tmpImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);

            // wait for image to be ready
            MediaTracker tracker = new MediaTracker(new java.awt.Container());
            tracker.addImage(scaled, 0);
            tracker.waitForAll();

            BufferedImage buffered = ((ToolkitImage) scaled).getBufferedImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffered, "png", baos);  // When put original format, colors are corrupted.
            baos.flush();
            resizedImage = baos.toByteArray();
            baos.close();
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Image conversion failed. {}", e.getMessage());
            throw new ImageConversionException();
        }
        return resizedImage;
    }

    @Transactional
    @Override
    public void deleteImage(Image image) {
        imageDao.deleteImage(image);
    }
}
