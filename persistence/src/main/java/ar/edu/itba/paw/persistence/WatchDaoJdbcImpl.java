package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.WatchDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.WatchedMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class WatchDaoJdbcImpl implements WatchDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert toWatchMediaJdbcInsert;

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;

    private static final RowMapper<Integer> MEDIA_ID_ROW_MAPPER = RowMappers.MEDIA_ID_ROW_MAPPER;

    private static final RowMapper<Media> MEDIA_ROW_MAPPER = RowMappers.MEDIA_ROW_MAPPER;

    private static final RowMapper<WatchedMedia> WATCHED_MEDIA_ROW_MAPPER = RowMappers.WATCHED_MEDIA_ROW_MAPPER;


    @Autowired
    public WatchDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        toWatchMediaJdbcInsert = new SimpleJdbcInsert(ds).withTableName("towatchmedia");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS towatchmedia(" +
                "userId INT NOT NULL," +
                "mediaId INT NOT NULL," +
                "watchDate DATE," +
                "FOREIGN KEY(mediaId) REFERENCES media(mediaId) ON DELETE CASCADE," +
                "FOREIGN KEY(userId) REFERENCES users(userId) ON DELETE CASCADE)");
    }

    @Override
    public void addWatchMedia(int mediaId, int userId, Date date) {
        Map<String, Object> data = new HashMap<>();
        data.put("mediaId", mediaId);
        data.put("userId", userId);
        data.put("watchDate", date);
        toWatchMediaJdbcInsert.execute(data);
    }

    @Override
    public void deleteWatchedMedia(int mediaId, int userId) {
        jdbcTemplate.update("DELETE FROM towatchmedia WHERE mediaId = ? AND userId = ? AND watchDate IS NOT NULL", mediaId, userId);

    }

    @Override
    public void deleteToWatchMedia(int mediaId, int userId) {
        jdbcTemplate.update("DELETE FROM towatchmedia WHERE mediaId = ? AND userId = ? AND watchDate IS NULL", mediaId, userId);
    }

    @Override
    public void updateWatchedMediaDate(int mediaId, int userId, Date date) {
        jdbcTemplate.update("UPDATE towatchmedia SET watchdate = ? WHERE mediaid = ? AND userid = ? AND watchdate IS NOT NULL", date, mediaId, userId);
    }

    @Override
    public boolean isWatched(int mediaId, int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM towatchmedia WHERE mediaId = ? AND userid = ? AND watchDate IS NOT NULL", new Object[]{mediaId, userId}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0) > 0;
    }

    @Override
    public boolean isToWatch(int mediaId, int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM towatchmedia WHERE mediaId = ? AND userid = ? AND watchDate IS NULL", new Object[]{mediaId, userId}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0) > 0;
    }

//    @Override
//    public PageContainer<Integer> getWatchedMediaIdIds(int userId, int page, int pageSize) {
//        List<Integer> elements = jdbcTemplate.query("SELECT * FROM towatchmedia WHERE watchDate IS NOT NULL ORDER BY watchDate OFFSET ? LIMIT ?", new Object[]{page * pageSize, pageSize}, MEDIA_ID_ROW_MAPPER);
//        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM towatchmedia WHERE userId = ? AND watchDate IS NOT NULL", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//        return new PageContainer<>(elements, page, pageSize, totalCount);
//    }

    @Override
    public PageContainer<WatchedMedia> getWatchedMediaId(int userId, int page, int pageSize) {
        List<WatchedMedia> elements = jdbcTemplate.query("SELECT * FROM towatchmedia NATURAL JOIN media WHERE watchDate IS NOT NULL ORDER BY watchDate DESC OFFSET ? LIMIT ?", new Object[]{page * pageSize, pageSize}, WATCHED_MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM towatchmedia WHERE userId = ? AND watchDate IS NOT NULL", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public Optional<Integer> getWatchedMediaCount(int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM towatchmedia WHERE userId = ? AND watchDate IS NOT NULL", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst();

    }

//    @Override
//    public PageContainer<Integer> getToWatchMediaIdIds(int userId, int page, int pageSize) {
//        List<Integer> elements = jdbcTemplate.query("SELECT * FROM towatchmedia WHERE watchDate IS NULL OFFSET ? LIMIT ?", new Object[]{page * pageSize, pageSize}, MEDIA_ID_ROW_MAPPER);
//        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM towatchmedia WHERE userId = ? AND watchDate IS NULL", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//        return new PageContainer<>(elements, page, pageSize, totalCount);
//    }

    @Override
    public PageContainer<Media> getToWatchMediaId(int userId, int page, int pageSize) {
        List<Media> elements = jdbcTemplate.query("SELECT * FROM towatchmedia NATURAL JOIN media WHERE watchDate IS NULL OFFSET ? LIMIT ?", new Object[]{page * pageSize, pageSize}, MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM towatchmedia WHERE userId = ? AND watchDate IS NULL", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements,page,pageSize,totalCount);
    }

    @Override
    public Optional<Integer> getToWatchMediaCount(int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM towatchmedia WHERE userId = ? AND watchDate IS NULL", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst();
    }
}
