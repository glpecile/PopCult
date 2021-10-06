package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.WatchDao;
import ar.edu.itba.paw.interfaces.WatchService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WatchServiceImpl implements WatchService {
    @Autowired
    private WatchDao watchDao;

    @Transactional
    @Override
    public void addWatchedMedia(int mediaId, int userId) {
        watchDao.addWatchMedia(mediaId, userId, new Date());
    }

    @Transactional
    @Override
    public void addMediaToWatch(int mediaId, int userId) {
        watchDao.addWatchMedia(mediaId, userId, null);
    }

    @Transactional
    @Override
    public void deleteWatchedMedia(int mediaId, int userId) {
        watchDao.deleteWatchedMedia(mediaId, userId);
    }

    @Transactional
    @Override
    public void deleteToWatchMedia(int mediaId, int userId) {
        watchDao.deleteToWatchMedia(mediaId, userId);
    }

    @Transactional
    @Override
    public void updateWatchedMediaDate(int mediaId, int userId, Date date) {
        watchDao.updateWatchedMediaDate(mediaId, userId, date);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isWatched(int mediaId, int userId) {
        return watchDao.isWatched(mediaId, userId);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isToWatch(int mediaId, int userId) {
        return watchDao.isToWatch(mediaId, userId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<WatchedMedia> getWatchedMediaId(int userId, int page, int pageSize) {
        return watchDao.getWatchedMediaId(userId, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getToWatchMediaId(int userId, int page, int pageSize) {
        return watchDao.getToWatchMediaId(userId, page, pageSize);

    }
}
