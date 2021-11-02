package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.hibernate.UserHibernateDao;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class UserHibernateDaoTest {

    private static final String EMAIL = "test@popcult.com";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "password";
    private static final String NAME = "PopCult Test";

    private static final int ALREADY_EXISTS_USER_ID = 4;
    private static final String ALREADY_EXISTS_EMAIL = "email@email.com";
    private static final String ALREADY_EXISTS_USERNAME = "username";

    private static final String USERS_TABLE = "users";

    @Autowired
    private UserHibernateDao userHibernateDao;

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
    public void testGetByID() {
        final Optional<User> user = userHibernateDao.getById(ALREADY_EXISTS_USER_ID);

        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(ALREADY_EXISTS_EMAIL, user.get().getEmail());
    }

    @Rollback
    @Test
    public void testGetByEmail() {
        final Optional<User> user = userHibernateDao.getByEmail(ALREADY_EXISTS_EMAIL);

        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(ALREADY_EXISTS_EMAIL, user.get().getEmail());
    }

    @Rollback
    @Test
    public void testGetByUsername() {
        final Optional<User> user = userHibernateDao.getByUsername(ALREADY_EXISTS_USERNAME);

        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(ALREADY_EXISTS_EMAIL, user.get().getEmail());
    }

    @Rollback
    @Test
    public void testRegister() throws  EmailAlreadyExistsException, UsernameAlreadyExistsException {
        final User user = userHibernateDao.register(EMAIL, USERNAME, PASSWORD, NAME);

        em.flush();

        Assert.assertNotNull(user);
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, USERS_TABLE, String.format("email = '%s'", EMAIL)));
    }

    @Rollback
    @Test(expected = EmailAlreadyExistsException.class)
    public void testEmailAlreadyExists() throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        //The inserts.sql script inserts a user with email: email@email.com.

        userHibernateDao.register(ALREADY_EXISTS_EMAIL, USERNAME, PASSWORD, NAME);

        Assert.fail();
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, USERS_TABLE, String.format("email = '%s'", ALREADY_EXISTS_EMAIL)));
    }

    @Rollback
    @Test(expected = UsernameAlreadyExistsException.class)
    public void testUsernameAlreadyExists() throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        //The inserts.sql script inserts a user with username: username.

        userHibernateDao.register(EMAIL, ALREADY_EXISTS_USERNAME, PASSWORD, NAME);

        Assert.fail();
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, USERS_TABLE, String.format("username = '%s'", ALREADY_EXISTS_USERNAME)));
    }

}
