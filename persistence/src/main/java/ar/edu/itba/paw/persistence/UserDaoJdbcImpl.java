package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class UserDaoJdbcImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<User> ROW_MAPPER =
            (rs, rowNum) -> new User(
                    rs.getInt("userId"),
                    rs.getString("mail"),
                    rs.getString("username"),
                    rs.getString("password"));

    @Autowired
    public UserDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("users").usingGeneratedKeyColumns("userId");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users(" +
                "userId SERIAL PRIMARY KEY," +
                "mail TEXT NOT NULL," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL)");
    }

    @Override
    public Optional<User> getById(int userId) {
        return Optional.empty();
    }
}
