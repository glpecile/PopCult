package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.media.Media;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WatchService {
    void addWatchedMedia(int mediaId, int userId);

    void addMediaToWatch(int mediaId, int userId);

    void deleteWatchedMedia(int mediaId, int userId);

    void deleteToWatchMedia(int mediaId, int userId);

    boolean isWatched(int mediaId, int userId);

    boolean isToWatch(int mediaId, int userId);

    List<Integer> getWatchedMediaId(int userId, int page, int pageSize);

    Optional<Integer> getWatchedMediaCount(int userId);

    List<Integer> getToWatchMediaId(int userId, int page, int pageSize);

    Optional<Integer> getToWatchMediaCount(int userId);
}
