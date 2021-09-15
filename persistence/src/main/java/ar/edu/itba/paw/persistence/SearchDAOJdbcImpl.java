package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SearchDAO;
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


    private static final RowMapper<Media> MEDIA_ROW_MAPPER =
            (rs, rowNum) -> new Media(
                    rs.getInt("mediaId"),
                    rs.getInt("type"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("image"),
                    rs.getInt("length"),
                    rs.getDate("releaseDate"),
                    rs.getInt("seasons"),
                    rs.getInt("country"));

    private static final RowMapper<Integer> COUNT_ROW_MAPPER =
            (rs, rowNum) -> rs.getInt("count");

    @Autowired
    public SearchDAOJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType){
        return jdbcTemplate.query("SELECT * FROM media WHERE title ILIKE CONCAT('%', ?, '%') AND type = ?  OFFSET ? LIMIT ?", new Object[]{title, page, pageSize, mediaType},MEDIA_ROW_MAPPER);
    }

    @Override
    public Optional<Integer> getCountSearchMediaByTitle(String title, int mediaType) {
        return jdbcTemplate.query("SELECT COUNT(*) FROM media WHERE title ILIKE CONCAT('%', ?, '%') AND type = ?", new Object[]{title, mediaType},COUNT_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<Media> searchMediaByTitle(String title, int page, int pageSize) {
        return jdbcTemplate.query("SELECT * FROM media WHERE title ILIKE CONCAT('%', ?, '%')  OFFSET ? LIMIT ?", new Object[]{title, page, pageSize},MEDIA_ROW_MAPPER);
    }

    @Override
    public Optional<Integer> getCountSearchMediaByTitle(String title){
        return jdbcTemplate.query("SELECT COUNT(*) FROM media WHERE title ILIKE CONCAT('%', ?, '%')", new Object[]{title},COUNT_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType, int sort) {
        return jdbcTemplate.query("SELECT * FROM media WHERE title ILIKE CONCAT('%', ?, '%') ORDER BY ? OFFSET ? LIMIT ?", new Object[]{title, page, pageSize, SortType.values()[sort].nameMedia},MEDIA_ROW_MAPPER);
    }
}
