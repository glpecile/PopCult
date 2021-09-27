package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CommentDao;
import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDao commentDao;

    @Override
    public Comment addCommentToMedia(int userId, int mediaId, String comment) {
        return commentDao.addCommentToMedia(userId, mediaId, comment);
    }

    @Override
    public Comment addCommentToList(int userId, int listId, String comment) {
        return commentDao.addCommentToList(userId, listId, comment);
    }

    @Override
    public PageContainer<Comment> getMediaComments(int mediaId, int page, int pageSize) {
        return commentDao.getMediaComments(mediaId, page, pageSize);
    }

    @Override
    public PageContainer<Comment> getListComments(int listId, int page, int pageSize) {
        return commentDao.getListComments(listId, page, pageSize);
    }

    @Override
    public void deleteCommentFromList(int commentId) {
        commentDao.deleteCommentFromList(commentId);
    }
}
