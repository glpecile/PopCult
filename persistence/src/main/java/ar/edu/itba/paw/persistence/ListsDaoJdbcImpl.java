package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ListsDao;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Time;
import java.util.*;

@Repository
public class ListsDaoJdbcImpl implements ListsDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert mediaListjdbcInsert;
    private final SimpleJdbcInsert listElementjdbcInsert;

    private static final int discoveryUserId = 1;


    private static final RowMapper<MediaList> MEDIA_LIST_ROW_MAPPER =
            (rs, rowNum) -> new MediaList(
                    rs.getInt("mediaListId"),
                    rs.getInt("userId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("creationDate"),
                    rs.getBoolean("visibility"),
                    rs.getBoolean("collaborative"));

    private static final RowMapper<Integer> INTEGER_ROW_MAPPER =
            (rs, rowNum) -> rs.getInt("mediaId");

    private static final RowMapper<Integer> COUNT_ROW_MAPPER =
            (rs, rowNum) -> rs.getInt("count");

    @Autowired
    public ListsDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        mediaListjdbcInsert = new SimpleJdbcInsert(ds).withTableName("medialist").usingGeneratedKeyColumns("medialistid");
        listElementjdbcInsert = new SimpleJdbcInsert(ds).withTableName("listelement");
        //forkedListsjdbcInsert = new SimpleJdbcInsert(ds).withTableName("forkedlists");

//        jdbcTemplate.execute("ALTER TABLE mediaList DROP COLUMN image");
//        jdbcTemplate.execute("ALTER TABLE mediaList ADD visibility BOOLEAN NOT NULL default TRUE");
//        jdbcTemplate.execute("ALTER TABLE mediaList ADD collaborative BOOLEAN NOT NULL default FALSE");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS mediaList(" +
                "mediaListId SERIAL PRIMARY KEY," +
                "userId INT NOT NULL," +
                "name TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "creationDate DATE," +
                "visibility BOOLEAN," +
                "collaborative BOOLEAN," +
                "FOREIGN KEY(userId) REFERENCES users(userId) ON DELETE CASCADE)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS listElement(" +
                "mediaId INT NOT NULL," +
                "mediaListId INT NOT NULL, " +
                "FOREIGN KEY(mediaId) REFERENCES media(mediaId) ON DELETE CASCADE," +
                "FOREIGN KEY (mediaListId) REFERENCES medialist(medialistid) ON DELETE CASCADE)");

