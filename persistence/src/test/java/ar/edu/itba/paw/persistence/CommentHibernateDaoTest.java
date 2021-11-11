package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.comment.Notification;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.hibernate.CommentHibernateDao;
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

import static ar.edu.itba.paw.persistence.InstanceProvider.ALREADY_EXISTS_LIST_COMMENT_ID;
import static ar.edu.itba.paw.persistence.InstanceProvider.ALREADY_EXISTS_MEDIA_COMMENT_ID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class CommentHibernateDaoTest {

    private static final String COMMENT = "Comment";

    private static final String LIST_COMMENT_TABLE = "listcomment";
    private static final String MEDIA_COMMENT_TABLE = "mediacomment";
    private static final String NOTIFICATION_TABLE = "commentnotifications";

    @Autowired
    private CommentHibernateDao commentHibernateDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    private User user;
    private Media media;
    private MediaList mediaList;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
        user = InstanceProvider.getUser();
        media = InstanceProvider.getMedia();
        mediaList = InstanceProvider.getMediaList();
    }

    @Rollback
    @Test
    public void testAddCommentToMedia() {
        MediaComment comment = commentHibernateDao.addCommentToMedia(user, media, COMMENT);

        em.flush();

        Assert.assertNotNull(comment);
        Assert.assertEquals(COMMENT, comment.getCommentBody());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, MEDIA_COMMENT_TABLE, String.format("commentid = '%d'", comment.getCommentId())));
    }

    @Rollback
    @Test
    public void testAddCommentToList() {
        ListComment comment = commentHibernateDao.addCommentToList(user, mediaList, COMMENT);

        em.flush();

        Assert.assertNotNull(comment);
        Assert.assertEquals(COMMENT, comment.getCommentBody());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, LIST_COMMENT_TABLE, String.format("commentid = '%d'", comment.getCommentId())));
    }

    @Rollback
    @Test
    public void testAddCommentNotification() {
        Notification notification = commentHibernateDao.addCommentNotification(InstanceProvider.getListComment());

        em.flush();

        Assert.assertNotNull(notification);
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, NOTIFICATION_TABLE, String.format("notificationid = %d", notification.getNotificationId())));
    }

    @Rollback
    @Test
    public void testGetMediaCommentById() {
        Optional<MediaComment> mediaComment = commentHibernateDao.getMediaCommentById(ALREADY_EXISTS_MEDIA_COMMENT_ID);

        Assert.assertTrue(mediaComment.isPresent());
    }

    @Rollback
    @Test
    public void testGetListCommentById() {
        Optional<ListComment> listComment = commentHibernateDao.getListCommentById(ALREADY_EXISTS_LIST_COMMENT_ID);

        Assert.assertTrue(listComment.isPresent());
    }
}
