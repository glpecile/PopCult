package ar.edu.itba.paw.models.report;

import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "listcommentreport")
public class ListCommentReport extends Report {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listcommentreport_reportid_seq")
    @SequenceGenerator(sequenceName = "listcommentreport_reportid_seq", name = "listcommentreport_reportid_seq", allocationSize = 1)
    @Column(name = "reportid")
    private Integer reportId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "commentid")
    private ListComment comment;

    /* default */ ListCommentReport() {

    }

    public ListCommentReport(Integer reportId, User reportee, String report, LocalDateTime date, ListComment comment) {
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

    public ListComment getComment() {
        return comment;
    }

    public void setComment(ListComment comment) {
        this.comment = comment;
    }
}
