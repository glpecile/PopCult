package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Country;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class CommentHibernateDaoTest {

    private static final int ALREADY_EXISTS_USER_ID = 4;
    private static final String ALREADY_EXISTS_EMAIL = "email@email.com";
    private static final String ALREADY_EXISTS_USERNAME = "username";
    private static final String ALREADY_EXISTS_PASSWORD = "password";
    private static final String ALREADY_EXISTS_NAME = "name";

    private static final int ALREADY_EXISTS_MEDIA_ID = 1;

    private static final int ALREADY_EXISTS_LIST_ID = 2;

    private static final String COMMENT = "Comment";

    private static final String LIST_COMMENT_TABLE = "listcomment";
    private static final String MEDIA_COMMENT_TABLE = "mediacomment";

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
        user = new User
                .Builder(ALREADY_EXISTS_EMAIL, ALREADY_EXISTS_USERNAME, ALREADY_EXISTS_PASSWORD, ALREADY_EXISTS_NAME)
                .userId(ALREADY_EXISTS_USER_ID)
                .build();
        media = new Media(ALREADY_EXISTS_MEDIA_ID, MediaType.FILMS, "House", "...", "", 7788, null, 8, Country.US);
        mediaList = new MediaList(ALREADY_EXISTS_LIST_ID, user, "Kids Movies", "...", null, true, false);
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
}
