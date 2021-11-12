package ar.edu.itba.paw.models.report;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "listreport")
public class ListReport extends Report {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listreport_reportid_seq")
    @SequenceGenerator(sequenceName = "listreport_reportid_seq", name = "listreport_reportid_seq", allocationSize = 1)
    @Column(name = "reportid")
    private Integer reportId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "listid")
    private MediaList mediaList;

    /* default */ ListReport() {

    }

    public ListReport(Integer reportId, User reportee, String report, LocalDateTime date, MediaList mediaList) {
        super(reportee, report, date);
        this.reportId = reportId;
        this.mediaList = mediaList;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public MediaList getMediaList() {
        return mediaList;
    }

    public void setMediaList(MediaList mediaList) {
        this.mediaList = mediaList;
    }
}
