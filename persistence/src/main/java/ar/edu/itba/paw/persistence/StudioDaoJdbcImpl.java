package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.StudioDao;
import ar.edu.itba.paw.models.staff.Studio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StudioDaoJdbcImpl implements StudioDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert studioJdbcInsert;
    private final SimpleJdbcInsert mediaStudioJdbcInsert;
    private static final RowMapper<Studio> STUDIO_ROW_MAPPER = (rs, rowNum) -> new Studio(
            rs.getInt("studioId"),
            rs.getString("name"),
            rs.getString("image"));

    @Autowired
    public StudioDaoJdbcImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.studioJdbcInsert = new SimpleJdbcInsert(ds).withTableName("studio").usingGeneratedKeyColumns("studioId");
        this.mediaStudioJdbcInsert = new SimpleJdbcInsert(ds).withTableName("mediaStudio");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS studio (" +
                "studioId SERIAL PRIMARY KEY," +
                "name TEXT," +
                "image TEXT)");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS mediaStudio (" +
                "mediaId INT NOT NULL," +
                "studioId INT NOT NULL," +
                "FOREIGN KEY(mediaId) REFERENCES media(mediaId)," +
                "FOREIGN KEY(studioId) REFERENCES studio(studioId))");
    }

    @Override
    public List<Studio> getStudioByMediaId(int mediaId) {
        return jdbcTemplate.query("SELECT studioid, name, image FROM studio NATURAL JOIN mediaStudio WHERE mediaId = ?", new Object[]{mediaId}
                ,STUDIO_ROW_MAPPER);
    }

    @Override
    public List<Studio> getStudios() {
        return jdbcTemplate.query("SELECT * FROM studio",STUDIO_ROW_MAPPER);
    }
}
