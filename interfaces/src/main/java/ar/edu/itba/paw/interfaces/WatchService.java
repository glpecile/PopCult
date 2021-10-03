package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WatchService {
    void addWatchedMedia(int mediaId, int userId);

    void addMediaToWatch(int mediaId, int userId);

    void deleteWatchedMedia(int mediaId, int userId);

    void deleteToWatchMedia(int mediaId, int userId);

    void updateWatchedMediaDate(int mediaId, int userId, Date date);

    boolean isWatched(int mediaId, int userId);

    boolean isToWatch(int mediaId, int userId);

    PageContainer<WatchedMedia> getWatchedMediaId(int userId, int page, int pageSize);

    PageContainer<Media> getToWatchMediaId(int userId, int page, int pageSize);

}
