package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SearchDAO;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.search.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class SearchDAOJdbcImpl implements SearchDAO {
    private final JdbcTemplate jdbcTemplate;


    private static final RowMapper<Media> MEDIA_ROW_MAPPER = RowMappers.MEDIA_ROW_MAPPER;

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;

    private static final RowMapper<MediaList> MEDIA_LIST_ROW_MAPPER = RowMappers.MEDIA_LIST_ROW_MAPPER;

    private static final RowMapper<Integer> MEDIA_ID_ROW_MAPPER = RowMappers.MEDIA_ID_ROW_MAPPER;

    @Autowired
    public SearchDAOJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, int sort) {
        String orderBy = "ORDER BY " + SortType.values()[sort].nameMedia;
        List<Media> elements = jdbcTemplate.query("SELECT * FROM media WHERE title ILIKE CONCAT('%', ?, '%')" + orderBy + " OFFSET ? LIMIT ?", new Object[]{title, page * pageSize, pageSize}, MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) FROM media WHERE title ILIKE CONCAT('%', ?, '%')", new Object[]{title}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public Optional<Integer> getCountSearchMediaByTitle(String title, int mediaType) {
        return jdbcTemplate.query("SELECT COUNT(*) FROM media WHERE title ILIKE CONCAT('%', ?, '%') AND type = ?", new Object[]{title, mediaType}, COUNT_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize) {
        List<Media> elements = jdbcTemplate.query("SELECT * FROM media WHERE title ILIKE CONCAT('%', ?, '%')  OFFSET ? LIMIT ?", new Object[]{title, page, pageSize}, MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) FROM media WHERE title ILIKE CONCAT('%', ?, '%')", new Object[]{title}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public Optional<Integer> getCountSearchMediaByTitle(String title) {
        return jdbcTemplate.query("SELECT COUNT(*) FROM media WHERE title ILIKE CONCAT('%', ?, '%')", new Object[]{title}, COUNT_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType, int sort) {
        String orderBy = " ORDER BY " + SortType.values()[sort].nameMedia;
        List<Media> elements = jdbcTemplate.query("SELECT * FROM media WHERE title ILIKE CONCAT('%', ?, '%') AND type = ? " + orderBy + " OFFSET ? LIMIT ?", new Object[]{title, mediaType, page, pageSize}, MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) FROM media WHERE title ILIKE CONCAT('%', ?, '%') AND type = ? ", new Object[]{title, mediaType}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public PageContainer<MediaList> searchListMediaByName(String name, int page, int pageSize, int sort) {
        String orderBy = "ORDER BY " + SortType.values()[sort].nameMediaList;
        List<MediaList> elements = jdbcTemplate.query("SELECT * FROM medialist WHERE medialist.listname ILIKE CONCAT('%', ?, '%') "
                + orderBy + " OFFSET ? LIMIT ?", new Object[]{name,
                page, pageSize}, MEDIA_LIST_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) FROM medialist WHERE medialist.listname ILIKE CONCAT('%', ?, '%')", new Object[]{name}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public PageContainer<Media> searchMediaByTitleNotInList(int listId, String title, int page, int pageSize, int mediaType, int sort) {
        String orderBy = " ORDER BY " + SortType.values()[sort].nameMedia;
        List<Media> elements = jdbcTemplate.query("SELECT * FROM media WHERE title ILIKE CONCAT('%', ?, '%') AND type = ?  AND mediaid NOT IN (SELECT mediaid FROM listelement WHERE medialistid = ?)" + orderBy + " OFFSET ? LIMIT ?", new Object[]{title, mediaType, listId, page, pageSize}, MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) FROM media WHERE title ILIKE CONCAT('%', ?, '%') AND type = ? AND mediaid NOT IN (SELECT mediaid FROM listelement WHERE medialistid = ?)", new Object[]{title, mediaType, listId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType, int sort, int genre) {
        String orderBy = " ORDER BY " + SortType.values()[sort].nameMedia;
        List<Media> elements = jdbcTemplate.query("SELECT * FROM media NATURAL JOIN mediagenre WHERE title ILIKE CONCAT('%', ?, '%') AND type = ? AND genreid = ? " + orderBy + " OFFSET ? LIMIT ?", new Object[]{title, mediaType,genre,page, pageSize},MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) FROM media NATURAL JOIN mediagenre WHERE title ILIKE CONCAT('%', ?, '%') AND type = ? AND genreid = ? ", new Object[]{title,mediaType, genre},COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements,page,pageSize,totalCount);
    }


    @Override
    public PageContainer<MediaList> searchListMediaByName(String name, int page, int pageSize, int sort, int genre, int minMatches) {
        String orderBy = "ORDER BY " + SortType.values()[sort].nameMediaList;
        List<MediaList> elements = jdbcTemplate.query("SELECT DISTINCT medialist.medialistid, medialist.userid, listname, description, creationdate, visibility, collaborative " +
                "FROM mediaGenre NATURAL JOIN listelement NATURAL JOIN mediaList " +
                "WHERE medialist.listname ILIKE CONCAT('%', ?, '%') AND genreid = ?" +
                " GROUP BY medialist.medialistid, medialist.userid, listname, description, creationdate " +
                " HAVING COUNT(mediaid) >= ? "
                + orderBy + " OFFSET ? LIMIT ?", new Object[]{name, genre, minMatches,
                page, pageSize},MEDIA_LIST_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) FROM medialist WHERE medialist.listname ILIKE CONCAT('%', ?, '%')", new Object[]{name},COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements,page,pageSize,totalCount);
    }
}
