package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;
import ar.edu.itba.paw.models.user.User;

import java.util.Date;

public interface WatchDao {
    void addWatchMedia(Media media, User user, Date date);

    void deleteWatchedMedia(Media media, User user);

    void deleteToWatchMedia(Media media, User user);

    void updateWatchedMediaDate (Media media, User user, Date date);

    boolean isWatched(Media media, User user);

    boolean isToWatch(Media media, User user);

    PageContainer<WatchedMedia> getWatchedMedia(User user, int page, int pageSize);

    PageContainer<Media> getToWatchMedia(User user, int page, int pageSize);

}
