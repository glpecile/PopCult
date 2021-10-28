package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.user.User;

public interface FavoriteService {
    void addMediaToFav(Media media, User user);

    void deleteMediaFromFav(Media media, User user);

    boolean isFavorite(Media media, User user);

    PageContainer<Media> getUserFavoriteMedia(User user, int page, int pageSize);

    void addListToFav(MediaList mediaList, User user);

    void deleteListFromFav(MediaList mediaList, User user);

    boolean isFavoriteList(MediaList mediaList, User user);

    PageContainer<MediaList> getUserFavoriteLists(User user, int page, int pageSize);

    PageContainer<MediaList> getUserPublicFavoriteLists(User user, int page, int pageSize);

    PageContainer<MediaList> getRecommendationsBasedOnFavLists(User user, int page, int pageSize);

    PageContainer<MediaList> getMostLikedLists(int page, int pageSize);

    PageContainer<Media> getRecommendationsBasedOnFavMedia(MediaType mediaType, User user, int page, int pageSize);

    PageContainer<Media> getMostLikedMedia(int page, int pageSize);

    PageContainer<Media> getMostLikedMedia(MediaType mediaType, int page, int pageSize);

    int getLikesFromList(MediaList mediaList);

    int getLikesFromMedia(Media media);
}
