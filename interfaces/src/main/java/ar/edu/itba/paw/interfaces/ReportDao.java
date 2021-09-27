package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.report.MediaCommentReport;

public interface ReportDao {

    void reportList(int listId, String report);

    void reportListComment(int listId, int commentId, String report);

    void reportMediaComment(int mediaId, int commentId, String report);

    PageContainer<ListReport> getListReports(int page, int pageSize);

    PageContainer<ListCommentReport> getListCommentReports(int page, int pageSize);

    PageContainer<MediaCommentReport> getMediaCommentReports(int page, int pageSize);

    void deleteListReport(int reportId);

    void deleteListCommentReport(int reportId);

    void deleteMediaCommentReport(int reportId);
}
