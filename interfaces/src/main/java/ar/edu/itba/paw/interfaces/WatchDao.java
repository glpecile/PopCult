package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WatchDao {
    void addWatchMedia(int mediaId, int userId, Date date);

    void deleteWatchedMedia(int mediaId, int userId);

    void deleteToWatchMedia(int mediaId, int userId);

    void updateWatchedMediaDate (int mediaId, int userId, Date date);

    boolean isWatched(int mediaId, int userId);

    boolean isToWatch(int mediaId, int userId);

//    @Deprecated
//    PageContainer<Integer> getWatchedMediaIdIds(int userId, int page, int pageSize);

    PageContainer<WatchedMedia> getWatchedMediaId(int userId, int page, int pageSize);

//    Optional<Integer> getWatchedMediaCount(int userId);

//    @Deprecated
//    PageContainer<Integer> getToWatchMediaIdIds(int userId, int page, int pageSize);

    PageContainer<Media> getToWatchMediaId(int userId, int page, int pageSize);

//    Optional<Integer> getToWatchMediaCount(int userId);
}
