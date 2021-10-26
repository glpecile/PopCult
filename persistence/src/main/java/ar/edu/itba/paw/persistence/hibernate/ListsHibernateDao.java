package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.ListsDao;
import ar.edu.itba.paw.interfaces.exceptions.MediaAlreadyInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class ListsHibernateDao implements ListsDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListsHibernateDao.class);

    @Override
    public Optional<MediaList> getMediaListById(int mediaListId) {
        return Optional.ofNullable(em.find(MediaList.class, mediaListId));
    }

    @Override
    public PageContainer<MediaList> getAllLists(int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM medialist OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(mediaListId) FROM MediaList");
        long count = (long) countQuery.getSingleResult();

        List<MediaList> list = getMediaLists(listIds);

        return new PageContainer<>(list, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> getMediaListByUser(User user, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM medialist WHERE userid = :userid OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("userid", user.getUserId());
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(m.mediaListId) FROM MediaList m WHERE user = :user")
                .setParameter("user", user);
        long count = (long) countQuery.getSingleResult();

        List<MediaList> list = getMediaLists(listIds);

        return new PageContainer<>(list, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> getPublicMediaListByUser(User user, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM medialist WHERE userid = :userid AND visibility = :visibility OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("userid", user);
        nativeQuery.setParameter("visibility", true);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(m.mediaListId) FROM MediaList m WHERE user = :user  AND m.visible = :visibility")
                .setParameter("user", user).setParameter("visibility", true);
        long count = (long) countQuery.getSingleResult();

        List<MediaList> list = getMediaLists(listIds);

        return new PageContainer<>(list, page, pageSize, count);
    }

    private List<MediaList> getMediaLists(List<Long> listIds) {
        final TypedQuery<MediaList> query = em.createQuery("FROM MediaList WHERE mediaListId IN (:listIds)", MediaList.class)
                .setParameter("listIds", listIds);
        return listIds.isEmpty() ? new ArrayList<>() : query.getResultList();
    }

    @Override
    public List<Media> getMediaIdInList(MediaList mediaList) {
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM listelement WHERE medialistid = :mediaListId")
                .setParameter("mediaListId", mediaList.getMediaListId());
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();

        return getMedias(mediaIds);

    }

    @Override
    public PageContainer<Media> getMediaIdInList(MediaList mediaList, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM listelement WHERE medialistid = :mediaListId OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("mediaListId", mediaList.getMediaListId());
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();

        final Query countQuery = em.createNativeQuery("SELECT COUNT(mediaid) FROM listelement WHERE medialistid = :mediaListId")
                .setParameter("mediaListId", mediaList.getMediaListId());
        long count = ((Number) countQuery.getSingleResult()).longValue();

        return new PageContainer<>(getMedias(mediaIds), page, pageSize, count);
    }

    private List<Media> getMedias(List<Long> mediaIds) {
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in :mediaIds", Media.class);
        query.setParameter("mediaIds", mediaIds);
        return mediaIds.isEmpty() ? new ArrayList<>() : query.getResultList();
    }

    @Override
    public PageContainer<MediaList> getLastAddedLists(int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM medialist WHERE visibility = :visibility ORDER BY creationDate DESC OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("visibility", true);
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(mediaListId) FROM MediaList WHERE visible = :visibility")
                .setParameter("visibility", true);
        long count = (long) countQuery.getSingleResult();

        List<MediaList> list = getMediaLists(listIds);

        return new PageContainer<>(list, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> getListsIncludingMedia(Media media, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM listelement WHERE mediaid = :mediaid OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("mediaid", media.getMediaId());
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();

        final Query countQuery = em.createNativeQuery("SELECT COUNT(mediaListId) FROM listelement WHERE mediaid = :mediaId")
                .setParameter("mediaId", media.getMediaId());
        long count = ((Number) countQuery.getSingleResult()).longValue();

        List<MediaList> list = getMediaLists(listIds);

        return new PageContainer<>(list, page, pageSize, count);
    }

    @Override
    public List<MediaList> getListsContainingGenre(Genre genre, int pageSize, int minMatches) {
        //todo lo moveria a genreDAO
        return null;
    }

    @Override
    public MediaList createMediaList(User user, String title, String description, boolean visibility, boolean collaborative) {
        final MediaList mediaList = new MediaList(user, title, description, visibility, collaborative);
        em.persist(mediaList);
        return mediaList;
    }

    @Override
    public void addToMediaList(MediaList mediaList, Media media) throws MediaAlreadyInListException {
        em.createNativeQuery("INSERT INTO listelement (mediaid, medialistid) VALUES (:mediaId, :mediaListId)")
                .setParameter("mediaId", media.getMediaId())
                .setParameter("mediaListId", mediaList.getMediaListId())
                .executeUpdate();
    }

    @Override
    public void addToMediaList(MediaList mediaList, List<Media> medias) throws MediaAlreadyInListException {
        for (Media media : medias) {
            addToMediaList(mediaList, media);
        }
    }

    @Override
    public void deleteMediaFromList(MediaList mediaList, Media media) {
        em.createNativeQuery("DELETE FROM listelement WHERE medialistid = :mediaListId AND mediaid = :mediaId")
                .setParameter("mediaId", media.getMediaId())
                .setParameter("mediaListId", mediaList.getMediaListId())
                .executeUpdate();
    }

    @Override
    public void deleteList(MediaList mediaList) {
        em.remove(mediaList);
    }

    @Override
    public MediaList createMediaListCopy(User user, MediaList toCopy) {
        MediaList fork = new MediaList(user, "Copy from " + toCopy.getListName(), toCopy.getDescription(), toCopy.getVisible(), toCopy.getCollaborative());
        em.persist(fork);
        @SuppressWarnings("unchecked")
        List<Long> toCopyMediaIds = em.createNativeQuery("SELECT mediaid FROM listelement WHERE medialistid = :toCopyId")
                .setParameter("toCopyId", toCopy)
                .getResultList();
        List<Media> toCopyMedia = getMedias(toCopyMediaIds);
        try {
            addToMediaList(fork, toCopyMedia);
        } catch (MediaAlreadyInListException e) {
            LOGGER.error("Media already exists in MediaList");
        }
        return fork;
    }

    @Override
    public boolean canEditList(User user, MediaList mediaList) {
        return !(((Number)em.createNativeQuery("SELECT COUNT(*) FROM medialist ml LEFT JOIN collaborative c on ml.medialistid = c.listid WHERE medialistid = :medialistid AND ((userid = :userid) OR " +
                        "(collaboratorid = :userid AND accepted = :accepted))")
                .setParameter("userid", user.getUserId())
                .setParameter("accepted", true)
                .setParameter("medialistid", mediaList.getMediaListId())
                .getSingleResult())
                .intValue() == 0);
    }

    @Override
    public PageContainer<MediaList> getUserEditableLists(User user, int page, int pageSize) {
        @SuppressWarnings("unchecked")
        List<Long> listIds = em.createNativeQuery("(SELECT medialistid FROM medialist WHERE userid = :userId) UNION " +
                        "(SELECT m.medialistid FROM collaborative c JOIN medialist m on c.listid = m.medialistid WHERE collaboratorid = :userId AND accepted = :accepted) " +
                        "OFFSET :offset LIMIT :limit")
                .setParameter("userId", user.getUserId())
                .setParameter("accepted", true)
                .setParameter("offset", page * pageSize)
                .setParameter("limit", pageSize)
                .getResultList();

        long count = ((Number) em.createNativeQuery("SELECT COUNT(*) FROM ((SELECT * FROM medialist WHERE userid = :userId) UNION " +
                        "(SELECT m.* FROM collaborative c JOIN medialist m on c.listid = m.medialistid WHERE collaboratorid = :userId AND accepted = :accepted)) AS aux")
                .setParameter("userId", user.getUserId())
                .setParameter("accepted", true)
                .getSingleResult()).longValue();

        return new PageContainer<>(getMediaLists(listIds), page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> getListForks(MediaList mediaList, int page, int pageSize) {
        @SuppressWarnings("unchecked")
        List<Long> listIds = em.createNativeQuery("SELECT m.medialistid FROM forkedlists f JOIN medialist m ON f.forkedlistid = m.medialistid " +
                        "WHERE f.originalistid = :mediaListId AND m.visibility = :visibility " +
                        "OFFSET :offset LIMIT :limit")
                .setParameter("mediaListId", mediaList.getMediaListId())
                .setParameter("visibility", true)
                .setParameter("offset", page * pageSize)
                .setParameter("limit", pageSize)
                .getResultList();

        long count = ((Number) em.createNativeQuery("SELECT COUNT(m.medialistid) FROM forkedlists f JOIN medialist m ON f.forkedlistid = m.medialistid " +
                        "WHERE f.originalistid = :mediaListId AND m.visibility = :visibility")
                .setParameter("mediaListId", mediaList.getMediaListId())
                .setParameter("visibility", true)
                .getSingleResult()).longValue();

        return new PageContainer<>(getMediaLists(listIds), page, pageSize, count);
    }

    @Override
    public Optional<MediaList> getForkedFrom(MediaList mediaList) {
        //        return jdbcTemplate.query("SELECT * FROM medialist JOIN forkedlists ON medialist.medialistid = forkedlists.originalistid WHERE forkedlistid = ? AND visibility = ?", new Object[]{listId, true}, MEDIA_LIST_ROW_MAPPER).stream().findFirst();
        @SuppressWarnings("unchecked")
        int listId = ((Number) em.createNativeQuery("SELECT medialistid FROM medialist JOIN forkedlists ON medialist.medialistid = forkedlists.originalistid " +
                        "WHERE forkedlistid = :mediaListId AND visibility = :visibility")
                .setParameter("mediaListId", mediaList.getMediaListId())
                .setParameter("visibility", true)
                .getSingleResult()).intValue();

        return getMediaListById(listId);
    }
}
