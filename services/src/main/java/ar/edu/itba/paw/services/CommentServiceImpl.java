package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CommentDao;
import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.comment.Notification;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDao commentDao;
    @Autowired
    UserService userService;

    @Transactional
    @Override
    public MediaComment addCommentToMedia(User user, Media media, String comment) {
        return commentDao.addCommentToMedia(user, media, comment);
    }

    @Transactional
    @Override
    public ListComment addCommentToList(User user, MediaList mediaList, String comment) {
        ListComment com = commentDao.addCommentToList(user, mediaList, comment);
        commentDao.addCommentNotification(com);
        return com;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<MediaComment> getMediaCommentById(int commentId) {
        return commentDao.getMediaCommentById(commentId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ListComment> getListCommentById(int commentId) {
        return commentDao.getListCommentById(commentId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaComment> getMediaComments(Media media, int page, int pageSize) {
        return commentDao.getMediaComments(media, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<ListComment> getListComments(MediaList mediaList, int page, int pageSize) {
        return commentDao.getListComments(mediaList, page, pageSize);
    }

    @Transactional
    @Override
    public void deleteCommentFromMedia(MediaComment comment) {
        commentDao.deleteCommentFromMedia(comment);
    }

    @Transactional
    @Override
    public void deleteCommentFromList(ListComment comment) {
        commentDao.deleteCommentFromList(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Notification> getUserListsCommentsNotifications(User user, int page, int pageSize) {
        return commentDao.getUserListsCommentsNotifications(user, page, pageSize);
    }

    @Transactional
    @Override
    public void setUserListsCommentsNotificationsAsOpened(User user) {
        commentDao.setUserListsCommentsNotificationsAsOpened(user);
    }

    @Transactional
    @Override
    public void deleteUserListsCommentsNotifications(User user) {
        commentDao.deleteUserListsCommentsNotifications(user);
    }
}
