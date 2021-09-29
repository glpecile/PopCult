package ar.edu.itba.paw.models.report;

import java.util.Date;

public class MediaCommentReport extends Report {
    private final int commentId;
    private final int mediaId;
    private final int userId;
    private final String commentBody;

    public MediaCommentReport(int reportId, String report, Date date, int commentId, int mediaId, int userId, String commentBody) {
        super(reportId, report, date);
        this.commentId = commentId;
        this.mediaId = mediaId;
        this.userId = userId;
        this.commentBody = commentBody;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCommentBody() {
        return commentBody;
    }
}
