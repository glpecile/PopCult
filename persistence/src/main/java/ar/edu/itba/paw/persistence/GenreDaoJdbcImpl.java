package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.GenreDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Genre;
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
public class GenreDaoJdbcImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert genrejdbcInsert;

    private static final RowMapper<String> STRING_NAME_ROW_MAPPER = RowMappers.STRING_NAME_ROW_MAPPER;


    private static final RowMapper<Media> MEDIA_ROW_MAPPER = RowMappers.MEDIA_ROW_MAPPER;

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;

    @Autowired
    public GenreDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        genrejdbcInsert = new SimpleJdbcInsert(ds).withTableName("genre").usingGeneratedKeyColumns("genreId");
    }

    @Override
    public List<String> getGenreByMediaId(int mediaId) {
        return jdbcTemplate.query("SELECT name FROM mediaGenre NATURAL JOIN genre WHERE mediaId = ?", new Object[]{mediaId}, STRING_NAME_ROW_MAPPER);
    }

    @Override
    public PageContainer<Media> getMediaByGenre(int genreId, int page, int pageSize) {
        List<Media> elements = jdbcTemplate.query("SELECT * FROM mediaGenre NATURAL JOIN media WHERE genreId = ? OFFSET ? LIMIT ?", new Object[] {genreId, pageSize * page, pageSize},
                MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM mediaGenre WHERE genreId = ?", new Object[] {genreId}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<>(elements,page,pageSize,totalCount);
    }

    @Override
    public Optional<Integer> getMediaCountByGenre(int genreId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM mediaGenre WHERE genreId = ?", new Object[] {genreId}, COUNT_ROW_MAPPER)
                .stream().findFirst();
    }

    public void loadGenres() {
        Map<String, Object> data = new HashMap<>();
        jdbcTemplate.execute("TRUNCATE genre CASCADE");
        for (Genre genre : Genre.values()) {
            data.put("name", genre.getGenre());
            genrejdbcInsert.execute(data);
        }
    }
}
