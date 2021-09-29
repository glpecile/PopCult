package ar.edu.itba.paw.models.report;

import java.util.Date;

public abstract class Report {
    private final int reportId;
    private final String report;
    private final Date date;

    public Report(int reportId, String report, Date date) {
        this.reportId = reportId;
        this.report = report;
        this.date = date;
    }

    public int getReportId() {
        return reportId;
    }

    public String getReport() {
        return report;
    }

    public Date getDate() {
        return date;
    }
}
