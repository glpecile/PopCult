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

    private static final RowMapper<MediaList> MEDIA_LIST_ROW_MAPPER =
            (rs, rowNum) -> new MediaList(
                    rs.getInt("mediaListId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("image"),
                    rs.getDate("creationDate"));

    private static final RowMapper<Integer> INTEGER_ROW_MAPPER =
            (rs, rowNum) -> rs.getInt("mediaId");

    private static final RowMapper<Integer> COUNT_ROW_MAPPER =
            (rs, rowNum) -> rs.getInt("count");

    @Autowired
    public ListsDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        mediaListjdbcInsert = new SimpleJdbcInsert(ds).withTableName("mediaList").usingGeneratedKeyColumns("mediaListId");
        listElementjdbcInsert = new SimpleJdbcInsert(ds).withTableName("listElement");

//        jdbcTemplate.execute("DROP TABLE mediaList CASCADE");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS mediaList(" +
                "mediaListId SERIAL PRIMARY KEY," +
                "userId INT NOT NULL," +
                "name TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "image TEXT NOT NULL," +
                "creationDate DATE," +
                "FOREIGN KEY(userId) REFERENCES users(userId) ON DELETE CASCADE)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS listElement(" +
                "mediaId INT NOT NULL," +
                "mediaListId INT NOT NULL, " +
                "FOREIGN KEY(mediaId) REFERENCES media(mediaId) ON DELETE CASCADE ," +
                "FOREIGN KEY (mediaListId) REFERENCES medialist(medialistid) ON DELETE CASCADE)");
    }

    @Override
    public Optional<MediaList> getMediaListById(int mediaListId) {
        return jdbcTemplate.query("SELECT * FROM mediaList WHERE mediaListId = ?", new Object[]{mediaListId}, MEDIA_LIST_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<MediaList> getMediaListByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM medialist WHERE userid = ?", new Object[]{userId}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public List<MediaList> getDiscoveryMediaLists() {
        return jdbcTemplate.query("SELECT * FROM medialist WHERE userid = ?", new Object[]{1}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public List<Integer> getMediaIdInList(int mediaListId) {
        return jdbcTemplate.query("SELECT mediaId FROM listelement WHERE mediaListId = ?", new Object[]{mediaListId}, INTEGER_ROW_MAPPER);
    }

    @Override
    public List<MediaList> getLastAddedLists(int page, int pageSize) {
        return jdbcTemplate.query("SELECT * FROM medialist ORDER BY creationDate DESC OFFSET ? LIMIT ?", new Object[]{pageSize * page, pageSize}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public List<MediaList> getListsIncludingMediaId(int mediaId, int page, int pageSize) {
        return jdbcTemplate.query("SELECT DISTINCT medialistid, name, description, image, creationdate FROM listElement NATURAL JOIN mediaList WHERE mediaId = ? OFFSET ? LIMIT ?", new Object[]{mediaId, pageSize * page, pageSize}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public Optional<Integer> getListCount() {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM medialist", COUNT_ROW_MAPPER)
                .stream().findFirst();
    }

}
