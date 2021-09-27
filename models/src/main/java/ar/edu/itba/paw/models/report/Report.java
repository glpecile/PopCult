package ar.edu.itba.paw.models.report;

import java.util.Date;

public abstract class Report {
    private final String report;
    private final Date date;

    public Report(String report, Date date) {
        this.report = report;
        this.date = date;
    }

    public String getReport() {
        return report;
    }

    public Date getDate() {
        return date;
    }
}
