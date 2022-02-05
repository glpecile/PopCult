package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReportListCommentDto {

    private String report;
    private LocalDateTime date;
    private String reportedComment;
    private String listName;
    private String reportedUsername;
    private String reporterUsername;

    private String url;

    private String reportedCommentUrl;
    private String listUrl;
    private String reportedUserUrl;
    private String reporterUserUrl;

    public static ReportListCommentDto fromListCommentReport(UriInfo url, ListCommentReport listCommentReport) {
        ReportListCommentDto reportListCommentDto = new ReportListCommentDto();
        reportListCommentDto.report = listCommentReport.getReport();
        reportListCommentDto.date = listCommentReport.getDate();
        reportListCommentDto.reportedComment = listCommentReport.getComment().getCommentBody();
        reportListCommentDto.listName = listCommentReport.getComment().getMediaList().getListName();
        reportListCommentDto.reportedUsername = listCommentReport.getComment().getUser().getUsername();
        reportListCommentDto.reporterUsername = listCommentReport.getReportee().getUsername();

        reportListCommentDto.url = url.getBaseUriBuilder().path("lists-comments-reports").path(String.valueOf(listCommentReport.getReportId())).build().toString();
        reportListCommentDto.reportedCommentUrl = url.getBaseUriBuilder().path("lists-comments").path(String.valueOf(listCommentReport.getComment().getCommentId())).build().toString();
        reportListCommentDto.listUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(listCommentReport.getComment().getMediaList().getListName())).build().toString();
        reportListCommentDto.reportedUserUrl = url.getBaseUriBuilder().path("users").path(listCommentReport.getComment().getUser().getUsername()).build().toString();
        reportListCommentDto.reporterUserUrl = url.getBaseUriBuilder().path("users").path(listCommentReport.getReportee().getUsername()).build().toString();
        return reportListCommentDto;
    }

    public static List<ReportListCommentDto> fromListCommentReportList(UriInfo url, List<ListCommentReport> listCommentReportList) {
        return listCommentReportList.stream().map(l -> ReportListCommentDto.fromListCommentReport(url, l)).collect(Collectors.toList());
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

    public String getReportedComment() {
        return reportedComment;
    }

    public void setReportedComment(String reportedComment) {
        this.reportedComment = reportedComment;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
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

    public String getReportedCommentUrl() {
        return reportedCommentUrl;
    }

    public void setReportedCommentUrl(String reportedCommentUrl) {
        this.reportedCommentUrl = reportedCommentUrl;
    }

    public String getListUrl() {
        return listUrl;
    }

    public void setListUrl(String listUrl) {
        this.listUrl = listUrl;
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
