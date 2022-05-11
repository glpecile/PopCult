package ar.edu.itba.paw.webapp.dto.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ReportDto {

    @NotNull
    @Size(min = 1, max = 1000)
    @Pattern(regexp = "[a-zA-Z0-9\\s]+")
    private String report;

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
