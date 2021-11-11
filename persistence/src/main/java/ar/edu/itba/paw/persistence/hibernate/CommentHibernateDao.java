package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.CommentDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.comment.Notification;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.*;

@Primary
@Repository
public class CommentHibernateDao implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public MediaComment addCommentToMedia(User user, Media media, String comment) {
        MediaComment mediaComment = new MediaComment(null, user, comment, LocalDateTime.now(), media);
        em.persist(mediaComment);
        return mediaComment;
    }

    @Override
    public ListComment addCommentToList(User user, MediaList mediaList, String comment) {
        ListComment listComment = new ListComment(null, user, comment, LocalDateTime.now(), mediaList);
        em.persist(listComment);
        return listComment;
    }

    @Override
    public Notification addCommentNotification(ListComment comment) {
        Notification notification = new Notification(null, comment);
        em.persist(notification);
        return notification;
    }

    @Override
    public Optional<MediaComment> getMediaCommentById(int commentId) {
        return Optional.ofNullable(em.find(MediaComment.class, commentId));
    }

    @Override
    public Optional<ListComment> getListCommentById(int commentId) {
        return Optional.ofNullable(em.find(ListComment.class, commentId));
    }

    @Override
    public PageContainer<MediaComment> getMediaComments(Media media, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT commentid FROM mediacomment WHERE mediaid = :mediaId " +
                        "ORDER BY date DESC OFFSET :offset LIMIT :limit")
                .setParameter("mediaId", media.getMediaId())
                .setParameter("offset", page * pageSize)
                .setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> commentIds = nativeQuery.getResultList();

        final Query countQuery = em.createNativeQuery("SELECT COUNT(commentid) FROM mediacomment WHERE mediaid = :mediaId")
                .setParameter("mediaId", media.getMediaId());
        long count = ((Number) countQuery.getSingleResult()).longValue();

        final TypedQuery<MediaComment> query = em.createQuery("FROM MediaComment WHERE commentId IN (:commentIds) ORDER BY creationDate DESC", MediaComment.class)//It works, ignore intellij
                .setParameter("commentIds", commentIds);
        List<MediaComment> mediaComments = commentIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediaComments, page, pageSize, count);
    }

    @Override
    public PageContainer<ListComment> getListComments(MediaList mediaList, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT commentid FROM listcomment WHERE listid = :listId " +
                        "ORDER BY date DESC OFFSET :offset LIMIT :limit")
                .setParameter("listId", mediaList.getMediaListId())
                .setParameter("offset", page * pageSize)
                .setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> commentIds = nativeQuery.getResultList();

        final Query countQuery = em.createNativeQuery("SELECT COUNT(commentid) FROM listcomment WHERE listid = :listId")
                .setParameter("listId", mediaList.getMediaListId());
        long count = ((Number) countQuery.getSingleResult()).longValue();

        final TypedQuery<ListComment> query = em.createQuery("FROM ListComment WHERE commentId IN (:commentIds) ORDER BY creationDate DESC", ListComment.class)//It works, ignore intellij
                .setParameter("commentIds", commentIds);
        List<ListComment> mediaComments = commentIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediaComments, page, pageSize, count);
    }

    @Override
    public void deleteCommentFromMedia(MediaComment comment) {
        em.remove(comment);
    }

    @Override
    public void deleteCommentFromList(ListComment comment) {
        em.remove(comment);
    }

    @Override
    public PageContainer<Notification> getUserListsCommentsNotifications(User user, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT notificationid FROM commentnotifications NATURAL JOIN listcomment " +
                        "WHERE commentid IN (SELECT lc.commentid FROM listcomment lc JOIN medialist ml ON lc.listid = ml.medialistid WHERE ml.userid = :userId)" +
                        "ORDER BY date DESC " +
                        "OFFSET :offset LIMIT :limit")
                .setParameter("userId", user.getUserId())
                .setParameter("offset", page * pageSize)
                .setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> notificationIds = nativeQuery.getResultList();

        final Query countQuery = em.createNativeQuery("SELECT COUNT(notificationid) FROM commentnotifications " +
                        "WHERE commentid IN (SELECT lc.commentid FROM listcomment lc JOIN medialist ml ON lc.listid = ml.medialistid WHERE ml.userid = :userId)")
                .setParameter("userId", user.getUserId());
        long count = ((Number) countQuery.getSingleResult()).longValue();

        final TypedQuery<Notification> query = em.createQuery("FROM Notification WHERE notificationId IN (:notificationIds)", Notification.class);
        query.setParameter("notificationIds", notificationIds);
        List<Notification> notifications = notificationIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(notifications, page, pageSize, count);
    }

    @Override
    public void setUserListsCommentsNotificationsAsOpened(User user) {
        em.createNativeQuery("UPDATE commentnotifications SET opened = :opened " +
                        "WHERE commentid IN " +
                        "(SELECT commentid FROM " +
                        "(commentnotifications NATURAL JOIN listcomment) AS aux JOIN medialist m ON aux.listid = m.medialistid WHERE m.userid = :userId)")
                .setParameter("opened", true)
                .setParameter("userId", user.getUserId())
                .executeUpdate();

    }

    @Override
    public void deleteUserListsCommentsNotifications(User user) {
        em.createNativeQuery("DELETE FROM commentnotifications " +
                        "WHERE commentid IN " +
                        "(SELECT commentid FROM " +
                        "(commentnotifications NATURAL JOIN listcomment) AS aux JOIN medialist m ON aux.listid = m.medialistid WHERE m.userid = :userId)")
                .setParameter("userId", user.getUserId())
                .executeUpdate();
    }
}
