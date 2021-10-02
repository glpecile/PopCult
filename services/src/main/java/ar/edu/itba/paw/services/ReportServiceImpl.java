package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
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
    @Autowired
    EmailService emailService;
    @Autowired
    UserService userService;

    @Override
    public void reportList(int listId, String report) {
        userService.getCurrentUser().ifPresent(user -> {
            reportDao.reportList(listId, user.getUserId(), report);
            sendReportCreatedEmail(user.getEmail(), report);
        });
    }

    @Override
    public void reportListComment(int listId, int commentId, String report) {
        userService.getCurrentUser().ifPresent(user -> {
            reportDao.reportListComment(listId, commentId, user.getUserId(), report);
            sendReportCreatedEmail(user.getEmail(), report);
        });
    }

    @Override
    public void reportMediaComment(int mediaId, int commentId, String report) {
        userService.getCurrentUser().ifPresent(user -> {
            reportDao.reportMediaComment(mediaId, commentId, user.getUserId(), report);
            sendReportCreatedEmail(user.getEmail(), report);
        });
    }

    private void sendReportCreatedEmail(String email, String report) {
        emailService.sendReportCreatedEmail(email, report);
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
        reportDao.getListReportById(reportId).ifPresent(report -> {
            reportDao.deleteListReport(reportId);
            sendReportRejectedEmail(report.getReporteeId(), report.getReport());
        });

    }

    @Override
    public void deleteListCommentReport(int reportId) {
        reportDao.getListCommentReportById(reportId).ifPresent(report -> {
            reportDao.deleteListCommentReport(reportId);
            sendReportRejectedEmail(report.getReporteeId(), report.getReport());
        });
    }

    @Override
    public void deleteMediaCommentReport(int reportId) {
        reportDao.getMediaCommentReportById(reportId).ifPresent(report -> {
            reportDao.deleteMediaCommentReport(reportId);
            sendReportRejectedEmail(report.getReporteeId(), report.getReport());
        });
    }

    private void sendReportRejectedEmail(int reporteeId, String report) {
        userService.getById(reporteeId).ifPresent(user -> {
            emailService.sendReportRejectedEmail(user.getEmail(), report);
        });
    }

    @Override
    public void approveListReport(int reportId) {
        reportDao.getListReportById(reportId).ifPresent(listReport -> {
            listsService.getMediaListById(listReport.getMediaListId()).ifPresent(mediaList -> {
                listsService.deleteList(listReport.getMediaListId());
                userService.getById(mediaList.getUserId()).ifPresent(user -> {
                    emailService.sendDeletedListEmail(user.getEmail(), mediaList);
                });
                userService.getCurrentUser().ifPresent(user -> {
                    emailService.sendReportApprovedEmail(user.getEmail(), listReport.getReport());
                });
            });
        });
    }

    @Override
    public void approveListCommentReport(int reportId) {
        reportDao.getListCommentReportById(reportId).ifPresent(listCommentReport -> {
            commentService.getListCommentById(listCommentReport.getCommentId()).ifPresent(comment -> {
                commentService.deleteCommentFromList(listCommentReport.getCommentId());
                userService.getById(comment.getUserId()).ifPresent(user -> {
                    emailService.sendDeletedCommentEmail(user.getEmail(), comment);
                });
                userService.getCurrentUser().ifPresent(user -> {
                    emailService.sendReportApprovedEmail(user.getEmail(), listCommentReport.getReport());
                });
            });
        });
    }

    @Override
    public void approveMediaCommentReport(int reportId) {
        reportDao.getMediaCommentReportById(reportId).ifPresent(mediaCommentReport -> {
            commentService.getMediaCommentById(mediaCommentReport.getCommentId()).ifPresent(comment -> {
                commentService.deleteCommentFromMedia(mediaCommentReport.getCommentId());
                userService.getById(comment.getUserId()).ifPresent(user -> {
                    emailService.sendDeletedCommentEmail(user.getEmail(), comment);
                });
                userService.getCurrentUser().ifPresent(user -> {
                    emailService.sendReportApprovedEmail(user.getEmail(), mediaCommentReport.getReport());
                });
            });
        });
    }
}
