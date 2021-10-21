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

        final Query countQuery = em.createQuery("SELECT COUNT(m.mediaListId) FROM MediaList m WHERE User.userId = :userid")
                .setParameter("userid", user.getUserId());
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

        final Query countQuery = em.createQuery("SELECT COUNT(m.mediaListId) FROM MediaList m WHERE User.userId = :userid  AND m.visible = :visibility")
                .setParameter("userid", user).setParameter("visibility", true);
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
        //SELECT * FROM listelement NATURAL JOIN media WHERE mediaListId = ?
        return em.createQuery("FROM Media m JOIN ListElement le ON m.mediaId = le.mediaId WHERE le.mediaListId = :listId", Media.class)
                .setParameter("listId", mediaList).getResultList();
    }

    @Override
    public PageContainer<Media> getMediaIdInList(MediaList mediaList, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM listelement NATURAL JOIN media WHERE medialistid = :listid OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("listid", mediaList);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        List<Media> list = em.createQuery("FROM Media WHERE mediaId IN (:mediaIds)", Media.class)
                .setParameter("mediaIds", mediaIds).getResultList();
        final Query countQuery = em.createQuery("SELECT COUNT(mediaId) FROM ListElement WHERE mediaListId = :listid")
                .setParameter("listid", mediaList);
        long count = (long) countQuery.getSingleResult();
        return new PageContainer<>(list, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> getLastAddedLists(int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM medialist WHERE visibility = :visibility ORDER BY creationDate DESC OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("visibility", true);
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();
        List<MediaList> list = em.createQuery("FROM MediaList WHERE mediaListId IN (:listIds)", MediaList.class)
                .setParameter("listIds", listIds).getResultList();
        final Query countQuery = em.createQuery("SELECT COUNT(m.mediaListId) FROM MediaList m WHERE m.visible = :visibility")
                .setParameter("visibility", true);
        long count = (long) countQuery.getSingleResult();
        return new PageContainer<>(list, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> getListsIncludingMedia(Media media, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM listelement WHERE mediaid = :mediaid OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("mediaid", media);
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();
        List<MediaList> list = em.createQuery("FROM MediaList WHERE mediaListId IN (:listIds)", MediaList.class)
                .setParameter("listIds", listIds).getResultList();
        final Query countQuery = em.createQuery("SELECT COUNT(m.mediaListId) FROM ListElement m WHERE m.mediaId = :mediaid")
                .setParameter("mediaid", media);
        long count = (long) countQuery.getSingleResult();
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
    public void addToMediaList(int mediaListId, int mediaId) throws MediaAlreadyInListException {
        final ListElement listElement = new ListElement(mediaListId, mediaId);
        em.persist(listElement);
    }

    @Override
    public void addToMediaList(int mediaListId, List<Integer> mediaIdList) throws MediaAlreadyInListException {
        mediaIdList.forEach(mediaId -> em.persist(new ListElement(mediaListId, mediaId)));
    }

    @Override
    public void deleteMediaFromList(int mediaListId, int mediaId) {
        em.remove(new ListElement(mediaListId, mediaId));
    }

    @Override
    public void deleteList(int mediaListId) {
        //TODO
    }

    @Override
    public void updateList(int mediaListId, String title, String description, boolean visibility, boolean collaborative) {
        //TODO
    }

    @Override
    public MediaList createMediaListCopy(int userId, int toCopyListId) {
        final MediaList toCopy = em.find(MediaList.class, toCopyListId);
        MediaList fork = new MediaList(userId, toCopy.getListName(), toCopy.getDescription(), toCopy.isVisible(), toCopy.isCollaborative());
        em.persist(fork);
        List<ListElement> toCopyMedia = em.createQuery("FROM ListElement WHERE mediaListId = :toCopyId", ListElement.class).setParameter("toCopyId", toCopyListId).getResultList();
        for (ListElement elem : toCopyMedia) {
            em.persist(new ListElement(fork.getMediaListId(), elem.getMediaId()));
        }
        return fork;
    }

    @Override
    public Optional<User> getListOwner(int listId) {
        final MediaList list = em.find(MediaList.class, listId);
        return Optional.of(em.find(User.class, list.getUserId()));
    }

    @Override
    public boolean canEditList(int userId, int listId) {
        return !em.createNativeQuery("SELECT COUNT(*) FROM medialist ml LEFT JOIN collaborative c on ml.medialistid = c.listid WHERE medialistid = :medialistid AND ((userid = :userid) OR (collaboratorid = :userid AND accepted = :accepted))").setParameter("userid", userId).setParameter("accepted", true).setParameter("medialistid", listId).getSingleResult()
                .equals(0);
    }

    @Override
    public PageContainer<MediaList> getUserEditableLists(int userId, int page, int pageSize) {
        return null;
    }

    @Override
    public PageContainer<MediaList> getListForks(int listId, int page, int pageSize) {
        return null;
    }

    @Override
    public Optional<MediaList> getForkedFrom(int listId) {
        return Optional.empty();
    }
}
