package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;

import java.util.List;
import java.util.Optional;

public interface FavoriteService {
    void addMediaToFav(int mediaId, int userId);

    void deleteMediaFromFav(int mediaId, int userId);

    boolean isFavorite(int mediaId, int userId);
    @Deprecated
    PageContainer<Integer> getUserFavoriteMediaIds(int userId, int page, int pageSize);

    PageContainer<Media> getUserFavoriteMedia(int userId, int page, int pageSize);

    Optional<Integer> getFavoriteMediaCount(int userId);

    void addListToFav(int mediaListId, int userId);

    void deleteListFromFav(int mediaListId, int userId);

    boolean isFavoriteList(int mediaListId, int userId);

    PageContainer<Integer> getUserFavoriteLists(int userId, int page, int pageSize);

    Optional<Integer> getFavoriteListsCount(int userId);
}
