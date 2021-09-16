package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.media.Media;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WatchDao {
    void addWatchMedia(int mediaId, int userId, Date date);

    void deleteWatchMedia(int mediaId, int userId, Date date);

    boolean isWatched(int mediaId, int userId);

    boolean isToWatch(int mediaId, int userId);

    List<Integer> getWatchedMediaId(int userId, int page, int pageSize);

    Optional<Integer> getWatchedMediaCount(int userId);

    List<Integer> getToWatchMediaId(int userId, int page, int pageSize);

    Optional<Integer> getToWatchMediaCount(int userId);
}