//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS forkedLists(" +
//                "mediaListId INT NOT NULL, " +
//                "originalOwnerId INT NOT NULL,"+
//                "forkerId INT NOT NULL,"+
//                "FOREIGN KEY (mediaListId) REFERENCES medialist(medialistid) ON DELETE CASCADE," +
//                "FOREIGN KEY(originalOwnerId) REFERENCES users(userId) ON DELETE CASCADE," +
//                "FOREIGN KEY(forkerId) REFERENCES users(userId) ON DELETE CASCADE)");
    }

    @Override
    public Optional<MediaList> getMediaListById(int mediaListId) {
        return jdbcTemplate.query("SELECT * FROM mediaList WHERE mediaListId = ?", new Object[]{mediaListId}, MEDIA_LIST_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<MediaList> getMediaListById(List<Integer> mediaListId) {
        if(mediaListId.size() == 0)
            return new ArrayList<>();
        String inSql = String.join(",", Collections.nCopies(mediaListId.size(), "?"));
        return jdbcTemplate.query(
                String.format("SELECT * FROM mediaList WHERE mediaListId IN (%s)", inSql),
                mediaListId.toArray(), MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public List<MediaList> getAllLists(int page, int pageSize) {
        return jdbcTemplate.query("SELECT * FROM mediaList WHERE visibility = ? OFFSET ? LIMIT ?", new Object[]{true, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public List<MediaList> getMediaListByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM medialist WHERE userid = ?", new Object[]{userId}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public List<MediaList> getMediaListByUserId(int userId, int page, int pageSize) {
        return jdbcTemplate.query("SELECT * FROM medialist WHERE userid = ? OFFSET ? LIMIT ?", new Object[]{userId, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public List<MediaList> getDiscoveryMediaLists(int pageSize) {
        return jdbcTemplate.query("SELECT * FROM medialist WHERE userid = ? ORDER BY name LIMIT ?", new Object[]{discoveryUserId, pageSize}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public List<Integer> getMediaIdInList(int mediaListId) {
        return jdbcTemplate.query("SELECT mediaId FROM listelement WHERE mediaListId = ?", new Object[]{mediaListId}, INTEGER_ROW_MAPPER);
    }

    @Override
    public List<Integer> getMediaIdInList(int mediaListId, int page, int pageSize){
        return jdbcTemplate.query("SELECT mediaId FROM listelement WHERE mediaListId = ? OFFSET ? LIMIT ?", new Object[]{mediaListId, pageSize*page, pageSize}, INTEGER_ROW_MAPPER);
    }

    @Override
    public List<MediaList> getLastAddedLists(int page, int pageSize) {
        return jdbcTemplate.query("SELECT * FROM medialist WHERE visibility = ? ORDER BY creationDate DESC OFFSET ? LIMIT ?", new Object[]{true, pageSize * page, pageSize}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public List<MediaList> getNLastAddedList(int amount) {
        return jdbcTemplate.query("SELECT * from medialist ORDER BY creationDate DESC LIMIT ?", new Object[]{amount}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public List<MediaList> getListsIncludingMediaId(int mediaId, int page, int pageSize) {
        return jdbcTemplate.query("SELECT DISTINCT medialist.medialistid, medialist.userid, name, description, creationdate, visibility, collaborative FROM listElement NATURAL JOIN" +
                " mediaList WHERE mediaId = ? OFFSET ? LIMIT ?", new Object[]{mediaId, pageSize * page, pageSize}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public Optional<Integer> getListCount() {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM medialist WHERE visibility = ?", COUNT_ROW_MAPPER, new Object[]{true})
                .stream().findFirst();
    }

    @Override
    public Optional<Integer> getListCountFromUserId(int userId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM medialist WHERE userId = ?", new Object[]{userId}, COUNT_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Optional<Integer> getListCountFromMedia(int mediaId) {
        return jdbcTemplate.query("SELECT DISTINCT COUNT(*) AS count FROM listelement WHERE mediaId = ?", new Object[]{mediaId}, COUNT_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<MediaList> getListsContainingGenre(int genreId, int pageSize, int minMatches) {
        return jdbcTemplate.query("SELECT DISTINCT medialist.medialistid, medialist.userid, name, description, creationdate, visibility, collaborative FROM mediaGenre NATURAL JOIN " +
                "listelement NATURAL JOIN mediaList WHERE genreId = ? GROUP BY mediaList.medialistid, medialist.name, description, " +
                "creationdate  HAVING COUNT(mediaId) >= ? ORDER BY creationdate DESC LIMIT ?", new Object[]{genreId, minMatches, pageSize}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public MediaList createMediaList(int userId, String title, String description, boolean visibility, boolean collaborative) {
        Map<String, Object> data = new HashMap<>();
        Date localDate = new Date();
        data.put("userid", userId);
        data.put("name", title);
        data.put("description", description);
        data.put("creationDate", localDate);
        data.put("visibility", visibility);
        data.put("collaborative", collaborative);
        KeyHolder key = mediaListjdbcInsert.executeAndReturnKeyHolder(data);
        return new MediaList((int) key.getKey(), userId, title, description, localDate, visibility, collaborative);
    }

    @Override
    public void addToMediaList(int mediaListId, int mediaId) {
        Map<String, Object> data = new HashMap<>();
        data.put("mediaId", mediaId);
        data.put("mediaListId", mediaListId);
        listElementjdbcInsert.execute(data);
    }

    @Override
    public void addToMediaList(int mediaListId, List<Integer> mediaIdList) {
        for (int mediaId : mediaIdList) {
            addToMediaList(mediaListId, mediaId);
        }
    }

    @Override
    public void deleteMediaFromList(int mediaListId, int mediaId) {
        jdbcTemplate.update("DELETE FROM listelement WHERE mediaListId = ? AND mediaId = ?", mediaListId, mediaId);
    }

    @Override
    public void deleteList(int mediaListId) {
        jdbcTemplate.update("DELETE FROM listelement WHERE mediaListId = ?", mediaListId);
        jdbcTemplate.update("DELETE FROM medialist WHERE medialistid = ?", mediaListId);
    }

    @Override
    public void updateList(int mediaListId, String title, String description, boolean visibility, boolean collaborative) {
        jdbcTemplate.update("UPDATE medialist SET name = ?, description = ?, visibility = ?, collaborative = ? WHERE medialistid = ?", title, description, visibility, collaborative, mediaListId);
    }

    @Override
    public MediaList createMediaListCopy(int userId, int toCopyListId) {
        Map<String, Object> data = new HashMap<>();
        MediaList toCopy = getMediaListById(toCopyListId).orElseThrow(RuntimeException::new);
        Date localDate = new Date();
        data.put("userid", userId);
        data.put("name", "Copy from " + toCopy.getName());
        data.put("description", toCopy.getDescription());
        data.put("creationDate", localDate);
        data.put("visibility", toCopy.isVisible());
        data.put("collaborative", toCopy.isCollaborative());
        KeyHolder key = mediaListjdbcInsert.executeAndReturnKeyHolder(data);
        addToMediaList((int) key.getKey(), getMediaIdInList(toCopyListId));
        Map<String, Object> forkData = new HashMap<>();
        /*
        forkData.put("mediaListId", (int) key.getKey());
        forkData.put("originalOwnerId", toCopy.getUserId());
        forkData.put("forkerId", userId);
        forkedListsjdbcInsert.execute(forkData)
        */
        return new MediaList((int) key.getKey(), userId, toCopy.getName(), toCopy.getDescription(), localDate, toCopy.isVisible(), toCopy.isCollaborative());
    }
}
