package ar.edu.itba.paw.models.comment;

public class Comment {
    final int commentId;
    final int userId;
    final String username;
    final String commentBody;
    final String listname;
    final int listId;
    final boolean opened;

    public Comment(int commentId, int userId, String username, String commentBody) {
        this.commentId = commentId;
        this.userId = userId;
        this.username = username;
        this.commentBody = commentBody;
        this.listname = "";
        this.listId = 0;
        this.opened = false;
    }

    public Comment(int commentId, int userId, String username, String commentBody, String listname, int listId, boolean opened) {
        this.commentId = commentId;
        this.userId = userId;
        this.username = username;
        this.commentBody = commentBody;
        this.listname = listname;
        this.listId = listId;
        this.opened = opened;
    }

    public String getListname() {
        return listname;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public boolean isOpened() {
        return opened;
    }

    public int getListId() {
        return listId;
    }
}
