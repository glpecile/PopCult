package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.MediaDao;
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
        if(mediaIds.size() == 0)
            return new ArrayList<>();
        String inSql = String.join(",", Collections.nCopies(mediaIds.size(), "?"));
        return jdbcTemplate.query(
                String.format("SELECT * FROM media WHERE mediaId IN (%s)", inSql),
                 mediaIds.toArray(), MEDIA_ROW_MAPPER);

    }

    @Override
    public List<Media> getMediaList() {
        return jdbcTemplate.query("SELECT * FROM media", MEDIA_ROW_MAPPER);
    }

    @Override
    public List<Media> getMediaList(int mediaType, int page, int pageSize) {
        return jdbcTemplate.query("SELECT * FROM media WHERE type = ? OFFSET ? LIMIT ?", new Object[] {mediaType, pageSize * page, pageSize}, MEDIA_ROW_MAPPER);
    }
}
