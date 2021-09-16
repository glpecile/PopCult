package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FavoriteService;
import ar.edu.itba.paw.interfaces.MediaDao;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.media.Media;
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
    public List<Media> getById(List<Integer> mediaIds) {
        return mediaDao.getById(mediaIds);
    }

    @Override
    public List<Media> getMediaList(int page, int pageSize) {
        return mediaDao.getMediaList( page,  pageSize);
    }

    @Override
    public List<Media> getMediaList(int mediaType, int page, int pageSize) {
        return mediaDao.getMediaList(mediaType, page, pageSize);
    }

    @Override
    public Optional<Integer> getMediaCount() {
        return mediaDao.getMediaCount();
    }

    @Override
    public Optional<Integer> getMediaCountByMediaType(int mediaType) {
        return mediaDao.getMediaCountByMediaType(mediaType);
    }

    @Override
    public List<Media> getLatestMediaList(int mediaType, int page, int pageSize) {
        return mediaDao.getLatestMediaList(mediaType, page, pageSize);
    }

    @Override
    public List<Media> searchMediaByTitle(String title, int page, int pageSize) {
        return mediaDao.searchMediaByTitle(title, page, pageSize);
    }

    @Override
    public Optional<Integer> getCountSearchMediaByTitle(String title) {
        return mediaDao.getCountSearchMediaByTitle(title);
    }
}
