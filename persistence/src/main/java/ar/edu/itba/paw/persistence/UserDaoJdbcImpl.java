package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoJdbcImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<User> ROW_MAPPER =
            (rs, rowNum) -> new User(
                    rs.getInt("userId"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("profilePhoto"));

    @Autowired
    public UserDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("users").usingGeneratedKeyColumns("userid");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users(" +
                "userId SERIAL PRIMARY KEY," +
                "email TEXT NOT NULL," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "name VARCHAR(100)," +
                "profilephoto BYTEA," +
                "UNIQUE(email)," +
                "UNIQUE(username)" +
                ")");
    }

    @Override
    public Optional<User> getById(int userId) {
        return jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", new Object[]{userId},ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> getByEmail(String email){
        return jdbcTemplate.query("SELECT * FROM users WHERE email = ?", new Object[]{email},ROW_MAPPER).stream().findFirst();
    }

    @Override
    public User register(String email, String userName, String password, String name, String profilePhotoURL){
        final Map<String, Object> args = new HashMap<>();
        args.put("email", email);
        args.put("username", userName);
        args.put("password", password);
        args.put("name", name);
        args.put("profilephoto", profilePhotoURL);
        final Number userId = jdbcInsert.executeAndReturnKey(args);
        return new User(userId.intValue(), email, userName, password, name, profilePhotoURL);
    }
}
