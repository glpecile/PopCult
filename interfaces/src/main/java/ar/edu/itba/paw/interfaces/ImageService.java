package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.image.Image;

import java.util.Optional;

public interface ImageService {
    Optional<Image> getImage(int imageId);

    Optional<Image> uploadImage(byte[] photoBlob, long imageContentLength, String imageContentType);
}
