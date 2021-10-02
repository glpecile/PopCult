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

    private static final RowMapper<MediaList> MEDIA_LIST_ROW_MAPPER = RowMappers.MEDIA_LIST_ROW_MAPPER;

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
        return new PageContainer<>(elements, page, pageSize, totalCount);
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
    public void deleteListFromFav(int mediaListId, int userId) {
        jdbcTemplate.update("DELETE FROM favoritelists WHERE medialistid = ? AND userid = ?", mediaListId, userId);
    }

    @Override
    public boolean isFavoriteList(int mediaListId, int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) FROM favoritelists WHERE medialistid = ? AND userid = ?", new Object[]{mediaListId, userId}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0) > 0;
    }

    @Override
    public PageContainer<MediaList> getUserFavoriteLists(int userId, int page, int pageSize) {
        List<MediaList> elements = jdbcTemplate.query("SELECT * FROM favoritelists JOIN medialist ON favoritelists.medialistid = medialist.medialistid WHERE favoritelists.userid = ? OFFSET ? LIMIT ?", new Object[]{userId, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritelists JOIN medialist ON favoritelists.medialistid = medialist.medialistid WHERE favoritelists.userid = ?", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public PageContainer<MediaList> getUserPublicFavoriteLists(int userId, int page, int pageSize) {
        List<MediaList> elements = jdbcTemplate.query("SELECT * FROM favoritelists JOIN medialist ON favoritelists.medialistid = medialist.medialistid WHERE favoritelists.userid = ? AND visibility = ? OFFSET ? LIMIT ?", new Object[]{userId, true, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritelists JOIN medialist ON favoritelists.medialistid = medialist.medialistid WHERE favoritelists.userid = ? AND visibility = ?", new Object[]{userId, true}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public Optional<Integer> getFavoriteListsCount(int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritelists WHERE userId = ?", new Object[]{userId}, COUNT_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public PageContainer<MediaList> getRecommendationsBasedOnFavLists(int userId, int page, int pageSize) {
//        List<MediaList> recommendedList = jdbcTemplate.query("((SELECT * FROM medialist NATURAL JOIN (SELECT medialistid FROM favoritelists WHERE userid IN (SELECT l.userid FROM favoritelists f JOIN favoritelists l ON f.medialistid = l.medialistid WHERE f.userid = ?) " +
//                "EXCEPT SELECT medialistId FROM favoritelists WHERE userid = ?) as AUX " +
//                "UNION (SELECT medialist.* FROM medialist LEFT JOIN favoritelists ON medialist.medialistid = favoritelists.medialistid WHERE visibility = ? " +
//                "GROUP BY medialist.medialistid ORDER BY COUNT(favoritelists.userid) DESC)) " +
//                "EXCEPT SELECT m.* FROM medialist m RIGHT JOIN favoritelists f2 ON m.userid=f2.userid WHERE f2.userid = ?) OFFSET ? LIMIT ?", new Object[]{userId, userId, true, userId, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
//        int count = jdbcTemplate.query("SELECT COUNT(*) FROM ((SELECT * FROM medialist NATURAL JOIN (SELECT medialistid FROM favoritelists WHERE userid IN (SELECT l.userid FROM favoritelists f JOIN favoritelists l ON f.medialistid = l.medialistid WHERE f.userid = ?)  EXCEPT SELECT medialistId FROM favoritelists WHERE userid = ?) as AUX UNION (SELECT medialist.* FROM medialist LEFT JOIN favoritelists ON medialist.medialistid = favoritelists.medialistid WHERE visibility = ? GROUP BY medialist.medialistid ORDER BY COUNT(favoritelists.userid) DESC)) EXCEPT SELECT m.* FROM medialist m RIGHT JOIN favoritelists f2 ON m.userid=f2.userid WHERE f2.userid = ?) as AUX", new Object[]{userId, userId, true, userId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        List<MediaList> recommendedList = jdbcTemplate.query("((SELECT * FROM medialist NATURAL JOIN (SELECT medialistid FROM favoritelists WHERE userid IN (SELECT l.userid FROM favoritelists f JOIN favoritelists l ON f.medialistid = l.medialistid WHERE f.userid = ?) " +
                "EXCEPT SELECT medialistId FROM favoritelists WHERE userid = ?) as AUX)  OFFSET ? LIMIT ?)", new Object[]{userId, userId, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
        int count = jdbcTemplate.query("SELECT COUNT(*) FROM (medialist NATURAL JOIN (SELECT medialistid FROM favoritelists WHERE userid IN (SELECT l.userid FROM favoritelists f JOIN favoritelists l ON f.medialistid = l.medialistid WHERE f.userid = ?) EXCEPT SELECT medialistId FROM favoritelists WHERE userid = ?) as AUX)", new Object[]{userId, userId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);

        return new PageContainer<>(recommendedList, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> getMostLikedLists(int page, int pageSize) {
        List<MediaList> mostLikedLists = jdbcTemplate.query("SELECT medialist.* FROM medialist LEFT JOIN favoritelists ON medialist.medialistid = favoritelists.medialistid WHERE visibility = ? GROUP BY medialist.medialistid ORDER BY COUNT(favoritelists.userid) DESC OFFSET ? LIMIT ?", new Object[]{true, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
        int listCount = jdbcTemplate.query("SELECT COUNT(*) FROM medialist WHERE visibility = ?", new Object[]{true}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(mostLikedLists, page, pageSize, listCount);
    }

    @Override
    public PageContainer<Media> getMostLikedMedia(int page, int pageSize) {
        List<Media> mediaList = jdbcTemplate.query("SELECT media.* FROM media LEFT JOIN favoritemedia ON media.mediaId = favoritemedia.mediaId GROUP BY media.mediaid ORDER BY COUNT(favoritemedia.userid) DESC OFFSET ? LIMIT ?", new Object[]{pageSize * page, pageSize}, MEDIA_ROW_MAPPER);
        int mediaListCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM media", COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(mediaList, page, pageSize, mediaListCount);

    }

    @Override
    public PageContainer<Media> getMostLikedMedia(int mediaType, int page, int pageSize) {
        List<Media> likedMoviesList = jdbcTemplate.query("SELECT media.* FROM media LEFT JOIN favoritemedia ON media.mediaId = favoritemedia.mediaId WHERE type = ? GROUP BY media.mediaid ORDER BY COUNT(favoritemedia.userid) DESC OFFSET ? LIMIT ?", new Object[]{mediaType, pageSize * page, pageSize}, MEDIA_ROW_MAPPER);
        int moviesCunt = jdbcTemplate.query("SELECT COUNT(*) AS count FROM media WHERE type = ?", new Object[]{mediaType}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(likedMoviesList, page, pageSize, moviesCunt);
    }

    @Override
    public PageContainer<Media> getRecommendationsBasedOnFavMedia(int mediaType, int userId, int page, int pageSize) {
//        List<Media> recommendedMedia = jdbcTemplate.query("(SELECT * FROM media NATURAL JOIN (SELECT mediaid FROM favoritemedia WHERE userid IN " +
//                "(SELECT m.userid FROM favoritemedia f JOIN favoritemedia m ON f.mediaid = m.mediaid WHERE f.userid = ?) " +
//                "EXCEPT SELECT mediaId FROM favoritemedia WHERE userid = ?) as AUX " +
//                "UNION (SELECT media.* FROM media LEFT JOIN favoritemedia ON media.mediaId = favoritemedia.mediaId WHERE type = ? " +
//                "GROUP BY media.mediaid ORDER BY COUNT(favoritemedia.userid) DESC) " +
//                "EXCEPT SELECT media.* FROM media NATURAL JOIN favoritemedia f2 WHERE f2.userid = ?) OFFSET ? LIMIT ?", new Object[]{userId, userId, mediaType, userId, page * pageSize, pageSize}, MEDIA_ROW_MAPPER) ;
        List<Media> recommendedMedia = jdbcTemplate.query("(SELECT * FROM media NATURAL JOIN (SELECT mediaid FROM favoritemedia WHERE userid IN " +
                "(SELECT m.userid FROM favoritemedia f JOIN favoritemedia m ON f.mediaid = m.mediaid WHERE f.userid = ?) " +
                "EXCEPT SELECT mediaId FROM favoritemedia WHERE userid = ?) as AUX WHERE type = ?) OFFSET ? LIMIT ?", new Object[]{userId, userId, mediaType, page * pageSize, pageSize}, MEDIA_ROW_MAPPER);
//        int count = jdbcTemplate.query("SELECT COUNT(*) FROM (SELECT * FROM media NATURAL JOIN (SELECT mediaid FROM favoritemedia WHERE userid IN (SELECT m.userid FROM favoritemedia f JOIN favoritemedia m ON f.mediaid = m.mediaid WHERE f.userid = ?)  EXCEPT SELECT mediaId FROM favoritemedia WHERE userid = ?) as AUX UNION (SELECT media.* FROM media LEFT JOIN favoritemedia ON media.mediaId = favoritemedia.mediaId WHERE type = ? GROUP BY media.mediaid ORDER BY COUNT(favoritemedia.userid) DESC) EXCEPT SELECT media.* FROM media NATURAL JOIN favoritemedia f2 WHERE f2.userid = ?) as AUX", new Object[]{userId, userId, mediaType, userId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        int count = jdbcTemplate.query(" (SELECT COUNT(*) AS COUNT FROM (media NATURAL JOIN (SELECT mediaid FROM favoritemedia WHERE userid IN (SELECT m.userid FROM favoritemedia f JOIN favoritemedia m ON f.mediaid = m.mediaid WHERE f.userid = ?) EXCEPT SELECT mediaId FROM favoritemedia WHERE userid = ?) as AUX) WHERE type = ?)", new Object[]{userId, userId, mediaType}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(recommendedMedia, page, pageSize, count);
    }
}
