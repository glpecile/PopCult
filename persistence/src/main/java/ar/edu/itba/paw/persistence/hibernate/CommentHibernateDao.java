package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.CommentDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class CommentHibernateDao implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public MediaComment addCommentToMedia(User user, Media media, String comment) {
        MediaComment mediaComment = new MediaComment(null, user, comment, new Date(), media);
        em.persist(mediaComment);
        return mediaComment;
    }

    @Override
    public ListComment addCommentToList(User user, MediaList mediaList, String comment) {
        ListComment listComment = new ListComment(null, user, comment, new Date(), mediaList);
        em.persist(listComment);
        return listComment;
    }

    @Override
    public void addCommentNotification(ListComment comment) {

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
                        "OFFSET :offset LIMIT :limit")
                .setParameter("mediaId", media.getMediaId())
                .setParameter("offset", page * pageSize)
                .setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> commentIds = nativeQuery.getResultList();

        final Query countQuery = em.createNativeQuery("SELECT COUNT(commentid) FROM mediacomment WHERE mediaid = :mediaId")
                .setParameter("mediaId", media.getMediaId());
        long count = ((Number) countQuery.getSingleResult()).longValue();

        final TypedQuery<MediaComment> query = em.createQuery("FROM MediaComment c WHERE c.commentId = :commentId", MediaComment.class);

    }

    @Override
    public PageContainer<ListComment> getListComments(MediaList mediaList, int page, int pageSize) {
        return null;
    }

    @Override
    public void deleteCommentFromMedia(MediaComment comment) {

    }

    @Override
    public void deleteCommentFromList(ListComment comment) {

    }

    @Override
    public PageContainer<ListComment> getUserListsCommentsNotifications(User user, int page, int pageSize) {
        return null;
    }

    @Override
    public void setUserListsCommentsNotificationsAsOpened(User user) {

    }

    @Override
    public void deleteUserListsCommentsNotifications(User user) {

    }
}
