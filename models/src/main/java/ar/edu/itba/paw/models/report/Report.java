package ar.edu.itba.paw.models.report;

import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class Report {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "reporteeid")
    private User reportee;

    @Column(name = "report", length = 1000, nullable = false)
    private String report;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;

    /* default */ Report() {
        //Just for hibernate
    }

    public Report(User reportee, String report, Date date) {
        this.reportee = reportee;
        this.report = report;
        this.date = date;
    }

    public User getReportee() {
        return reportee;
    }

    public void setReportee(User reportee) {
        this.reportee = reportee;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
