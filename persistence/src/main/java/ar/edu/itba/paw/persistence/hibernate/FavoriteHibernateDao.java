package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.FavoriteDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Primary
@Repository
public class FavoriteHibernateDao implements FavoriteDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addMediaToFav(Media media, User user) {
        em.createNativeQuery("INSERT INTO favoritemedia (userid, mediaid) VALUES (:userId, :mediaId)")
                .setParameter("mediaId", media.getMediaId())
                .setParameter("userId", user.getUserId())
                .executeUpdate();
    }

    @Override
    public void deleteMediaFromFav(Media media, User user) {
        em.createNativeQuery("DELETE FROM favoritemedia WHERE userid = :userId AND mediaid = :mediaId")
                .setParameter("mediaId", media.getMediaId())
                .setParameter("userId", user.getUserId())
                .executeUpdate();
    }

    @Override
    public boolean isFavorite(Media media, User user) {
        return !em.createNativeQuery("SELECT COUNT(*) FROM favoritemedia WHERE mediaid = :mediaId AND userid = :userId")
                .setParameter("mediaId", media.getMediaId()).setParameter("userId", user.getUserId()).getSingleResult().equals(0);
    }

    @Override
    public PageContainer<Media> getUserFavoriteMedia(User user, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM favoritemedia WHERE userId = :userId OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("userId", user.getUserId());
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("SELECT COUNT(mediaid) FROM favoritemedia WHERE userId = :userId");
        countQuery.setParameter("userId", user.getUserId());
        return getMediaPageContainer(page, pageSize, mediaIds, countQuery);
    }

    @Override
    public void addListToFav(MediaList mediaList, User user) {
        em.createNativeQuery("INSERT INTO favoritelists (userid, medialistid) VALUES (:userId, :mediaListId)")
                .setParameter("mediaListId", mediaList.getMediaListId())
                .setParameter("userId", user.getUserId())
                .executeUpdate();
    }

    @Override
    public void deleteListFromFav(MediaList mediaList, User user) {
        em.createNativeQuery("DELETE FROM favoritelists WHERE userid = :userId AND medialistid = :mediaListId")
                .setParameter("mediaListId", mediaList.getMediaListId())
                .setParameter("userId", user.getUserId())
                .executeUpdate();
    }

    @Override
    public boolean isFavoriteList(MediaList mediaList, User user) {
        return !em.createNativeQuery("SELECT COUNT(*) FROM favoritelists WHERE medialistid = :mediaListId AND userid = :userId")
                .setParameter("mediaListId", mediaList.getMediaListId()).setParameter("userId", user.getUserId()).getSingleResult().equals(0);
    }

    @Override
    public PageContainer<MediaList> getUserFavoriteLists(User user, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM favoritelists WHERE userId = :userId OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("userId", user.getUserId());
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("SELECT medialistid FROM favoritelists WHERE userId = :userId");
        countQuery.setParameter("userId", user.getUserId());
        return getMediaListPageContainer(page, pageSize, listIds, countQuery);
    }

    @Override
    public PageContainer<MediaList> getUserPublicFavoriteLists(User user, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM favoritelists NATURAL JOIN medialist WHERE userId = :userId AND visibility = :visibility OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("userId", user.getUserId());
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("visibility", true);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("SELECT medialistid FROM favoritelists NATURAL JOIN medialist WHERE userId = :userId AND visibility = :visibility");
        countQuery.setParameter("userId", user.getUserId()).setParameter("visibility", true);
        return getMediaListPageContainer(page, pageSize, listIds, countQuery);
    }

    @Override
    public PageContainer<MediaList> getRecommendationsBasedOnFavLists(User user, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("((SELECT medialistid FROM medialist NATURAL JOIN (SELECT medialistid FROM favoritelists WHERE userid IN (SELECT l.userid FROM favoritelists f JOIN favoritelists l ON f.medialistid = l.medialistid WHERE f.userid = :userId) EXCEPT SELECT m.medialistid FROM medialist m RIGHT JOIN favoritelists f ON m.userid=f.userid WHERE f.userid = :userId) as AUX) OFFSET :offset LIMIT :limit)");
        nativeQuery.setParameter("userId", user.getUserId());
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("SELECT COUNT(medialistid) FROM (medialist NATURAL JOIN (SELECT medialistid FROM favoritelists WHERE userid IN (SELECT l.userid FROM favoritelists f JOIN favoritelists l ON f.medialistid = l.medialistid WHERE f.userid = :userId) EXCEPT SELECT medialistId FROM favoritelists WHERE userid = :userId) as AUX)");
        countQuery.setParameter("userId", user.getUserId());
        return getMediaListPageContainer(page, pageSize, listIds, countQuery);
    }

    @Override
    public PageContainer<MediaList> getMostLikedLists(int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT medialist.medialistid FROM medialist LEFT JOIN favoritelists ON medialist.medialistid = favoritelists.medialistid WHERE visibility = :visibility GROUP BY medialist.medialistid ORDER BY COUNT(favoritelists.userid) DESC OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("visibility", true);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("SELECT COUNT(medialist.medialistid) FROM medialist LEFT JOIN favoritelists ON medialist.medialistid = favoritelists.medialistid WHERE visibility = :visibility GROUP BY medialist.medialistid");
        countQuery.setParameter("visibility", true);
        return getMediaListPageContainer(page, pageSize, listIds, countQuery);
    }

    @Override
    public PageContainer<Media> getRecommendationsBasedOnFavMedia(MediaType mediaType, User user, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("(SELECT mediaid FROM media NATURAL JOIN (SELECT mediaid FROM favoritemedia WHERE userid IN (SELECT m.userid FROM favoritemedia f JOIN favoritemedia m ON f.mediaid = m.mediaid WHERE f.userid = :userId) EXCEPT SELECT mediaId FROM favoritemedia WHERE userid = :userId) as AUX WHERE type = :mediaType) OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("userId", user.getUserId());
        nativeQuery.setParameter("mediaType", mediaType.ordinal());
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("offset", page * pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("(SELECT mediaid FROM media NATURAL JOIN (SELECT mediaid FROM favoritemedia WHERE userid IN (SELECT m.userid FROM favoritemedia f JOIN favoritemedia m ON f.mediaid = m.mediaid WHERE f.userid = :userId) EXCEPT SELECT mediaId FROM favoritemedia WHERE userid = :userId) as AUX WHERE type = :mediaType)");
        countQuery.setParameter("userId", user.getUserId());
        countQuery.setParameter("mediaType", mediaType.ordinal());
        return getMediaPageContainer(page, pageSize, mediaIds, countQuery);
    }

    @Override
    public PageContainer<Media> getMostLikedMedia(int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT media.mediaid FROM media LEFT JOIN favoritemedia ON media.mediaId = favoritemedia.mediaId GROUP BY media.mediaid ORDER BY COUNT(favoritemedia.userid) DESC OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("SELECT COUNT(mediaid) AS count FROM media");
        return getMediaPageContainer(page, pageSize, mediaIds, countQuery);
    }

    @Override
    public PageContainer<Media> getMostLikedMedia(MediaType mediaType, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT media.mediaid FROM media LEFT JOIN favoritemedia ON media.mediaId = favoritemedia.mediaId WHERE type = :mediaType GROUP BY media.mediaid ORDER BY COUNT(favoritemedia.userid) DESC OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("mediaType", mediaType.ordinal());
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("SELECT COUNT(mediaid) AS count FROM media WHERE type = :mediaType");
        countQuery.setParameter("mediaType", mediaType.ordinal());
        return getMediaPageContainer(page, pageSize, mediaIds, countQuery);
    }

    @Override
    public int getLikesFromList(MediaList mediaList) {
        return ((Number) em.createNativeQuery("SELECT COUNT(userid) FROM favoritelists WHERE medialistid = :mediaListId").setParameter("mediaListId", mediaList.getMediaListId()).getSingleResult()).intValue();
    }

    @Override
    public int getLikesFromMedia(Media media) {
        return ((Number) em.createNativeQuery("SELECT COUNT(userid) FROM favoritemedia WHERE mediaid = :mediaId").setParameter("mediaId", media.getMediaId()).getSingleResult()).intValue();
    }

    @Override
    public PageContainer<MediaList> getMostLikedLists(User user, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT medialist.* FROM medialist LEFT JOIN favoritelists ON medialist.medialistid = favoritelists.medialistid WHERE visibility = :visibility AND medialist.userid != :userId GROUP BY medialist.medialistid ORDER BY COUNT(favoritelists.userid) DESC OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("visibility", true);
        nativeQuery.setParameter("userId", user.getUserId());
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("SELECT COUNT(medialistid) FROM medialist WHERE visibility = :visibility AND userid != :userId");
        countQuery.setParameter("visibility", true);
        countQuery.setParameter("userId", user.getUserId());
        return getMediaListPageContainer(page, pageSize, listIds, countQuery);
    }

    private PageContainer<Media> getMediaPageContainer(int page, int pageSize, List<Long> mediaIds, Query countQuery) {
        long count = ((Number) countQuery.getSingleResult()).longValue();

        final TypedQuery<Media> typedQuery = em.createQuery("FROM Media WHERE mediaId IN (:mediaIds)", Media.class)
                .setParameter("mediaIds", mediaIds);
        List<Media> mediaList = mediaIds.isEmpty() ? new ArrayList<>() : typedQuery.getResultList();
        return new PageContainer<>(mediaList, page, pageSize, count);
    }

    private PageContainer<MediaList> getMediaListPageContainer(int page, int pageSize, List<Long> listIds, Query countQuery) {
        long count = ((Number) countQuery.getSingleResult()).longValue();

        final TypedQuery<MediaList> typedQuery = em.createQuery("FROM MediaList WHERE mediaListId IN (:listIds)", MediaList.class)
                .setParameter("listIds", listIds);
        List<MediaList> mediaList = listIds.isEmpty() ? new ArrayList<>() : typedQuery.getResultList();
        return new PageContainer<>(mediaList, page, pageSize, count);
    }
}
