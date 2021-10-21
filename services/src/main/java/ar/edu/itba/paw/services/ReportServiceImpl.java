package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import ar.edu.itba.paw.models.report.Report;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private ListsService listsService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModeratorService moderatorService;

    @Transactional
    @Override
    public void reportList(int listId, String report) {
        if (moderatorService.principalIsMod()) {
            listsService.getMediaListById(listId).ifPresent(mediaList -> {
                listsService.deleteList(mediaList.getMediaListId());
                sendDeletedListEmail(mediaList.getUserId(), mediaList, report);
            });
        } else {
            userService.getCurrentUser().ifPresent(user -> {
                reportDao.reportList(listId, user.getUserId(), report);
                sendReportCreatedEmail(user, report);
            });
        }
    }

    @Transactional
    @Override
    public void reportListComment(int listId, int commentId, String report) {
        if (moderatorService.principalIsMod()) {
            commentService.getListCommentById(commentId).ifPresent(comment -> {
                commentService.deleteCommentFromList(comment.getCommentId());
                sendDeletedCommentEmail(comment.getUserId(), comment, report);
            });
        } else {
            userService.getCurrentUser().ifPresent(user -> {
                reportDao.reportListComment(listId, commentId, user.getUserId(), report);
                sendReportCreatedEmail(user, report);
            });
        }
    }

    @Transactional
    @Override
    public void reportMediaComment(int mediaId, int commentId, String report) {
        if (moderatorService.principalIsMod()) {
            commentService.getMediaCommentById(commentId).ifPresent(comment -> {
                commentService.deleteCommentFromMedia(comment.getCommentId());
                sendDeletedCommentEmail(comment.getUserId(), comment, report);
            });
        } else {
            userService.getCurrentUser().ifPresent(user -> {
                reportDao.reportMediaComment(mediaId, commentId, user.getUserId(), report);
                sendReportCreatedEmail(user, report);
            });
        }
    }

    private void sendReportCreatedEmail(User user, String report) {
        emailService.sendReportCreatedEmail(user, report);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<ListReport> getListReports(int page, int pageSize) {
        return reportDao.getListReports(page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<ListCommentReport> getListCommentReports(int page, int pageSize) {
        return reportDao.getListCommentReports(page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaCommentReport> getMediaCommentReports(int page, int pageSize) {
        return reportDao.getMediaCommentReports(page, pageSize);
    }

    @Transactional
    @Override
    public void deleteListReport(int reportId) {
        reportDao.getListReportById(reportId).ifPresent(report -> {
            reportDao.deleteListReport(reportId);
            sendReportRejectedEmail(report.getReporteeId(), report.getReport());
        });

    }

    @Transactional
    @Override
    public void deleteListCommentReport(int reportId) {
        reportDao.getListCommentReportById(reportId).ifPresent(report -> {
            reportDao.deleteListCommentReport(reportId);
            sendReportRejectedEmail(report.getReporteeId(), report.getReport());
        });
    }

    @Transactional
    @Override
    public void deleteMediaCommentReport(int reportId) {
        reportDao.getMediaCommentReportById(reportId).ifPresent(report -> {
            reportDao.deleteMediaCommentReport(reportId);
            sendReportRejectedEmail(report.getReporteeId(), report.getReport());
        });
    }

    private void sendReportRejectedEmail(int reporteeId, String report) {
        userService.getById(reporteeId).ifPresent(user -> {
            emailService.sendReportRejectedEmail(user, report);
        });
    }

    @Transactional
    @Override
    public void approveListReport(int reportId) {
        reportDao.getListReportById(reportId).ifPresent(report -> {
            listsService.getMediaListById(report.getMediaListId()).ifPresent(mediaList -> {
                listsService.deleteList(mediaList.getMediaListId());
                sendReportApprovedEmail(report.getReporteeId(), report.getReport());
                sendDeletedListEmail(mediaList.getUserId(), mediaList, report.getReport());
            });
        });
    }

    @Transactional
    @Override
    public void approveListCommentReport(int reportId) {
        reportDao.getListCommentReportById(reportId).ifPresent(report -> {
            commentService.getListCommentById(report.getCommentId()).ifPresent(comment -> {
                commentService.deleteCommentFromList(comment.getCommentId());
                sendReportApprovedEmail(report.getReporteeId(), report.getReport());
                sendDeletedCommentEmail(comment.getUserId(), comment, report.getReport());
            });
        });
    }

    @Transactional
    @Override
    public void approveMediaCommentReport(int reportId) {
        reportDao.getMediaCommentReportById(reportId).ifPresent(report -> {
            commentService.getMediaCommentById(report.getCommentId()).ifPresent(comment -> {
                commentService.deleteCommentFromMedia(comment.getCommentId());
                sendReportApprovedEmail(report.getReporteeId(), report.getReport());
                sendDeletedCommentEmail(comment.getUserId(), comment, report.getReport());
            });
        });
    }

    private void sendReportApprovedEmail(int reporteeId, String report) {
        userService.getById(reporteeId).ifPresent(user -> {
            emailService.sendReportApprovedEmail(user, report);
        });
    }

    private void sendDeletedListEmail(int userId, MediaList mediaList, String report) {
        userService.getById(userId).ifPresent(user -> {
            emailService.sendDeletedListEmail(user, mediaList, report);
        });
    }

    private void sendDeletedCommentEmail(int userId, Comment comment, String report) {
        userService.getById(userId).ifPresent(user -> {
            emailService.sendDeletedCommentEmail(user, comment, report);
        });
    }
}
