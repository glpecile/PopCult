package ar.edu.itba.paw.interfaces;

import java.util.List;
import java.util.Optional;

public interface FavoriteDao {
    void addMediaToFav(int mediaId, int userId);

    void deleteMediaFromFav(int mediaId, int userId);

    boolean isFavorite(int mediaId, int userId);

    List<Integer> getUserFavoriteMedia(int userId, int page, int pageSize);

    Optional<Integer> getFavoriteMediaCount(int userId);

    void addListToFav(int userId, int mediaListId);

    void deleteListFromFav(int userId, int mediaListId);

    boolean isFavoriteList(int userId, int mediaListId);

    List<Integer> getUserFavoriteLists(int userId, int page, int pageSize);

    Optional<Integer> getFavoriteListsCount(int userId);
}
