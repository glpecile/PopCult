package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.image.Image;

import java.util.Optional;

public interface ImageDao {
    Optional<Image> getImage(int imageId);

    Image uploadImage(byte[] photoBlob);

    void deleteImage(Image image);
}
