package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.ReportDao;
import ar.edu.itba.paw.interfaces.exceptions.CommentAlreadyReportedException;
import ar.edu.itba.paw.interfaces.exceptions.ListAlreadyReportedException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.persistence.hibernate.utils.PaginationValidator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.*;

@Primary
@Repository
public class ReportHibernateDao implements ReportDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public ListReport reportList(MediaList mediaList, User reporter, String report) throws ListAlreadyReportedException {
        if (listAlreadyReportedByUser(mediaList, reporter)) {
            throw new ListAlreadyReportedException();
        }
        ListReport listReport = new ListReport(null, reporter, report, LocalDateTime.now(), mediaList);
        em.persist(listReport);
        return listReport;
    }

    private boolean listAlreadyReportedByUser(MediaList mediaList, User user) {
        return ((Number) em.createNativeQuery("SELECT COUNT(*) FROM listreport WHERE listid = :mediaListId AND reporteeid = :userId")
                .setParameter("mediaListId", mediaList.getMediaListId())
                .setParameter("userId", user.getUserId())
                .getSingleResult()).intValue() != 0;
    }

    @Override
    public ListCommentReport reportListComment(ListComment listComment, User reporter, String report) throws CommentAlreadyReportedException {
        if (listCommentAlreadyReportedByUser(listComment, reporter)) {
            throw new CommentAlreadyReportedException();
        }
        ListCommentReport listCommentReport = new ListCommentReport(null, reporter, report, LocalDateTime.now(), listComment);
        em.persist(listCommentReport);
        return listCommentReport;
    }

    private boolean listCommentAlreadyReportedByUser(ListComment listComment, User user) {
        return ((Number) em.createNativeQuery("SELECT COUNT(*) FROM listcommentreport WHERE commentid = :commentId AND reporteeid = :userId")
                .setParameter("commentId", listComment.getCommentId())
                .setParameter("userId", user.getUserId())
                .getSingleResult()).intValue() != 0;
    }

    @Override
    public MediaCommentReport reportMediaComment(MediaComment mediaComment, User reporter, String report) throws CommentAlreadyReportedException {
        if (mediaCommentAlreadyReportedByUser(mediaComment, reporter)) {
            throw new CommentAlreadyReportedException();
        }
        MediaCommentReport mediaCommentReport = new MediaCommentReport(null, reporter, report, LocalDateTime.now(), mediaComment);
        em.persist(mediaCommentReport);
        return mediaCommentReport;
    }

    private boolean mediaCommentAlreadyReportedByUser(MediaComment mediaComment, User user) {
        return ((Number) em.createNativeQuery("SELECT COUNT(*) FROM mediacommentreport WHERE commentid = :commentId AND reporteeid = :userId")
                .setParameter("commentId", mediaComment.getCommentId())
                .setParameter("userId", user.getUserId())
                .getSingleResult()).intValue() != 0;
    }

    @Override
    public Optional<ListReport> getListReportById(int reportId) {
        return Optional.ofNullable(em.find(ListReport.class, reportId));
    }

    @Override
    public Optional<ListCommentReport> getListCommentReportById(int reportId) {
        return Optional.ofNullable(em.find(ListCommentReport.class, reportId));
    }

    @Override
    public Optional<MediaCommentReport> getMediaCommentReportById(int reportId) {
        return Optional.ofNullable(em.find(MediaCommentReport.class, reportId));
    }

    @Override
    public PageContainer<ListReport> getListReports(int page, int pageSize) {
        PaginationValidator.validate(page, pageSize);

        final Query nativeQuery = em.createNativeQuery("SELECT reportid FROM listreport ORDER BY date DESC OFFSET :offset LIMIT :limit")
                .setParameter("offset", (page - 1) * pageSize)
                .setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> reportIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(*) FROM ListReport");
        long count = (long) countQuery.getSingleResult();

        final TypedQuery<ListReport> query = em.createQuery("FROM ListReport WHERE reportId IN (:reportIds) ORDER BY date DESC", ListReport.class)
                .setParameter("reportIds", reportIds);
        List<ListReport> listReports = reportIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(listReports, page, pageSize, count);
    }

    @Override
    public PageContainer<ListCommentReport> getListCommentReports(int page, int pageSize) {
        PaginationValidator.validate(page, pageSize);

        final Query nativeQuery = em.createNativeQuery("SELECT reportid FROM listcommentreport ORDER BY date DESC OFFSET :offset LIMIT :limit")
                .setParameter("offset", (page - 1) * pageSize)
                .setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> reportIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(*) FROM ListCommentReport");
        long count = (long) countQuery.getSingleResult();

        final TypedQuery<ListCommentReport> query = em.createQuery("FROM ListCommentReport WHERE reportId IN (:reportIds) ORDER BY date DESC", ListCommentReport.class)
                .setParameter("reportIds", reportIds);
        List<ListCommentReport> listReports = reportIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(listReports, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaCommentReport> getMediaCommentReports(int page, int pageSize) {
        PaginationValidator.validate(page, pageSize);

        final Query nativeQuery = em.createNativeQuery("SELECT reportid FROM mediacommentreport ORDER BY date DESC OFFSET :offset LIMIT :limit")
                .setParameter("offset", (page - 1) * pageSize)
                .setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> reportIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(*) FROM MediaCommentReport");
        long count = (long) countQuery.getSingleResult();

        final TypedQuery<MediaCommentReport> query = em.createQuery("FROM MediaCommentReport WHERE reportId IN (:reportIds) ORDER BY date DESC", MediaCommentReport.class)
                .setParameter("reportIds", reportIds);
        List<MediaCommentReport> listReports = reportIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(listReports, page, pageSize, count);
    }

    @Override
    public void deleteListReport(ListReport listReport) {
        em.remove(listReport);
    }

    @Override
    public void deleteListCommentReport(ListCommentReport listCommentReport) {
        em.remove(listCommentReport);
    }

    @Override
    public void deleteMediaCommentReport(MediaCommentReport mediaCommentReport) {
        em.remove(mediaCommentReport);
    }
}
