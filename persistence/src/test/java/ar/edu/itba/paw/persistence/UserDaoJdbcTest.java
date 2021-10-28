//package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
//import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
//import ar.edu.itba.paw.models.user.UserRole;
//import ar.edu.itba.paw.models.user.User;
//import ar.edu.itba.paw.persistence.config.TestConfig;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.jdbc.JdbcTestUtils;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.sql.DataSource;
//import java.util.Optional;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@Transactional
//public class UserDaoJdbcTest {
//
//    private static final String EMAIL = "test@popcult.com";
//    private static final String USERNAME = "test";
//    private static final String PASSWORD = "password";
//    private static final String NAME = "PopCult Test";
//    private static final boolean NOT_ENABLED_USER = false;
//    private static final boolean ENABLED_USER = true;
//    private static final int DEFAULT_USER_ROLE = UserRole.USER.ordinal();
//
//    private static final int ALREADY_EXISTS_USER_ID = 4;
//    private static final String ALREADY_EXISTS_EMAIL = "email@email.com";
//    private static final String ALREADY_EXISTS_USERNAME = "username";
//
//    @Autowired
//    private UserDaoJdbcImpl userDaoJdbc;
//
//    @Autowired
//    private DataSource ds;
//
//    private JdbcTemplate jdbcTemplate;
//
//    @Before
//    public void setUp() {
//        jdbcTemplate = new JdbcTemplate(ds);
//    }
//
//    @Rollback
//    @Test
//    public void testGetById() {
//        //2
//        final Optional<User> user = userDaoJdbc.getById(ALREADY_EXISTS_USER_ID);
//
//        //3 - Asserts
//        Assert.assertTrue(user.isPresent());
//        Assert.assertEquals(ALREADY_EXISTS_EMAIL, user.get().getEmail());
//    }
//
//    @Rollback
//    @Test
//    public void testGetByEmail() {
//        //2
//        final Optional<User> user = userDaoJdbc.getByEmail(ALREADY_EXISTS_EMAIL);
//
//        //3 - Asserts
//        Assert.assertTrue(user.isPresent());
//        Assert.assertEquals(ALREADY_EXISTS_EMAIL, user.get().getEmail());
//    }
//
//    @Rollback
//    @Test
//    public void testGetByUSername() {
//        //2
//        final Optional<User> user = userDaoJdbc.getByUsername(ALREADY_EXISTS_USERNAME);
//
//        //3 - Asserts
//        Assert.assertTrue(user.isPresent());
//        Assert.assertEquals(ALREADY_EXISTS_EMAIL, user.get().getEmail());
//    }
//
//
//
//    @Rollback
//    @Test
//    public void testRegister() throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
//        //1 - Setup - Preconditions
//
//        //2 - Try class under test
//        final User user = userDaoJdbc.register(EMAIL, USERNAME, PASSWORD, NAME);
//
//        //3 - Asserts - Postconditions
//        Assert.assertNotNull(user);
//        Assert.assertEquals(EMAIL, user.getEmail());
//        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", String.format("email = '%s'", EMAIL)));
//    }
//
//    @Rollback
//    @Test()
//    public void testEmailAlreadyExists() throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
//        //1 - Setup
//        //The inserts.sql scripts inserts an user with email: email@email.com.
//
//        //2
//        final User user = userDaoJdbc.register(ALREADY_EXISTS_EMAIL, USERNAME, PASSWORD, NAME);
//
//        //3 -Asserts
//        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", String.format("email = '%s'", ALREADY_EXISTS_EMAIL)));
//    }
//
//    @Rollback
//    @Test()
//    public void testUsernameAlreadyExists() throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
//        //1 - Setup
//        //The inserts.sql scripts inserts an user with username: username.
//
//        //2
//        final User user = userDaoJdbc.register(EMAIL, ALREADY_EXISTS_USERNAME, PASSWORD, NAME);
//
//        //3 -Asserts
//        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", String.format("username = '%s'", ALREADY_EXISTS_USERNAME)));
//    }
//
////    @Rollback
////    @Test
////    public void testChangePassword() {
////        //1 - Setup
////        final String newPassword = "newPassword";
////
////        //2
////        final Optional<User> user = userDaoJdbc.changePassword(ALREADY_EXISTS_USER_ID, newPassword);
////
////        //3 - Asserts
////        Assert.assertTrue(user.isPresent());
////        Assert.assertEquals(ALREADY_EXISTS_EMAIL, user.get().getEmail());
////        Assert.assertEquals(newPassword, user.get().getPassword());
////    }
//
////    @Rollback
////    @Test
////    public void testConfirmRegister() {
////        //1 - Setup
////
////        //2
////        final Optional<User> user = userDaoJdbc.confirmRegister(ALREADY_EXISTS_USER_ID, ENABLED_USER);
////
////        //3 - Asserts
////        Assert.assertTrue(user.isPresent());
////        Assert.assertEquals(ALREADY_EXISTS_EMAIL, user.get().getEmail());
////        Assert.assertTrue(user.get().isEnabled());
////    }
//
//}
