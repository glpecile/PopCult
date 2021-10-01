package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FavoriteDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
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
public class FavoriteDaoJdbcImpl implements FavoriteDao {


    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert favoriteMediaJdbcInsert;
    private final SimpleJdbcInsert favoriteListsJdbcInsert;

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;

    private static final RowMapper<Integer> MEDIA_ID_ROW_MAPPER = RowMappers.MEDIA_ID_ROW_MAPPER;

    private static final RowMapper<Integer> MEDIA_LIST_ID_MAPPER = RowMappers.MEDIA_LIST_ID_MAPPER;

    private static final RowMapper<Media> MEDIA_ROW_MAPPER = RowMappers.MEDIA_ROW_MAPPER;

    private static final RowMapper<MediaList> MEDIA_LIST_MAPPER = RowMappers.MEDIA_LIST_ROW_MAPPER;
    @Autowired
    public FavoriteDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        favoriteMediaJdbcInsert = new SimpleJdbcInsert(ds).withTableName("favoritemedia");
        favoriteListsJdbcInsert = new SimpleJdbcInsert(ds).withTableName("favoritelists");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS favoritemedia(" +
                "userId INT NOT NULL," +
                "mediaId INT NOT NULL," +
                "FOREIGN KEY(mediaId) REFERENCES media(mediaId) ON DELETE CASCADE," +
                "FOREIGN KEY(userId) REFERENCES users(userId) ON DELETE CASCADE)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS favoritelists(" +
                "userId INT NOT NULL," +
                "mediaListId INT NOT NULL," +
                "FOREIGN KEY(mediaListId) REFERENCES medialist(mediaListId) ON DELETE CASCADE," +
                "FOREIGN KEY(userId) REFERENCES users(userId) ON DELETE CASCADE)");
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
    public PageContainer<Media> getUserFavoriteMedia(int userId, int page, int pageSize) {
        List<Media> elements = jdbcTemplate.query("SELECT * FROM favoritemedia NATURAL JOIN media WHERE userId = ? OFFSET ? LIMIT ?", new Object[]{userId, page * pageSize, pageSize}, MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritemedia WHERE userId = ?", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements,page,pageSize,totalCount);
    }

    @Override
    public Optional<Integer> getFavoriteMediaCount(int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritemedia WHERE userId = ?", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void addListToFav(int mediaListId, int userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("mediaListId", mediaListId);
        data.put("userId", userId);
        favoriteListsJdbcInsert.execute(data);
    }

    @Override
    public void deleteListFromFav(int mediaListId,int userId) {
        jdbcTemplate.update("DELETE FROM favoritelists WHERE medialistid = ? AND userid = ?", mediaListId, userId);
    }

    @Override
    public boolean isFavoriteList(int mediaListId, int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) FROM favoritelists WHERE medialistid = ? AND userid = ?", new Object[]{mediaListId, userId}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0) > 0;
    }

    @Override
    public PageContainer<MediaList> getUserFavoriteLists(int userId, int page, int pageSize) {
        List<MediaList> elements = jdbcTemplate.query("SELECT * FROM favoritelists JOIN medialist ON favoritelists.medialistid = medialist.medialistid WHERE favoritelists.userid = ? OFFSET ? LIMIT ?", new Object[]{userId, page * pageSize, pageSize}, MEDIA_LIST_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritelists JOIN medialist ON favoritelists.medialistid = medialist.medialistid WHERE favoritelists.userid = ?", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements,page,pageSize,totalCount);
    }

    @Override
    public PageContainer<MediaList> getUserPublicFavoriteLists(int userId, int page, int pageSize) {
        List<MediaList> elements = jdbcTemplate.query("SELECT * FROM favoritelists JOIN medialist ON favoritelists.medialistid = medialist.medialistid WHERE favoritelists.userid = ? AND visibility = ? OFFSET ? LIMIT ?", new Object[]{userId, true, page * pageSize, pageSize}, MEDIA_LIST_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritelists JOIN medialist ON favoritelists.medialistid = medialist.medialistid WHERE favoritelists.userid = ? AND visibility = ?", new Object[]{userId, true}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements,page,pageSize,totalCount);
    }

    @Override
    public Optional<Integer> getFavoriteListsCount(int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritelists WHERE userId = ?", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst();
    }
}
