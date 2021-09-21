package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ImageDao;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.models.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageDao imageDao;

    @Override
    public Optional<Image> getUserProfilePicture(int userId) {
        return imageDao.getUserProfilePicture(userId);
    }

    @Override
    public Optional<Image> uploadUserProfilePicture(int userId, byte[] photoBlob, Integer imageContentLength, String imageContentType) {
        return imageDao.uploadUserProfilePicture(userId, photoBlob, imageContentLength, imageContentType);
    }
}
