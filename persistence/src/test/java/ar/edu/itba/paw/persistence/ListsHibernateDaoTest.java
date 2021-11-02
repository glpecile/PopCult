package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.hibernate.ListsHibernateDao;
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
public class ListsHibernateDaoTest {

    private static final int ALREADY_EXISTS_USER_ID = 4;
    private static final String ALREADY_EXISTS_EMAIL = "email@email.com";
    private static final String ALREADY_EXISTS_USERNAME = "username";
    private static final String ALREADY_EXISTS_PASSWORD = "password";
    private static final String ALREADY_EXISTS_NAME = "name";

    private static final String LISTNAME = "List";
    private static final String DESCRIPTION = "Description";
    private static final boolean VISIBILITY = true;
    private static final boolean COLLABORATIVE = true;

    private static final String LISTS_TABLE = "medialist";

    @Autowired
    private ListsHibernateDao listsHibernateDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    private User user;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
        user = new User
                .Builder(ALREADY_EXISTS_EMAIL, ALREADY_EXISTS_USERNAME, ALREADY_EXISTS_PASSWORD, ALREADY_EXISTS_NAME)
                .userId(ALREADY_EXISTS_USER_ID)
                .build();
    }

    @Rollback
    @Test
    public void testCreateMediaList() {
        MediaList mediaList = listsHibernateDao.createMediaList(user, LISTNAME, DESCRIPTION, VISIBILITY, COLLABORATIVE);

        em.flush();

        Assert.assertNotNull(mediaList);
        Assert.assertEquals(LISTNAME, mediaList.getListName());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, LISTS_TABLE, String.format("listname = '%s'", LISTNAME)));
    }

}
