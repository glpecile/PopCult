package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.report.ListReport;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReportListDto {

    private String report;
    private LocalDateTime date;
    private String reportedListName;
    private String reportedUsername;
    private String reporterUsername;

    private String url;

    private String reportedListUrl;
    private String reportedUserUrl;
    private String reporterUserUrl;

    public static ReportListDto fromListReport(UriInfo url, ListReport listReport) {
        ReportListDto reportListDto = new ReportListDto();
        reportListDto.report = listReport.getReport();
        reportListDto.date = listReport.getDate();
        reportListDto.reportedListName = listReport.getMediaList().getListName();
        reportListDto.reportedUsername = listReport.getMediaList().getUser().getUsername();
        reportListDto.reporterUsername = listReport.getReportee().getUsername();

        reportListDto.url = url.getBaseUriBuilder().path("lists-reports").path(String.valueOf(listReport.getReportId())).build().toString();
        reportListDto.reportedListUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(listReport.getMediaList().getMediaListId())).build().toString();
        reportListDto.reportedUserUrl = url.getBaseUriBuilder().path("users").path(listReport.getMediaList().getUser().getUsername()).build().toString();
        reportListDto.reporterUserUrl = url.getBaseUriBuilder().path("users").path(listReport.getReportee().getUsername()).build().toString();
        return reportListDto;
    }

    public static List<ReportListDto> fromListReportList(UriInfo url, List<ListReport> listReportList) {
        return listReportList.stream().map(l -> ReportListDto.fromListReport(url, l)).collect(Collectors.toList());
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getReportedListName() {
        return reportedListName;
    }

    public void setReportedListName(String reportedListName) {
        this.reportedListName = reportedListName;
    }

    public String getReportedUsername() {
        return reportedUsername;
    }

    public void setReportedUsername(String reportedUsername) {
        this.reportedUsername = reportedUsername;
    }

    public String getReporterUsername() {
        return reporterUsername;
    }

    public void setReporterUsername(String reporterUsername) {
        this.reporterUsername = reporterUsername;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReportedListUrl() {
        return reportedListUrl;
    }

    public void setReportedListUrl(String reportedListUrl) {
        this.reportedListUrl = reportedListUrl;
    }

    public String getReportedUserUrl() {
        return reportedUserUrl;
    }

    public void setReportedUserUrl(String reportedUserUrl) {
        this.reportedUserUrl = reportedUserUrl;
    }

    public String getReporterUserUrl() {
        return reporterUserUrl;
    }

    public void setReporterUserUrl(String reporterUserUrl) {
        this.reporterUserUrl = reporterUserUrl;
    }
}
