package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.Comment;

import java.util.Optional;

public interface CommentDao {

    Comment addCommentToMedia(int userId, int mediaId, String comment);

    Comment addCommentToList(int userId, int listId, String comment);

    PageContainer<Comment> getMediaComments(int mediaId, int page, int pageSize);

    PageContainer<Comment> getListComments(int listId, int page, int pageSize);

    void deleteCommentFromList(int commentId);

}
