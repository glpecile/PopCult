package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.WatchDao;
import ar.edu.itba.paw.interfaces.WatchService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class WatchServiceImpl implements WatchService {
    @Autowired
    private WatchDao watchDao;

    @Transactional
    @Override
    public void addWatchedMedia(Media media, User user) {
        watchDao.addWatchMedia(media, user, LocalDateTime.now());
    }

    @Transactional
    @Override
    public void addMediaToWatch(Media media, User user) {
        watchDao.addWatchMedia(media, user, null);
    }

    @Transactional
    @Override
    public void deleteWatchedMedia(Media media, User user) {
        watchDao.deleteWatchedMedia(media, user);
    }

    @Transactional
    @Override
    public void deleteToWatchMedia(Media media, User user) {
        watchDao.deleteToWatchMedia(media, user);
    }

    @Transactional
    @Override
    public void updateWatchedMediaDate(Media media, User user, LocalDateTime date) {
        watchDao.updateWatchedMediaDate(media, user, date);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isWatched(Media media, User user) {
        return watchDao.isWatched(media, user);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isToWatch(Media media, User user) {
        return watchDao.isToWatch(media, user);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<WatchedMedia> getWatchedMediaId(User user, int page, int pageSize) {
        return watchDao.getWatchedMedia(user, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getToWatchMediaId(User user, int page, int pageSize) {
        return watchDao.getToWatchMedia(user, page, pageSize);

    }
}
