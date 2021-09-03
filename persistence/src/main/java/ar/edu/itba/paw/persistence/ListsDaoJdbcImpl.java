package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ListsDao;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ListsDaoJdbcImpl implements ListsDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert mediaListjdbcInsert;
    private final SimpleJdbcInsert listElementjdbcInsert;

    private static final RowMapper<MediaList> ROW_MAPPER =
            (rs, rowNum) -> new MediaList(
                    rs.getInt("mediaListId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("image"));

    private static final RowMapper<Integer> INTEGER_ROW_MAPPER =
            (rs, rowNum) -> rs.getInt("mediaListId");

    @Autowired
    public ListsDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        mediaListjdbcInsert = new SimpleJdbcInsert(ds).withTableName("mediaList").usingGeneratedKeyColumns("mediaListId");
        listElementjdbcInsert = new SimpleJdbcInsert(ds).withTableName("listElement");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS mediaList(" +
                "mediaListId SERIAL PRIMARY KEY," +
                "userId SERIAL NOT NULL," +
                "name TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "image TEXT NOT NULL," +
                "FOREIGN KEY(userId) REFERENCES users(userId))");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS listElement(" +
                "mediaId SERIAL NOT NULL," +
                "mediaListId SERIAL NOT NULL, " +
                "FOREIGN KEY(mediaId) REFERENCES media(mediaId)," +
                "FOREIGN KEY (mediaListId) REFERENCES medialist(medialistid))");
    }

    @Override
    public Optional<MediaList> getMediaListById(int mediaListId) {
        return jdbcTemplate.query("SELECT * FROM mediaList WHERE mediaListId = ?", new Object[]{mediaListId}, ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<MediaList> getMediaListByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM medialist WHERE userid = ?", new Object[]{userId}, ROW_MAPPER);
    }

    @Override
    public List<MediaList> getDiscoveryMediaLists() {
        return jdbcTemplate.query("SELECT * FROM medialist WHERE userid = ?", new Object[]{1}, ROW_MAPPER);
    }

    @Override
    public List<Integer> getMediaIdInList(int mediaListId) {
        return jdbcTemplate.query("SELECT mediaId FROM listelement WHERE medialistid = ?", new Object[]{mediaListId}, INTEGER_ROW_MAPPER);
    }
}
