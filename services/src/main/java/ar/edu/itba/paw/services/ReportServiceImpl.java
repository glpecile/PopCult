package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if (principalIsMod()) {
            listsService.getMediaListById(listId).ifPresent(mediaList -> {
                listsService.deleteList(mediaList.getMediaListId());
                sendDeletedListEmail(mediaList.getUserId(), mediaList);
            });
        } else {
            userService.getCurrentUser().ifPresent(user -> {
                reportDao.reportList(listId, user.getUserId(), report);
                sendReportCreatedEmail(user.getEmail(), report);
            });
        }
    }

    @Override
    public void reportListComment(int listId, int commentId, String report) {
        if (principalIsMod()) {
            commentService.getListCommentById(commentId).ifPresent(comment -> {
                commentService.deleteCommentFromList(comment.getCommentId());
                sendDeletedCommentEmail(comment.getUserId(), comment);
            });
        } else {
            userService.getCurrentUser().ifPresent(user -> {
                reportDao.reportListComment(listId, commentId, user.getUserId(), report);
                sendReportCreatedEmail(user.getEmail(), report);
            });
        }
    }

    @Override
    public void reportMediaComment(int mediaId, int commentId, String report) {
        if (principalIsMod()) {
            commentService.getMediaCommentById(commentId).ifPresent(comment -> {
                commentService.deleteCommentFromMedia(comment.getCommentId());
                sendDeletedCommentEmail(comment.getUserId(), comment);
            });
        } else {
            userService.getCurrentUser().ifPresent(user -> {
                reportDao.reportMediaComment(mediaId, commentId, user.getUserId(), report);
                sendReportCreatedEmail(user.getEmail(), report);
            });
        }
    }

    private void sendReportCreatedEmail(String email, String report) {
        emailService.sendReportCreatedEmail(email, report);
    }

    private boolean principalIsMod() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MOD"));
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
        reportDao.getListReportById(reportId).ifPresent(report -> {
            listsService.getMediaListById(report.getMediaListId()).ifPresent(mediaList -> {
                listsService.deleteList(mediaList.getMediaListId());
                sendReportApprovedEmail(report.getReporteeId(), report.getReport());
                sendDeletedListEmail(mediaList.getUserId(), mediaList);
            });
        });
    }

    @Override
    public void approveListCommentReport(int reportId) {
        reportDao.getListCommentReportById(reportId).ifPresent(report -> {
            commentService.getListCommentById(report.getCommentId()).ifPresent(comment -> {
                commentService.deleteCommentFromList(comment.getCommentId());
                sendReportApprovedEmail(report.getReporteeId(), report.getReport());
                sendDeletedCommentEmail(comment.getUserId(), comment);
            });
        });
    }

    @Override
    public void approveMediaCommentReport(int reportId) {
        reportDao.getMediaCommentReportById(reportId).ifPresent(report -> {
            commentService.getMediaCommentById(report.getCommentId()).ifPresent(comment -> {
                commentService.deleteCommentFromMedia(comment.getCommentId());
                sendReportApprovedEmail(report.getReporteeId(), report.getReport());
                sendDeletedCommentEmail(comment.getUserId(), comment);
            });
        });
    }

    private void sendReportApprovedEmail(int reporteeId, String report) {
        userService.getById(reporteeId).ifPresent(user -> {
            emailService.sendReportApprovedEmail(user.getEmail(), report);
        });
    }

    private void sendDeletedListEmail(int userId, MediaList mediaList) {
        userService.getById(userId).ifPresent(user -> {
            emailService.sendDeletedListEmail(user.getEmail(), mediaList);
        });
    }

    private void sendDeletedCommentEmail(int userId, Comment comment) {
        userService.getById(userId).ifPresent(user -> {
            emailService.sendDeletedCommentEmail(user.getEmail(), comment);
        });
    }
}
