package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.GenreDao;
import ar.edu.itba.paw.models.media.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbcImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert genrejdbcInsert;
    private final SimpleJdbcInsert mediaGenrejdbcInsert;

    private static final RowMapper<String> STRING_ROW_MAPPER =
            (rs, rowNum) -> new String(
                    rs.getString("name"));

    @Autowired
    public GenreDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        genrejdbcInsert = new SimpleJdbcInsert(ds).withTableName("genre").usingGeneratedKeyColumns("genreId");
        mediaGenrejdbcInsert = new SimpleJdbcInsert(ds).withTableName("mediaGenre");

        jdbcTemplate.execute("DROP TABLE IF EXISTS genre");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS genre(" +
                "genreId SERIAL PRIMARY KEY," +
                "name TEXT NOT NULL)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS mediaGenre(" +
                "mediaId SERIAL NOT NULL," +
                "genreId SERIAL NOT NULL," +
                "FOREIGN KEY(mediaId) REFERENCES media(mediaid)," +
                "FOREIGN KEY(genreId) REFERENCES genre(genreId))");

    }

    @Override
    public List<String> getGenreByMediaId(int mediaId) {
        return jdbcTemplate.query("SELECT name FROM mediaGenre NATURAL JOIN genre WHERE mediaId = ?", new Object[]{mediaId}, STRING_ROW_MAPPER);
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
