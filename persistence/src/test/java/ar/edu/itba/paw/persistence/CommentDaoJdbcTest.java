package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class CommentDaoJdbcTest {

    private static final int ALREADY_EXISTS_USER_ID = 2;
    private static final int ALREADY_EXISTS_MEDIA_ID = 1;
    private static final int ALREADY_EXISTS_LIST_ID = 1;
    private static final String COMMENT = "Comment";


    @Autowired
    private CommentDaoJdbcImpl commentDaoJdbc;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Rollback
    @Test
    public void testAddCommentToMedia() {
        //1

        //2
        Comment comment = commentDaoJdbc.addCommentToMedia(ALREADY_EXISTS_USER_ID, ALREADY_EXISTS_MEDIA_ID, COMMENT);

        //3
        Assert.assertNotNull(comment);
        Assert.assertEquals(COMMENT, comment.getCommentBody());
    }

    @Rollback
    @Test
    public void testAddCommentToList() {
        //1

        //2
        Comment comment = commentDaoJdbc.addCommentToList(ALREADY_EXISTS_USER_ID, ALREADY_EXISTS_LIST_ID, COMMENT);

        //3
        Assert.assertNotNull(comment);
        Assert.assertEquals(COMMENT, comment.getCommentBody());
    }
}
