package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.ReportDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import ar.edu.itba.paw.models.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Primary
@Repository
public class ReportHibernateDao implements ReportDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public ListReport reportList(MediaList mediaList, User reportee, String report) {
        ListReport listReport = new ListReport(null, reportee, report, new Date(), mediaList);
        em.persist(listReport);
        return listReport;
    }

    @Override
    public ListCommentReport reportListComment(ListComment listComment, User reportee, String report) {
        ListCommentReport listCommentReport = new ListCommentReport(null, reportee, report, new Date(), listComment);
        em.persist(listCommentReport);
        return listCommentReport;
    }

    @Override
    public MediaCommentReport reportMediaComment(MediaComment mediaComment, User reportee, String report) {
        MediaCommentReport mediaCommentReport = new MediaCommentReport(null, reportee, report, new Date(), mediaComment);
        em.persist(mediaCommentReport);
        return mediaCommentReport;
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
        final Query nativeQuery = em.createNativeQuery("SELECT reportid FROM listreport ORDER BY date DESC OFFSET :offset LIMIT :limit")
                .setParameter("offset", page * pageSize)
                .setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> reportIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(*) FROM ListReport");
        long count = (long) countQuery.getSingleResult();

        final TypedQuery<ListReport> query = em.createQuery("FROM ListReport WHERE reportId IN (:reportIds)", ListReport.class)
                .setParameter("reportIds", reportIds);
        List<ListReport> listReports = reportIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(listReports, page, pageSize, count);
    }

    @Override
    public PageContainer<ListCommentReport> getListCommentReports(int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT reportid FROM listcommentreport ORDER BY date DESC OFFSET :offset LIMIT :limit")
                .setParameter("offset", page * pageSize)
                .setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> reportIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(*) FROM ListCommentReport");
        long count = (long) countQuery.getSingleResult();

        final TypedQuery<ListCommentReport> query = em.createQuery("FROM ListCommentReport WHERE reportId IN (:reportIds)", ListCommentReport.class)
                .setParameter("reportIds", reportIds);
        List<ListCommentReport> listReports = reportIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(listReports, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaCommentReport> getMediaCommentReports(int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT reportid FROM mediacommentreport ORDER BY date DESC OFFSET :offset LIMIT :limit")
                .setParameter("offset", page * pageSize)
                .setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> reportIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(*) FROM MediaCommentReport");
        long count = (long) countQuery.getSingleResult();

        final TypedQuery<MediaCommentReport> query = em.createQuery("FROM MediaCommentReport WHERE reportId IN (:reportIds)", MediaCommentReport.class)
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
