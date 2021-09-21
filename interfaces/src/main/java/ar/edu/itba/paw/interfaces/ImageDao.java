package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.image.Image;

import java.util.Optional;

public interface ImageDao {
    Optional<Image> getUserProfilePicture(int userId);

    Optional<Image> uploadUserProfilePicture(int userId, byte[] photoBlob, Integer imageContentLength, String imageContentType);
}
