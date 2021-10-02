package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.MediaDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class MediaDaoJdbcImpl implements MediaDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Media> MEDIA_ROW_MAPPER = RowMappers.MEDIA_ROW_MAPPER;

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;

    @Autowired
    public MediaDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("media").usingGeneratedKeyColumns("mediaId");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS media (" +
                "mediaId SERIAL PRIMARY KEY," +
                "type INT NOT NULL," +
                "title VARCHAR(100) NOT NULL, " +
                "description TEXT," +
                "image TEXT," +
                "length INT," +
                "releaseDate DATE," +
                "seasons INT," +
                "country INT)");
    }

    @Override
    public Optional<Media> getById(int mediaId) {
        return jdbcTemplate.query("SELECT * FROM media WHERE mediaId = ?", new Object[]{mediaId}, MEDIA_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<Media> getById(List<Integer> mediaIds) {
        if (mediaIds.size() == 0)
            return new ArrayList<>();
        String inSql = String.join(",", Collections.nCopies(mediaIds.size(), "?"));
        return jdbcTemplate.query(
                String.format("SELECT * FROM media WHERE mediaId IN (%s)", inSql),
                mediaIds.toArray(), MEDIA_ROW_MAPPER);

    }

    @Override
    public PageContainer<Media> getMediaList(int page, int pageSize) {
        List<Media> elements = jdbcTemplate.query("SELECT * FROM media OFFSET ? LIMIT ? ",
                new Object[]{pageSize * page, pageSize}, MEDIA_ROW_MAPPER);
        int mediaCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM media",
                COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, mediaCount);
    }

    @Override
    public PageContainer<Media> getMediaList(int mediaType, int page, int pageSize) {
        List<Media> elements = jdbcTemplate.query("SELECT * FROM media WHERE type = ? OFFSET ? LIMIT ?",
                new Object[]{mediaType, pageSize * page, pageSize}, MEDIA_ROW_MAPPER);
        int mediaCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM media WHERE type = ?",
                new Object[]{mediaType}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, mediaCount);
    }

    @Override
    public Optional<Integer> getMediaCount() {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM media", COUNT_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Optional<Integer> getMediaCountByMediaType(int mediaType) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM media WHERE type = ?", new Object[]{mediaType}, COUNT_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public PageContainer<Media> getLatestMediaList(int mediaType, int page, int pageSize) {
        List<Media> elements = jdbcTemplate.query("SELECT * FROM media WHERE type = ? " +
                        "ORDER BY releasedate DESC OFFSET ? LIMIT ?  ",
                new Object[]{mediaType, pageSize * page, pageSize}, MEDIA_ROW_MAPPER);
        int mediaCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM media WHERE type = ?",
                new Object[]{mediaType}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, mediaCount);
    }

    @Override
    public List<Media> searchMediaByTitle(String title, int page, int pageSize) {
        return jdbcTemplate.query("SELECT * FROM media WHERE title ILIKE CONCAT('%', ?, '%') ORDER BY title OFFSET ? LIMIT ?", new Object[]{title, page, pageSize}, MEDIA_ROW_MAPPER);
    }//TODO CHECK page*pageSize

    @Override
    public Optional<Integer> getCountSearchMediaByTitle(String title) {
        return jdbcTemplate.query("SELECT COUNT(*) FROM media WHERE title ILIKE CONCAT('%', ?, '%')", new Object[]{title}, COUNT_ROW_MAPPER).stream().findFirst();
    }
}
