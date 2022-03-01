package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.report.MediaCommentReport;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReportMediaCommentDto {

    private String report;
    private LocalDateTime date;
    private String reportedComment;
    private String mediaTitle;
    private String reportedUsername;
    private String reporterUsername;

    private String url;

    private String reportedCommentUrl;
    private String mediaUrl;
    private String reportedUserUrl;
    private String reporterUserUrl;

    public static ReportMediaCommentDto fromMediaCommentReport(UriInfo url, MediaCommentReport mediaCommentReport) {
        ReportMediaCommentDto reportMediaCommentDto = new ReportMediaCommentDto();
        reportMediaCommentDto.report = mediaCommentReport.getReport();
        reportMediaCommentDto.date = mediaCommentReport.getDate();
        reportMediaCommentDto.reportedComment = mediaCommentReport.getComment().getCommentBody();
        reportMediaCommentDto.mediaTitle = mediaCommentReport.getComment().getMedia().getTitle();
        reportMediaCommentDto.reportedUsername = mediaCommentReport.getComment().getUser().getUsername();
        reportMediaCommentDto.reporterUsername = mediaCommentReport.getReportee().getUsername();

        reportMediaCommentDto.url = url.getBaseUriBuilder().path("media-comments-reports").path(String.valueOf(mediaCommentReport.getReportId())).build().toString();
        reportMediaCommentDto.reportedCommentUrl = url.getBaseUriBuilder().path("media-comments").path(String.valueOf(mediaCommentReport.getComment().getCommentId())).build().toString();
        reportMediaCommentDto.mediaUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(mediaCommentReport.getComment().getMedia().getMediaId())).build().toString();
        reportMediaCommentDto.reportedUserUrl = url.getBaseUriBuilder().path("users").path(mediaCommentReport.getComment().getUser().getUsername()).build().toString();
        reportMediaCommentDto.reporterUserUrl = url.getBaseUriBuilder().path("users").path(mediaCommentReport.getReportee().getUsername()).build().toString();
        return reportMediaCommentDto;
    }

    public static List<ReportMediaCommentDto> fromMediaCommentReportList(UriInfo url, List<MediaCommentReport> mediaCommentReportList) {
        return mediaCommentReportList.stream().map(l -> ReportMediaCommentDto.fromMediaCommentReport(url, l)).collect(Collectors.toList());
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

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
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

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
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
