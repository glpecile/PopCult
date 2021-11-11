package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.models.user.ModRequest;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.hibernate.ModeratorHibernateDao;
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
public class ModeratorHibernateDaoTest {

    private static final String MOD_REQUESTS_TABLE = "modrequests";

    @Autowired
    private ModeratorHibernateDao moderatorHibernateDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Rollback
    @Test
    public void testAddModRequest() throws ModRequestAlreadyExistsException {
        User user = InstanceProvider.getUser();

        ModRequest modRequest = moderatorHibernateDao.addModRequest(user);

        em.flush();

        Assert.assertNotNull(modRequest);
        Assert.assertEquals(user.getUserId(), modRequest.getUser().getUserId());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, MOD_REQUESTS_TABLE, String.format("userid = %d", user.getUserId())));
    }

    @Rollback
    @Test(expected = ModRequestAlreadyExistsException.class)
    public void testModRequestAlreadyExists() throws ModRequestAlreadyExistsException {
        User user = InstanceProvider.getUserWithModRequest();

        moderatorHibernateDao.addModRequest(user);

        Assert.fail();
    }
}
