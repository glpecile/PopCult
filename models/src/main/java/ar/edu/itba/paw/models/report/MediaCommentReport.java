package ar.edu.itba.paw.models.report;

import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mediacommentreport")
public class MediaCommentReport extends Report {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "commentid")
    private MediaComment comment;

    /* default */ MediaCommentReport() {

    }

    public MediaCommentReport(Integer reportId, User reportee, String report, Date date, MediaComment comment) {
        super(reportId, reportee, report, date);
        this.comment = comment;
    }

    public MediaComment getComment() {
        return comment;
    }

    public void setComment(MediaComment comment) {
        this.comment = comment;
    }
}
