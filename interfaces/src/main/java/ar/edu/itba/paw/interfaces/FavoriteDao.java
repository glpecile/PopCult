package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;

import java.util.List;
import java.util.Optional;

public interface FavoriteDao {
    void addMediaToFav(int mediaId, int userId);

    void deleteMediaFromFav(int mediaId, int userId);

    boolean isFavorite(int mediaId, int userId);

    PageContainer<Media> getUserFavoriteMedia(int userId, int page, int pageSize);

    Optional<Integer> getFavoriteMediaCount(int userId);

    void addListToFav(int mediaListId, int userId);

    void deleteListFromFav(int mediaListId, int userId);

    boolean isFavoriteList(int mediaListId, int userId);

    PageContainer<MediaList> getUserFavoriteLists(int userId, int page, int pageSize);

    PageContainer<MediaList> getUserPublicFavoriteLists(int userId, int page, int pageSize);

    Optional<Integer> getFavoriteListsCount(int userId);

    PageContainer<MediaList> getRecommendationsBasedOnFavLists(int userId, int page, int pageSize);

    PageContainer<MediaList> getRecommendationsBasedOnFavMedia(int userId, int page, int pageSize);
}
