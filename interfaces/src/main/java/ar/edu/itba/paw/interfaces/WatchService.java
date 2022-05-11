package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;
import ar.edu.itba.paw.models.user.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WatchService {
    void addWatchedMedia(Media media, User user, LocalDateTime dateTime);

    void addMediaToWatch(Media media, User user);

    void deleteWatchedMedia(Media media, User user);

    void deleteToWatchMedia(Media media, User user);

//    void updateWatchedMediaDate(Media media, User user, LocalDateTime date);

    boolean isWatched(Media media, User user);

    boolean isToWatch(Media media, User user);

    Optional<WatchedMedia> getWatchedMedia(User user, Media media);

    PageContainer<WatchedMedia> getWatchedMedia(User user, int page, int pageSize);

    PageContainer<Media> getToWatchMedia(User user, int page, int pageSize);

}
