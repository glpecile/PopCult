package ar.edu.itba.paw.models.report;

import java.util.Date;

public class CommentReport extends Report {
    private final int commentId;
    private final int userId;
    private final String commentBody;

    public CommentReport(String report, Date date, int commentId, int userId, String commentBody) {
        super(report, date);
        this.commentId = commentId;
        this.userId = userId;
        this.commentBody = commentBody;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCommentBody() {
        return commentBody;
    }
}
