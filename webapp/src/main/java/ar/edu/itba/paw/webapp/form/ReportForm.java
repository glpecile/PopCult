package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ReportForm {
    @Size(min = 1, max = 1000)
    @Pattern(regexp = "[^></]+")
    private String report;

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
