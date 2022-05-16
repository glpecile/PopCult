package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.comment.Notification;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;

import java.util.Optional;

public interface CommentDao {

    MediaComment addCommentToMedia(User user, Media media, String comment);

    ListComment addCommentToList(User user, MediaList mediaList, String comment);

    Notification addCommentNotification(ListComment comment);

    Optional<MediaComment> getMediaCommentById(int commentId);

    Optional<ListComment> getListCommentById(int commentId);

    PageContainer<MediaComment> getMediaComments(Media media, int page, int pageSize);

    PageContainer<ListComment> getListComments(MediaList mediaList, int page, int pageSize);

    void deleteCommentFromMedia(MediaComment comment);

    void deleteCommentFromList(ListComment comment);

    Optional<Notification> getListCommentNotification(int notificationId);

    void deleteListCommentNotification(Notification notification);

    PageContainer<Notification> getUserListsCommentsNotifications(User user, int page, int pageSize);

}
