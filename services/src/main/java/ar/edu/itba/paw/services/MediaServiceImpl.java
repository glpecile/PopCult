package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.MediaDao;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.models.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaDao mediaDao;

    @Override
    public Optional<Media> getById(int mediaId) {
        return mediaDao.getById(mediaId);
    }

    @Override
    public List<Media> getMediaList() {
        return mediaDao.getMediaList();
    }

    @Override
    public List<Media> getMediaList(int mediaType, int page, int pageSize) {
        return mediaDao.getMediaList(mediaType, page, pageSize);
    }
}
