package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.ImageConversionException;
import ar.edu.itba.paw.models.image.Image;

import java.util.Optional;

public interface ImageService {
    Optional<Image> getImage(int imageId);

    Optional<Image> getImage(String imagePath) throws ImageConversionException;

    Image uploadImage(byte[] photoBlob);
}
