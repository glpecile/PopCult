package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.CommentAlreadyReportedException;
import ar.edu.itba.paw.interfaces.exceptions.ListAlreadyReportedException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    /**
     * A diferencia de todas las funciones que crean entidades y retornan la entidad,
     * en este caso se retorna Optional debido a que dependiendo si el usuario es o no
     * moderador, el contenido directamente se elimina y se informa al usuario.
     * Si el usuario es moderador no se crea un reporte, por ende se retorna un Optional.empty().
     * Si el usuario no es moderador, se crea el reporta y se retorna el Optional correspondiente.
     */
    @Transactional
    @Override
    public Optional<ListReport> reportList(MediaList mediaList, String report) throws ListAlreadyReportedException {
        ListReport listReport = null;
        if (moderatorService.principalIsMod()) {
            listsService.deleteList(mediaList);
            userService.strikeUser(mediaList.getUser());
            sendDeletedListEmail(mediaList.getUser(), mediaList, report);
        } else {
            Optional<User> user = userService.getCurrentUser();
            if (user.isPresent()) {
                listReport = reportDao.reportList(mediaList, user.get(), report);
                sendReportCreatedEmail(user.get(), report);
            }
        }
        return Optional.ofNullable(listReport);
    }

    @Transactional
    @Override
    public Optional<ListCommentReport> reportListComment(ListComment comment, String report) throws CommentAlreadyReportedException {
        ListCommentReport listCommentReport = null;
        if (moderatorService.principalIsMod()) {
            commentService.deleteCommentFromList(comment);
            userService.strikeUser(comment.getUser());
            sendDeletedCommentEmail(comment.getUser(), comment, report);
        } else {
            Optional<User> user = userService.getCurrentUser();
            if (user.isPresent()) {
                listCommentReport = reportDao.reportListComment(comment, user.get(), report);
                sendReportCreatedEmail(user.get(), report);
            }
        }
        return Optional.ofNullable(listCommentReport);
    }

    @Transactional
    @Override
    public Optional<MediaCommentReport> reportMediaComment(MediaComment comment, String report) throws CommentAlreadyReportedException {
        MediaCommentReport mediaCommentReport = null;
        if (moderatorService.principalIsMod()) {
            commentService.deleteCommentFromMedia(comment);
            userService.strikeUser(comment.getUser());
            sendDeletedCommentEmail(comment.getUser(), comment, report);
        } else {
            Optional<User> user = userService.getCurrentUser();
            if (user.isPresent()) {
                mediaCommentReport = reportDao.reportMediaComment(comment, user.get(), report);
                sendReportCreatedEmail(user.get(), report);
            }
        }
        return Optional.ofNullable(mediaCommentReport);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ListReport> getListReportById(int reportId) {
        return reportDao.getListReportById(reportId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ListCommentReport> getListCommentReportById(int reportId) {
        return reportDao.getListCommentReportById(reportId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<MediaCommentReport> getMediaCommentReportById(int reportId) {
        return reportDao.getMediaCommentReportById(reportId);
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
    public void deleteListReport(ListReport listReport) {
        reportDao.deleteListReport(listReport);
        sendReportRejectedEmail(listReport.getReportee(), listReport.getReport());
    }

    @Transactional
    @Override
    public void deleteListCommentReport(ListCommentReport listCommentReport) {
        reportDao.deleteListCommentReport(listCommentReport);
        sendReportRejectedEmail(listCommentReport.getReportee(), listCommentReport.getReport());
    }

    @Transactional
    @Override
    public void deleteMediaCommentReport(MediaCommentReport mediaCommentReport) {
        reportDao.deleteMediaCommentReport(mediaCommentReport);
        sendReportRejectedEmail(mediaCommentReport.getReportee(), mediaCommentReport.getReport());
    }


    @Transactional
    @Override
    public void approveListReport(ListReport listReport) {
        listsService.deleteList(listReport.getMediaList());
        userService.strikeUser(listReport.getMediaList().getUser());
        sendReportApprovedEmail(listReport.getReportee(), listReport.getReport());
        sendDeletedListEmail(listReport.getMediaList().getUser(), listReport.getMediaList(), listReport.getReport());
    }

    @Transactional
    @Override
    public void approveListCommentReport(ListCommentReport listCommentReport) {
        commentService.deleteCommentFromList(listCommentReport.getComment());
        userService.strikeUser(listCommentReport.getComment().getUser());
        sendReportApprovedEmail(listCommentReport.getReportee(), listCommentReport.getReport());
        sendDeletedCommentEmail(listCommentReport.getComment().getUser(), listCommentReport.getComment(), listCommentReport.getReport());
    }

    @Transactional
    @Override
    public void approveMediaCommentReport(MediaCommentReport mediaCommentReport) {
        commentService.deleteCommentFromMedia(mediaCommentReport.getComment());
        userService.strikeUser(mediaCommentReport.getComment().getUser());
        sendReportApprovedEmail(mediaCommentReport.getReportee(), mediaCommentReport.getReport());
        sendDeletedCommentEmail(mediaCommentReport.getComment().getUser(), mediaCommentReport.getComment(), mediaCommentReport.getReport());
    }

    private void sendReportCreatedEmail(User reporter, String report) {
        emailService.sendReportCreatedEmail(reporter, report, LocaleContextHolder.getLocale());
    }

    private void sendReportRejectedEmail(User reporter, String report) {
        emailService.sendReportRejectedEmail(reporter, report, LocaleContextHolder.getLocale());
    }

    private void sendReportApprovedEmail(User reporter, String report) {
        emailService.sendReportApprovedEmail(reporter, report, LocaleContextHolder.getLocale());
    }

    private void sendDeletedListEmail(User user, MediaList mediaList, String report) {
        emailService.sendDeletedListEmail(user, mediaList, report, LocaleContextHolder.getLocale());
    }

    private void sendDeletedCommentEmail(User user, Comment comment, String report) {
        emailService.sendDeletedCommentEmail(user, comment, report, LocaleContextHolder.getLocale());
    }
}
