package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.ReportDao;
import ar.edu.itba.paw.interfaces.ReportService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private ListsService listsService;
    @Autowired
    private CommentService commentService;

    @Override
    public void reportList(int listId, String report) {
        reportDao.reportList(listId, report);
    }

    @Override
    public void reportListComment(int listId, int commentId, String report) {
        reportDao.reportListComment(listId, commentId, report);
    }

    @Override
    public void reportMediaComment(int mediaId, int commentId, String report) {
        reportDao.reportMediaComment(mediaId, commentId, report);
    }

    @Override
    public PageContainer<ListReport> getListReports(int page, int pageSize) {
        return reportDao.getListReports(page, pageSize);
    }

    @Override
    public PageContainer<ListCommentReport> getListCommentReports(int page, int pageSize) {
        return reportDao.getListCommentReports(page, pageSize);
    }

    @Override
    public PageContainer<MediaCommentReport> getMediaCommentReports(int page, int pageSize) {
        return reportDao.getMediaCommentReports(page, pageSize);
    }

    @Override
    public void deleteListReport(int reportId) {
        reportDao.deleteListReport(reportId);
    }

    @Override
    public void deleteListCommentReport(int reportId) {
        reportDao.deleteListCommentReport(reportId);
    }

    @Override
    public void deleteMediaCommentReport(int reportId) {
        reportDao.deleteMediaCommentReport(reportId);
    }

    @Override
    public void approveListReport(int reportId) {
        reportDao.getListReportById(reportId).ifPresent(listReport -> {
            listsService.deleteList(listReport.getMediaListId());
        });
    }

    @Override
    public void approveListCommentReport(int reportId) {
        reportDao.getListCommentReportById(reportId).ifPresent(listCommentReport -> {
            commentService.deleteCommentFromList(listCommentReport.getCommentId());
        });
    }

    @Override
    public void approveMediaCommentReport(int reportId) {
        reportDao.getMediaCommentReportById(reportId).ifPresent(mediaCommentReport ->  {
            commentService.deleteCommentFromMedia(mediaCommentReport.getCommentId());
        });
    }
}
