package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.FavoriteDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Primary
@Repository
public class FavoriteHibernateDao implements FavoriteDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addMediaToFav(int mediaId, int userId) {

    }

    @Override
    public void deleteMediaFromFav(int mediaId, int userId) {

    }

    @Override
    public boolean isFavorite(int mediaId, int userId) {
        return false;
    }

    @Override
    public PageContainer<Media> getUserFavoriteMedia(int userId, int page, int pageSize) {
        return null;
    }

    @Override
    public Optional<Integer> getFavoriteMediaCount(int userId) {
        return Optional.empty();
    }

    @Override
    public void addListToFav(int mediaListId, int userId) {

    }

    @Override
    public void deleteListFromFav(int mediaListId, int userId) {

    }

    @Override
    public boolean isFavoriteList(int mediaListId, int userId) {
        return false;
    }

    @Override
    public PageContainer<MediaList> getUserFavoriteLists(int userId, int page, int pageSize) {
        return null;
    }

    @Override
    public PageContainer<MediaList> getUserPublicFavoriteLists(int userId, int page, int pageSize) {
        return null;
    }

    @Override
    public Optional<Integer> getFavoriteListsCount(int userId) {
        return Optional.empty();
    }

    @Override
    public PageContainer<MediaList> getRecommendationsBasedOnFavLists(int userId, int page, int pageSize) {
        return null;
    }

    @Override
    public PageContainer<MediaList> getMostLikedLists(int page, int pageSize) {
        return null;
    }

    @Override
    public PageContainer<Media> getRecommendationsBasedOnFavMedia(int mediaType, int userId, int page, int pageSize) {
        return null;
    }

    @Override
    public PageContainer<Media> getMostLikedMedia(int page, int pageSize) {
        return null;
    }

    @Override
    public PageContainer<Media> getMostLikedMedia(int mediaType, int page, int pageSize) {
        return null;
    }

    @Override
    public int getLikesFromList(int listId) {
        return 0;
    }

    @Override
    public int getLikesFromMedia(int mediaId) {
        return 0;
    }

    @Override
    public PageContainer<MediaList> getMostLikedLists(int userid, int page, int pageSize) {
        return null;
    }
}
