package ar.edu.itba.paw.models.report;

import java.util.Date;

public class ListCommentReport extends Report {
    private final int commentId;
    private final int mediaListId;
    private final int userId;
    private final String commentBody;

    public ListCommentReport(int reportId, String report, Date date, int commentId, int mediaListId, int userId, String commentBody) {
        super(reportId, report, date);
        this.commentId = commentId;
        this.mediaListId = mediaListId;
        this.userId = userId;
        this.commentBody = commentBody;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getMediaListId() {
        return mediaListId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCommentBody() {
        return commentBody;
    }
}
