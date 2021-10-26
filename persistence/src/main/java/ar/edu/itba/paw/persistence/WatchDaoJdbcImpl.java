//package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.interfaces.WatchDao;
//import ar.edu.itba.paw.models.PageContainer;
//import ar.edu.itba.paw.models.media.Media;
//import ar.edu.itba.paw.models.media.WatchedMedia;
//import ar.edu.itba.paw.models.user.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.util.*;
//
//@Repository
//public class WatchDaoJdbcImpl implements WatchDao {
//    private final JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert toWatchMediaJdbcInsert;
//
//    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;
//
//    private static final RowMapper<Media> MEDIA_ROW_MAPPER = RowMappers.MEDIA_ROW_MAPPER;
//
//    private static final RowMapper<WatchedMedia> WATCHED_MEDIA_ROW_MAPPER = RowMappers.WATCHED_MEDIA_ROW_MAPPER;
//
//
//    @Autowired
//    public WatchDaoJdbcImpl(final DataSource ds) {
//        jdbcTemplate = new JdbcTemplate(ds);
//        toWatchMediaJdbcInsert = new SimpleJdbcInsert(ds).withTableName("towatchmedia");
//    }
//
//    @Override
//    public void addWatchMedia(Media media, User user, Date date) {
//        Map<String, Object> data = new HashMap<>();
//        data.put("mediaId", media);
//        data.put("userId", user);
//        data.put("watchDate", date);
//        toWatchMediaJdbcInsert.execute(data);
//    }
//
//    @Override
//    public void deleteWatchedMedia(Media media, User user) {
//        jdbcTemplate.update("DELETE FROM towatchmedia WHERE mediaId = ? AND userId = ? AND watchDate IS NOT NULL", media, user);
//
//    }
//
//    @Override
//    public void deleteToWatchMedia(Media media, User user) {
//        jdbcTemplate.update("DELETE FROM towatchmedia WHERE mediaId = ? AND userId = ? AND watchDate IS NULL", media, user);
//    }
//
//    @Override
//    public void updateWatchedMediaDate(Media media, User user, Date date) {
//        jdbcTemplate.update("UPDATE towatchmedia SET watchdate = ? WHERE mediaid = ? AND userid = ? AND watchdate IS NOT NULL", date, media, user);
//    }
//
//    @Override
//    public boolean isWatched(Media media, User user) {
//        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM towatchmedia WHERE mediaId = ? AND userid = ? AND watchDate IS NOT NULL", new Object[]{media, user}, COUNT_ROW_MAPPER)
//                .stream().findFirst().orElse(0) > 0;
//    }
//
//    @Override
//    public boolean isToWatch(Media media, User user) {
//        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM towatchmedia WHERE mediaId = ? AND userid = ? AND watchDate IS NULL", new Object[]{media, user}, COUNT_ROW_MAPPER)
//                .stream().findFirst().orElse(0) > 0;
//    }
//
//    @Override
//    public PageContainer<WatchedMedia> getWatchedMediaId(User user, int page, int pageSize) {
//        List<WatchedMedia> elements = jdbcTemplate.query("SELECT * FROM towatchmedia NATURAL JOIN media WHERE userId = ? AND watchDate IS NOT NULL ORDER BY watchDate DESC OFFSET ? LIMIT ?", new Object[]{user, page * pageSize, pageSize}, WATCHED_MEDIA_ROW_MAPPER);
//        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM towatchmedia WHERE userId = ? AND watchDate IS NOT NULL", new Object[]{user}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//        return new PageContainer<>(elements, page, pageSize, totalCount);
//    }
//
//    @Override
//    public PageContainer<Media> getToWatchMediaId(User user, int page, int pageSize) {
//        List<Media> elements = jdbcTemplate.query("SELECT * FROM towatchmedia NATURAL JOIN media WHERE userId = ? AND watchDate IS NULL ORDER BY watchDate DESC OFFSET ? LIMIT ?", new Object[]{user, page * pageSize, pageSize}, MEDIA_ROW_MAPPER);
//        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM towatchmedia WHERE userId = ? AND watchDate IS NULL", new Object[]{user}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//        return new PageContainer<>(elements,page,pageSize,totalCount);
//    }
//
//}
