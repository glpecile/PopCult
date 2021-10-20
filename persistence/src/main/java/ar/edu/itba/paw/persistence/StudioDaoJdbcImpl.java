package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.StudioDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Studio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class StudioDaoJdbcImpl implements StudioDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert studioJdbcInsert;
    private final SimpleJdbcInsert mediaStudioJdbcInsert;
    private static final RowMapper<Studio> STUDIO_ROW_MAPPER = RowMappers.STUDIO_ROW_MAPPER;

    private static final RowMapper<Integer> MEDIA_ID_ROW_MAPPER = RowMappers.MEDIA_ID_ROW_MAPPER;

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;

    private static final RowMapper<Media> MEDIA_ROW_MAPPER = RowMappers.MEDIA_ROW_MAPPER;

    @Override
    public Optional<Studio> getById(int studioId) {
        return jdbcTemplate.query("SELECT * FROM studio WHERE studioId = ?", new Object[]{studioId}, STUDIO_ROW_MAPPER)
                .stream().findFirst();
    }

    @Autowired
    public StudioDaoJdbcImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.studioJdbcInsert = new SimpleJdbcInsert(ds).withTableName("studio").usingGeneratedKeyColumns("studioId");
        this.mediaStudioJdbcInsert = new SimpleJdbcInsert(ds).withTableName("mediaStudio");
    }

    @Override
    public List<Studio> getStudioByMediaId(int mediaId) {
        return jdbcTemplate.query("SELECT studioid, name, image FROM studio NATURAL JOIN mediaStudio WHERE mediaId = ?", new Object[]{mediaId}, STUDIO_ROW_MAPPER);
    }

    @Override
    public PageContainer<Media> getMediaByStudio(Studio studio, int page, int pageSize) {
        List<Media> elements = jdbcTemplate.query("SELECT * FROM mediaStudio NATURAL JOIN media WHERE studioId = ? OFFSET ? LIMIT ?", new Object[]{studio.getStudioId(), pageSize * page, pageSize},
                MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM mediaStudio where studioId = ?", new Object[]{studio}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public Optional<Integer> getMediaCountByStudio(int studioId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM mediaStudio where studioId = ?", new Object[]{studioId}, COUNT_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<Studio> getStudios() {
        return jdbcTemplate.query("SELECT * FROM studio", STUDIO_ROW_MAPPER);
    }
}
