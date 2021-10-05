package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserJdbcDaoTest {

    private static final int USER_ID = 1;
    private static final String EMAIL = "test@popcult.com";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "password";
    private static final String NAME = "PopCult Test";
    private static final boolean NOT_ENABLED_USER = false;
    private static final boolean ENABLED_USER = true;
    private static final int DEFAULT_IMAGE_ID = 1;
    private static final int DEFAULT_USER_ROLE = Roles.USER.ordinal();

    @Autowired
    private UserDaoJdbcImpl userDaoJdbc;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void testRegister() {
        //1 - Setup - Preconditions


        //2 -Try class under test
        final User user = userDaoJdbc.register(EMAIL, USERNAME, PASSWORD, NAME, NOT_ENABLED_USER, DEFAULT_IMAGE_ID, DEFAULT_USER_ROLE);

        //3 - Asserts - Postconditions
        Assert.assertNotNull(user);
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }



}
