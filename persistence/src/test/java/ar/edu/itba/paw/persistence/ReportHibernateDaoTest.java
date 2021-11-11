package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.hibernate.ReportHibernateDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import java.util.Optional;

import static ar.edu.itba.paw.persistence.InstanceProvider.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ReportHibernateDaoTest {

    private static final String REPORT = "Report";

    private static final String LIST_REPORT_TABLE = "listreport";
    private static final String LIST_COMMENT_REPORT_TABLE = "listcommentreport";
    private static final String MEDIA_COMMENT_REPORT_TABLE = "mediacommentreport";

    @Autowired
    private ReportHibernateDao reportHibernateDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    private User user;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
        user = InstanceProvider.getUser();
    }

    @Rollback
    @Test
    public void testReportList() {
        MediaList mediaList = InstanceProvider.getMediaList();

        ListReport listReport = reportHibernateDao.reportList(mediaList, user, REPORT);

        em.flush();

        Assert.assertNotNull(listReport);
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, LIST_REPORT_TABLE, String.format("reportid = %d", listReport.getReportId())));
    }

    @Rollback
    @Test
    public void testReportListComment() {
        ListComment listComment = InstanceProvider.getListComment();

        ListCommentReport listCommentReport = reportHibernateDao.reportListComment(listComment, user, REPORT);

        em.flush();

        Assert.assertNotNull(listCommentReport);
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, LIST_COMMENT_REPORT_TABLE, String.format("reportid = %d", listCommentReport.getReportId())));
    }

    @Rollback
    @Test
    public void testReportMediaComment() {
        MediaComment mediaComment = InstanceProvider.getMediaComment();

        MediaCommentReport mediaCommentReport = reportHibernateDao.reportMediaComment(mediaComment, user, REPORT);

        em.flush();

        Assert.assertNotNull(mediaComment);
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, MEDIA_COMMENT_REPORT_TABLE, String.format("reportid = %d", mediaCommentReport.getReportId())));
    }

    @Rollback
    @Test
    public void testGetListReportById() {
        Optional<ListReport> listReport = reportHibernateDao.getListReportById(ALREADY_EXISTS_LIST_REPORT_ID);

        Assert.assertTrue(listReport.isPresent());
        Assert.assertEquals(ALREADY_EXISTS_LIST_REPORT_ID, listReport.get().getReportId().intValue());
    }

    @Rollback
    @Test
    public void testGetListCommentReportById() {
        Optional<ListCommentReport> listCommentReport = reportHibernateDao.getListCommentReportById(ALREADY_EXISTS_LIST_COMMENT_REPORT_ID);

        Assert.assertTrue(listCommentReport.isPresent());
        Assert.assertEquals(ALREADY_EXISTS_LIST_COMMENT_REPORT_ID, listCommentReport.get().getReportId().intValue());
    }

    @Rollback
    @Test
    public void testGetMediaCommentReportById() {
        Optional<MediaCommentReport> mediaCommentReport = reportHibernateDao.getMediaCommentReportById(ALREADY_EXISTS_MEDIA_COMMENT_REPORT_ID);

        Assert.assertTrue(mediaCommentReport.isPresent());
        Assert.assertEquals(ALREADY_EXISTS_MEDIA_COMMENT_REPORT_ID, mediaCommentReport.get().getReportId().intValue());
    }
}
