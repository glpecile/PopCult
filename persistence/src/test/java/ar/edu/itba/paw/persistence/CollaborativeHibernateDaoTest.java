package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.CollaboratorRequestAlreadyExistsException;
import ar.edu.itba.paw.models.collaborative.CollabRequest;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.hibernate.CollaborativeHibernateDao;
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

import static ar.edu.itba.paw.persistence.InstanceProvider.ALREADY_EXISTS_COLLAB_ID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class CollaborativeHibernateDaoTest {

    private static final String REQUEST_TABLE = "collaborative";

    @Autowired
    private CollaborativeHibernateDao collaborativeHibernateDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    private User user;
    private MediaList mediaList;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
        user = InstanceProvider.getNoCollaboratorUser();
        mediaList = InstanceProvider.getMediaList();
    }

    @Rollback
    @Test
    public void testMakeNewRequest() throws CollaboratorRequestAlreadyExistsException {
        CollabRequest request = collaborativeHibernateDao.makeNewRequest(mediaList, user);

        em.flush();

        Assert.assertNotNull(request);
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, REQUEST_TABLE, String.format("collabid = %d", request.getCollabId())));
    }

    @Rollback
    @Test(expected = CollaboratorRequestAlreadyExistsException.class)
    public void testCollaboratorRequestAlreadyExists() throws CollaboratorRequestAlreadyExistsException {
        User user = InstanceProvider.getUserCollaborator();

        collaborativeHibernateDao.makeNewRequest(mediaList, user);

        em.flush();

        Assert.fail();
    }

    @Rollback
    @Test
    public void testGetById() {
        Optional<CollabRequest> request = collaborativeHibernateDao.getById(ALREADY_EXISTS_COLLAB_ID);

        Assert.assertTrue(request.isPresent());
        Assert.assertEquals(ALREADY_EXISTS_COLLAB_ID, request.get().getCollabId().intValue());
    }

    @Rollback
    @Test
    public void testAddCollaborator() {
        collaborativeHibernateDao.addCollaborator(mediaList, user);

        em.flush();

        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, REQUEST_TABLE, String.format("listid = %d AND collaboratorid = %d", mediaList.getMediaListId(), user.getUserId())));
    }
}
