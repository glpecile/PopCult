package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.WatchDao;
import ar.edu.itba.paw.interfaces.WatchService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WatchServiceImpl implements WatchService {
    @Autowired
    private WatchDao watchDao;

    @Override
    public void addWatchedMedia(int mediaId, int userId) {
        watchDao.addWatchMedia(mediaId, userId, new Date());
    }

    @Override
    public void addMediaToWatch(int mediaId, int userId) {
        watchDao.addWatchMedia(mediaId, userId, null);
    }

    @Override
    public void deleteWatchedMedia(int mediaId, int userId) {
        watchDao.deleteWatchedMedia(mediaId, userId);
    }

    @Override
    public void deleteToWatchMedia(int mediaId, int userId) {
        watchDao.deleteToWatchMedia(mediaId, userId);
    }

    @Override
    public void updateWatchedMediaDate(int mediaId, int userId, Date date) {
        watchDao.updateWatchedMediaDate(mediaId, userId, date);
    }

    @Override
    public boolean isWatched(int mediaId, int userId) {
        return watchDao.isWatched(mediaId, userId);
    }

    @Override
    public boolean isToWatch(int mediaId, int userId) {
        return watchDao.isToWatch(mediaId, userId);
    }

//    @Override
//    public PageContainer<Integer> getWatchedMediaIdIds(int userId, int page, int pageSize) {
//        return watchDao.getWatchedMediaIdIds(userId, page, pageSize);
//    }

    @Override
    public PageContainer<WatchedMedia> getWatchedMediaId(int userId, int page, int pageSize) {
        return watchDao.getWatchedMediaId(userId, page, pageSize);
    }

//    @Override
//    public Optional<Integer> getWatchedMediaCount(int userId) {
//        return watchDao.getWatchedMediaCount(userId);
//    }

//    @Override
//    public PageContainer<Integer> getToWatchMediaIdIds(int userId, int page, int pageSize) {
//        return watchDao.getToWatchMediaIdIds(userId, page, pageSize);
//    }

    @Override
    public PageContainer<Media> getToWatchMediaId(int userId, int page, int pageSize) {
        return watchDao.getToWatchMediaId(userId, page, pageSize);

    }

//    @Override
//    public Optional<Integer> getToWatchMediaCount(int userId) {
//        return watchDao.getToWatchMediaCount(userId);
//    }
}
