//package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.interfaces.FavoriteDao;
//import ar.edu.itba.paw.models.PageContainer;
//import ar.edu.itba.paw.models.lists.MediaList;
//import ar.edu.itba.paw.models.media.Media;
//import ar.edu.itba.paw.models.media.MediaType;
//import ar.edu.itba.paw.models.user.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Repository
//public class FavoriteDaoJdbcImpl implements FavoriteDao {
//
//
//    private final JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert favoriteMediaJdbcInsert;
//    private final SimpleJdbcInsert favoriteListsJdbcInsert;
//
//    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;
//
//    private static final RowMapper<Integer> MEDIA_ID_ROW_MAPPER = RowMappers.MEDIA_ID_ROW_MAPPER;
//
//    private static final RowMapper<Integer> MEDIA_LIST_ID_MAPPER = RowMappers.MEDIA_LIST_ID_MAPPER;
//
//    private static final RowMapper<Media> MEDIA_ROW_MAPPER = RowMappers.MEDIA_ROW_MAPPER;
//
//    private static final RowMapper<MediaList> MEDIA_LIST_ROW_MAPPER = RowMappers.MEDIA_LIST_ROW_MAPPER;
//
//    @Autowired
//    public FavoriteDaoJdbcImpl(final DataSource ds) {
//        jdbcTemplate = new JdbcTemplate(ds);
//        favoriteMediaJdbcInsert = new SimpleJdbcInsert(ds).withTableName("favoritemedia");
//        favoriteListsJdbcInsert = new SimpleJdbcInsert(ds).withTableName("favoritelists");
//    }
//
//    @Override
//    public void addMediaToFav(Media media, User user) {
//        Map<String, Object> data = new HashMap<>();
//        data.put("mediaId", media);
//        data.put("userId", user);
//        favoriteMediaJdbcInsert.execute(data);
//    }
//
//    @Override
//    public void deleteMediaFromFav(Media media, User user) {
//        jdbcTemplate.update("DELETE FROM favoritemedia WHERE mediaid = ? AND userid = ?", media, user);
//    }
//
//    @Override
//    public boolean isFavorite(Media media, User user) {
//        return jdbcTemplate.query("SELECT COUNT(*) FROM favoritemedia WHERE mediaid = ? AND userid = ?", new Object[]{media, user}, COUNT_ROW_MAPPER)
//                .stream().findFirst().orElse(0) > 0;
//    }
//
//    @Override
//    public PageContainer<Media> getUserFavoriteMedia(User user, int page, int pageSize) {
//        List<Media> elements = jdbcTemplate.query("SELECT * FROM favoritemedia NATURAL JOIN media WHERE userId = ? OFFSET ? LIMIT ?", new Object[]{user, page * pageSize, pageSize}, MEDIA_ROW_MAPPER);
//        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritemedia WHERE userId = ?", new Object[]{user}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//        return new PageContainer<>(elements, page, pageSize, totalCount);
//    }
//
//    @Override
//    public Optional<Integer> getFavoriteMediaCount(User user) {
//        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritemedia WHERE userId = ?", new Object[]{user}, COUNT_ROW_MAPPER).stream().findFirst();
//    }
//
//    @Override
//    public void addListToFav(MediaList mediaList, User user) {
//        Map<String, Object> data = new HashMap<>();
//        data.put("mediaListId", mediaList);
//        data.put("userId", user);
//        favoriteListsJdbcInsert.execute(data);
//    }
//
//    @Override
//    public void deleteListFromFav(MediaList mediaList, User user) {
//        jdbcTemplate.update("DELETE FROM favoritelists WHERE medialistid = ? AND userid = ?", mediaList, user);
//    }
//
//    @Override
//    public boolean isFavoriteList(MediaList mediaList, User user) {
//        return jdbcTemplate.query("SELECT COUNT(*) FROM favoritelists WHERE medialistid = ? AND userid = ?", new Object[]{mediaList, user}, COUNT_ROW_MAPPER)
//                .stream().findFirst().orElse(0) > 0;
//    }
//
//    @Override
//    public PageContainer<MediaList> getUserFavoriteLists(User user, int page, int pageSize) {
//        List<MediaList> elements = jdbcTemplate.query("SELECT * FROM favoritelists JOIN medialist ON favoritelists.medialistid = medialist.medialistid WHERE favoritelists.userid = ? OFFSET ? LIMIT ?", new Object[]{user, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
//        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritelists JOIN medialist ON favoritelists.medialistid = medialist.medialistid WHERE favoritelists.userid = ?", new Object[]{user}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//        return new PageContainer<>(elements, page, pageSize, totalCount);
//    }
//
//    @Override
//    public PageContainer<MediaList> getUserPublicFavoriteLists(User user, int page, int pageSize) {
//        List<MediaList> elements = jdbcTemplate.query("SELECT * FROM favoritelists JOIN medialist ON favoritelists.medialistid = medialist.medialistid WHERE favoritelists.userid = ? AND visibility = ? OFFSET ? LIMIT ?", new Object[]{user, true, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
//        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritelists JOIN medialist ON favoritelists.medialistid = medialist.medialistid WHERE favoritelists.userid = ? AND visibility = ?", new Object[]{user, true}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//        return new PageContainer<>(elements, page, pageSize, totalCount);
//    }
//
//    @Override
//    public Optional<Integer> getFavoriteListsCount(User user) {
//        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM favoritelists WHERE userId = ?", new Object[]{user}, COUNT_ROW_MAPPER).stream().findFirst();
//    }
//
//    @Override
//    public PageContainer<MediaList> getRecommendationsBasedOnFavLists(User user, int page, int pageSize) {
//        List<MediaList> recommendedList = jdbcTemplate.query("((SELECT * FROM medialist NATURAL JOIN (SELECT medialistid FROM favoritelists WHERE userid IN (SELECT l.userid FROM favoritelists f JOIN favoritelists l ON f.medialistid = l.medialistid WHERE f.userid = ?) " +
//                "EXCEPT SELECT m.medialistid FROM medialist m RIGHT JOIN favoritelists f ON m.userid=f.userid WHERE f.userid = ?) as AUX)  OFFSET ? LIMIT ?)", new Object[]{user, user, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
//        int count = jdbcTemplate.query("SELECT COUNT(*) FROM (medialist NATURAL JOIN (SELECT medialistid FROM favoritelists WHERE userid IN (SELECT l.userid FROM favoritelists f JOIN favoritelists l ON f.medialistid = l.medialistid WHERE f.userid = ?) EXCEPT SELECT medialistId FROM favoritelists WHERE userid = ?) as AUX)", new Object[]{user, user}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//
//        return new PageContainer<>(recommendedList, page, pageSize, count);
//    }
//
//    @Override
//    public PageContainer<MediaList> getMostLikedLists(int page, int pageSize) {
//        List<MediaList> mostLikedLists = jdbcTemplate.query("SELECT medialist.* FROM medialist LEFT JOIN favoritelists ON medialist.medialistid = favoritelists.medialistid WHERE visibility = ? GROUP BY medialist.medialistid ORDER BY COUNT(favoritelists.userid) DESC OFFSET ? LIMIT ?", new Object[]{true, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
//        int listCount = jdbcTemplate.query("SELECT COUNT(*) FROM medialist WHERE visibility = ?", new Object[]{true}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//        return new PageContainer<>(mostLikedLists, page, pageSize, listCount);
//    }
//
//    @Override
//    public PageContainer<Media> getMostLikedMedia(int page, int pageSize) {
//        List<Media> mediaList = jdbcTemplate.query("SELECT media.* FROM media LEFT JOIN favoritemedia ON media.mediaId = favoritemedia.mediaId GROUP BY media.mediaid ORDER BY COUNT(favoritemedia.userid) DESC OFFSET ? LIMIT ?", new Object[]{pageSize * page, pageSize}, MEDIA_ROW_MAPPER);
//        int mediaListCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM media", COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//        return new PageContainer<>(mediaList, page, pageSize, mediaListCount);
//
//    }
//
//    @Override
//    public PageContainer<Media> getMostLikedMedia(MediaType mediaType, int page, int pageSize) {
//        List<Media> likedMoviesList = jdbcTemplate.query("SELECT media.* FROM media LEFT JOIN favoritemedia ON media.mediaId = favoritemedia.mediaId WHERE type = ? GROUP BY media.mediaid ORDER BY COUNT(favoritemedia.userid) DESC OFFSET ? LIMIT ?", new Object[]{mediaType, pageSize * page, pageSize}, MEDIA_ROW_MAPPER);
//        int moviesCunt = jdbcTemplate.query("SELECT COUNT(*) AS count FROM media WHERE type = ?", new Object[]{mediaType}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//        return new PageContainer<>(likedMoviesList, page, pageSize, moviesCunt);
//    }
//
//    @Override
//    public int getLikesFromList(MediaList mediaList) {
//        return jdbcTemplate.query("SELECT COUNT(*) FROM favoritelists WHERE medialistid = ?", new Object[]{mediaList}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//    }
//
//    @Override
//    public int getLikesFromMedia(Media media) {
//        return jdbcTemplate.query("SELECT COUNT(*) FROM favoritemedia WHERE mediaid = ?", new Object[]{media}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//    }
//
//    @Override
//    public PageContainer<MediaList> getMostLikedLists(User user, int page, int pageSize) {
//        List<MediaList> mostLikedLists = jdbcTemplate.query("SELECT medialist.* FROM medialist LEFT JOIN favoritelists ON medialist.medialistid = favoritelists.medialistid WHERE visibility = ? AND medialist.userid != ? GROUP BY medialist.medialistid ORDER BY COUNT(favoritelists.userid) DESC OFFSET ? LIMIT ?", new Object[]{true, user,page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
//        int listCount = jdbcTemplate.query("SELECT COUNT(*) FROM medialist WHERE visibility = ? AND userid != ?", new Object[]{true, user}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//        return new PageContainer<>(mostLikedLists, page, pageSize, listCount);
//    }
//
//    @Override
//    public PageContainer<Media> getRecommendationsBasedOnFavMedia(MediaType mediaType, User user, int page, int pageSize) {
//        List<Media> recommendedMedia = jdbcTemplate.query("(SELECT * FROM media NATURAL JOIN (SELECT mediaid FROM favoritemedia WHERE userid IN " +
//                "(SELECT m.userid FROM favoritemedia f JOIN favoritemedia m ON f.mediaid = m.mediaid WHERE f.userid = ?) " +
//                "EXCEPT SELECT mediaId FROM favoritemedia WHERE userid = ?) as AUX WHERE type = ?) OFFSET ? LIMIT ?", new Object[]{user, user, mediaType, page * pageSize, pageSize}, MEDIA_ROW_MAPPER);
//        int count = jdbcTemplate.query(" (SELECT COUNT(*) AS COUNT FROM (media NATURAL JOIN (SELECT mediaid FROM favoritemedia WHERE userid IN (SELECT m.userid FROM favoritemedia f JOIN favoritemedia m ON f.mediaid = m.mediaid WHERE f.userid = ?) EXCEPT SELECT mediaId FROM favoritemedia WHERE userid = ?) as AUX) WHERE type = ?)", new Object[]{user, user, mediaType}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
//        return new PageContainer<>(recommendedMedia, page, pageSize, count);
//    }
//}
