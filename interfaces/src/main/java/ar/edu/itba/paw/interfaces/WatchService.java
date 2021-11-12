package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;
import ar.edu.itba.paw.models.user.User;

import java.time.LocalDateTime;

public interface WatchService {
    void addWatchedMedia(Media media, User user);

    void addMediaToWatch(Media media, User user);

    void deleteWatchedMedia(Media media, User user);

    void deleteToWatchMedia(Media media, User user);

    void updateWatchedMediaDate(Media media, User user, LocalDateTime date);

    boolean isWatched(Media media, User user);

    boolean isToWatch(Media media, User user);

    PageContainer<WatchedMedia> getWatchedMediaId(User user, int page, int pageSize);

    PageContainer<Media> getToWatchMediaId(User user, int page, int pageSize);

}
