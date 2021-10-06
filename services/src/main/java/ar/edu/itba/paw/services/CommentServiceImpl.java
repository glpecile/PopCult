package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CommentDao;
import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.Comment;
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
    public Comment addCommentToMedia(int userId, int mediaId, String comment) {
        return commentDao.addCommentToMedia(userId, mediaId, comment);
    }

    @Transactional
    @Override
    public Comment addCommentToList(int userId, int listId, String comment) {
        Comment com = commentDao.addCommentToList(userId, listId, comment);
        userService.getCurrentUser().ifPresent(user -> {
            if (com != null) {
                commentDao.addCommentNotification(com.getCommentId());
            }
        });
        return com;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> getMediaCommentById(int commentId) {
        return commentDao.getMediaCommentById(commentId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> getListCommentById(int commentId) {
        return commentDao.getListCommentById(commentId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Comment> getMediaComments(int mediaId, int page, int pageSize) {
        return commentDao.getMediaComments(mediaId, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Comment> getListComments(int listId, int page, int pageSize) {
        return commentDao.getListComments(listId, page, pageSize);
    }

    @Transactional
    @Override
    public void deleteCommentFromList(int commentId) {
        commentDao.deleteCommentFromList(commentId);
    }

    @Transactional
    @Override
    public void deleteCommentFromMedia(int commentId) {
        commentDao.deleteCommentFromMedia(commentId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Comment> getUserListsCommentsNotifications(int userId, int page, int pageSize) {
        return commentDao.getUserListsCommentsNotifications(userId, page, pageSize);
    }

    @Transactional
    @Override
    public void setUserListsCommentsNotificationsAsOpened(int userId) {
        commentDao.setUserListsCommentsNotificationsAsOpened(userId);
    }

    @Transactional
    @Override
    public void deleteUserListsCommentsNotifications(int userId) {
        commentDao.deleteUserListsCommentsNotifications(userId);
    }
}
