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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoJdbcImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert favoriteMediaJdbcInsert;
    private final SimpleJdbcInsert toWatchMediaJdbcInsert;
    private final SimpleJdbcInsert favoriteListsJdbcInsert;

    private static final RowMapper<User> ROW_MAPPER =
            (rs, rowNum) -> new User(
                    rs.getInt("userId"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    "name", //TODO
                    "profilePhoto"); //TODO

    private static final RowMapper<Integer> COUNT_ROW_MAPPER =
            (rs, rowNum) -> rs.getInt("count");

    private static final RowMapper<Integer> INTEGER_ROW_MAPPER =
            (rs, rowNum) -> rs.getInt("mediaId");

    @Autowired
    public UserDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("users").usingGeneratedKeyColumns("userid");
        favoriteMediaJdbcInsert = new SimpleJdbcInsert(ds).withTableName("favoritemedia");
        toWatchMediaJdbcInsert = new SimpleJdbcInsert(ds).withTableName("towatchmedia");
        favoriteListsJdbcInsert = new SimpleJdbcInsert(ds).withTableName("favoritelists");


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

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS favoritemedia(" +
                "userId INT NOT NULL," +
                "mediaId INT NOT NULL," +
                "FOREIGN KEY(mediaId) REFERENCES media(mediaId) ON DELETE CASCADE," +
                "FOREIGN KEY(userId) REFERENCES users(userId) ON DELETE CASCADE)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS towatchmedia(" +
                "userId INT NOT NULL," +
                "mediaId INT NOT NULL," +
                "watchedDate DATE," +
                "FOREIGN KEY(mediaId) REFERENCES media(mediaId) ON DELETE CASCADE," +
                "FOREIGN KEY(userId) REFERENCES users(userId) ON DELETE CASCADE)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS favoritelists(" +
                "userId INT NOT NULL," +
                "mediaListId INT NOT NULL," +
                "FOREIGN KEY(mediaListId) REFERENCES medialist(mediaListId) ON DELETE CASCADE," +
                "FOREIGN KEY(userId) REFERENCES users(userId) ON DELETE CASCADE)");
    }

    @Override
    public Optional<User> getById(int userId) {
        return jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", new Object[]{userId}, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM users WHERE email = ?", new Object[]{email}, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public User register(String email, String userName, String password, String name, String profilePhotoURL) {
        final Map<String, Object> args = new HashMap<>();
        args.put("email", email);
        args.put("username", userName);
        args.put("password", password);
        args.put("name", name);
        args.put("profilephoto", profilePhotoURL);
        final Number userId = jdbcInsert.executeAndReturnKey(args);
        return new User(userId.intValue(), email, userName, password, name, profilePhotoURL);
    }

    @Override
    public void addMediaToFav(int mediaId, int userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("mediaId", mediaId);
        data.put("userId", userId);
        favoriteMediaJdbcInsert.execute(data);
    }

    @Override
    public void deleteMediaFromFav(int mediaId, int userId) {
        jdbcTemplate.update("DELETE FROM favoritemedia WHERE mediaid = ? AND userid = ?", mediaId, userId);
    }

    @Override
    public boolean isFavorite(int mediaId, int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) FROM favoritemedia WHERE mediaid = ? AND userid = ?", new Object[]{mediaId, userId}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0) > 0;
    }

    @Override
    public List<Integer> getUserFavoriteMedia(int userId, int page, int pageSize) {
        return jdbcTemplate.query("SELECT * FROM favoritemedia WHERE userId = ? OFFSET ? LIMIT ?", new Object[]{userId, page * pageSize, pageSize}, INTEGER_ROW_MAPPER);
    }

    @Override
    public Optional<Integer> getFavoriteMediaCount(int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritemedia WHERE userId = ?", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void addListToFav(int userId, int mediaListId) {
        Map<String, Object> data = new HashMap<>();
        data.put("mediaListId", mediaListId);
        data.put("userId", userId);
        favoriteListsJdbcInsert.execute(data);
    }

    @Override
    public void deleteListFromFav(int userId, int mediaListId) {
        jdbcTemplate.update("DELETE FROM favoritelists WHERE medialistid = ? AND userid = ?", mediaListId, userId);
    }

    @Override
    public boolean isFavoriteList(int userId, int mediaListId) {
        return jdbcTemplate.query("SELECT COUNT(*) FROM favoritelists WHERE medialistid = ? AND userid = ?", new Object[]{mediaListId, userId}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0) > 0;
    }

    @Override
    public List<Integer> getUserFavoriteLists(int userId, int page, int pageSize) {
        return jdbcTemplate.query("SELECT * FROM favoritelists WHERE userId = ? OFFSET ? LIMIT ?", new Object[]{userId, page * pageSize, pageSize}, INTEGER_ROW_MAPPER);
    }

    @Override
    public Optional<Integer> getFavoriteListsCount(int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritelists WHERE userId = ?", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst();
    }
}
