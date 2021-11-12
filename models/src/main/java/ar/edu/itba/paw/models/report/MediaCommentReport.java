package ar.edu.itba.paw.models.report;

import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "mediacommentreport")
public class MediaCommentReport extends Report {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mediacommentreport_reportid_seq")
    @SequenceGenerator(sequenceName = "mediacommentreport_reportid_seq", name = "mediacommentreport_reportid_seq", allocationSize = 1)
    @Column(name = "reportid")
    private Integer reportId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "commentid")
    private MediaComment comment;

    /* default */ MediaCommentReport() {

    }

    public MediaCommentReport(Integer reportId, User reportee, String report, LocalDateTime date, MediaComment comment) {
        super(reportee, report, date);
        this.reportId = reportId;
        this.comment = comment;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public MediaComment getComment() {
        return comment;
    }

    public void setComment(MediaComment comment) {
        this.comment = comment;
    }
}
