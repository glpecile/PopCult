package ar.edu.itba.paw.models.comment;

public class Comment {
    final int commentId;
    final int userId;
    final String username;
    final String commentBody;

    public Comment(int commentId, int userId, String username, String commentBody) {
        this.commentId = commentId;
        this.userId = userId;
        this.username = username;
        this.commentBody = commentBody;
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
}
