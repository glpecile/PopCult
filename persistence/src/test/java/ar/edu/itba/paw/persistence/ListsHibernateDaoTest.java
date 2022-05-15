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
import java.util.Optional;

import static ar.edu.itba.paw.persistence.InstanceProvider.ALREADY_EXISTS_LIST_ID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ListsHibernateDaoTest {

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
        user = InstanceProvider.getUser();
    }

    @Rollback
    @Test
    public void testGetMediaListById() {
        Optional<MediaList> mediaList = listsHibernateDao.getMediaListById(ALREADY_EXISTS_LIST_ID);

        Assert.assertTrue(mediaList.isPresent());
        Assert.assertEquals(ALREADY_EXISTS_LIST_ID, mediaList.get().getMediaListId().intValue());
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

    @Rollback
    @Test
    public void testCanEditList() {
        MediaList mediaList = InstanceProvider.getCanEditMediaList();

        boolean canEdit = listsHibernateDao.canEditList(user, mediaList);

        Assert.assertTrue(canEdit);
    }

    @Rollback
    @Test
    public void testCanNotEditList() {
        MediaList mediaList = InstanceProvider.getMediaList();

        boolean canEdit = listsHibernateDao.canEditList(user, mediaList);

        Assert.assertFalse(canEdit);
    }
}
