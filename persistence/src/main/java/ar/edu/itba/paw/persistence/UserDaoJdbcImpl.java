package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoJdbcImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<User> USER_ROW_MAPPER = RowMappers.USER_ROW_MAPPER;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoJdbcImpl.class);

    @Autowired
    public UserDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("users").usingGeneratedKeyColumns("userid");
    }

    @Override
    public Optional<User> getById(int userId) {
        return jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", new Object[]{userId}, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM users WHERE email = ?", new Object[]{email}, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return jdbcTemplate.query("SELECT * FROM users WHERE username = ?", new Object[]{username}, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public User register(String email, String userName, String password, String name, boolean enabled, int role) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        final Map<String, Object> args = new HashMap<>();
        args.put("email", email);
        args.put("username", userName);
        args.put("password", password);
        args.put("name", name);
        args.put("enabled", enabled);
//        args.put("imageid", imageId);
        args.put("role", role);
        int userId = 0;
        try {
            userId = jdbcInsert.executeAndReturnKey(args).intValue();
        } catch (DuplicateKeyException e) {
            if (e.getMessage().contains("users_email_key")) {
                LOGGER.error("Email {} already in use by another user", email);
                throw new EmailAlreadyExistsException();
            }
            if (e.getMessage().contains("users_username_key")) {
                LOGGER.error("Username {} already in use by another user", userName);
                throw new UsernameAlreadyExistsException();
            }
        }
        return new User(userId, email, userName, password, name, enabled, null, role);

    }

    @Override
    public Optional<User> changePassword(int userId, String password) {
        jdbcTemplate.update("UPDATE users SET password = ? WHERE userId = ?", password, userId);
        return getById(userId);
    }

    @Override
    public void confirmRegister(int userId, boolean enabled) {
        jdbcTemplate.update("UPDATE users SET enabled = ? WHERE userId = ?", enabled, userId);
    }

    @Override
    public void updateUserProfileImage(int userId, int imageId) {
        jdbcTemplate.update("UPDATE users SET imageid = ? WHERE userid = ? ", imageId, userId);
    }

    @Override
    public void updateUserData(int userId, String email, String username, String name) {
        jdbcTemplate.update("UPDATE users SET name = ?, email = ?, username = ? WHERE userid = ?", name, email, username, userId);
    }
}
