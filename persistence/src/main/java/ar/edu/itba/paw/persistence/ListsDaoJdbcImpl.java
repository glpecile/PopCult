package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ListsDao;
import ar.edu.itba.paw.interfaces.exceptions.MediaAlreadyInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ListsDaoJdbcImpl implements ListsDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert mediaListjdbcInsert;
    private final SimpleJdbcInsert listElementjdbcInsert;
    private final SimpleJdbcInsert forkedListsjdbcInsert;

    private static final int discoveryUserId = 1;


    private static final RowMapper<MediaList> MEDIA_LIST_ROW_MAPPER = RowMappers.MEDIA_LIST_ROW_MAPPER;

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;

    private static final RowMapper<Media> MEDIA_ROW_MAPPER = RowMappers.MEDIA_ROW_MAPPER;

    private static final RowMapper<User> USER_ROW_MAPPER = RowMappers.USER_ROW_MAPPER;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListsDaoJdbcImpl.class);

    @Autowired
    public ListsDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        mediaListjdbcInsert = new SimpleJdbcInsert(ds).withTableName("medialist").usingGeneratedKeyColumns("medialistid");
        listElementjdbcInsert = new SimpleJdbcInsert(ds).withTableName("listelement");
        forkedListsjdbcInsert = new SimpleJdbcInsert(ds).withTableName("forkedlists");
    }

    @Override
    public Optional<MediaList> getMediaListById(int mediaListId) {
        return jdbcTemplate.query("SELECT * FROM mediaList WHERE mediaListId = ?", new Object[]{mediaListId}, MEDIA_LIST_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<MediaList> getMediaListById(List<Integer> mediaListId) {
        if (mediaListId.size() == 0)
            return new ArrayList<>();
        String inSql = String.join(",", Collections.nCopies(mediaListId.size(), "?"));
        return jdbcTemplate.query(
                String.format("SELECT * FROM mediaList WHERE mediaListId IN (%s)", inSql),
                mediaListId.toArray(), MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public PageContainer<MediaList> getAllLists(int page, int pageSize) {
        List<MediaList> elements = jdbcTemplate.query("SELECT * FROM mediaList WHERE visibility = ? OFFSET ? LIMIT ?", new Object[]{true, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM medialist WHERE visibility = ?", COUNT_ROW_MAPPER, new Object[]{true})
                .stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public List<MediaList> getMediaListByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM medialist WHERE userid = ?", new Object[]{userId}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public PageContainer<MediaList> getMediaListByUserId(int userId, int page, int pageSize) {
        List<MediaList> elements = jdbcTemplate.query("SELECT * FROM medialist WHERE userid = ? OFFSET ? LIMIT ?", new Object[]{userId, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM medialist WHERE userId = ?", new Object[]{userId}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public PageContainer<MediaList> getPublicMediaListByUserId(int userId, int page, int pageSize) {
        List<MediaList> elements = jdbcTemplate.query("SELECT * FROM medialist WHERE userid = ? AND visibility = ? OFFSET ? LIMIT ?", new Object[]{userId, true, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM medialist WHERE userId = ? AND visibility = ?", new Object[]{userId, true}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public List<MediaList> getDiscoveryMediaLists(int pageSize) {
        return jdbcTemplate.query("SELECT * FROM medialist WHERE userid = ? ORDER BY listname LIMIT ?", new Object[]{discoveryUserId, pageSize}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public List<Media> getMediaIdInList(int mediaListId) {
        return jdbcTemplate.query("SELECT * FROM listelement NATURAL JOIN media WHERE mediaListId = ?", new Object[]{mediaListId}, MEDIA_ROW_MAPPER);
    }

    @Override
    public PageContainer<Media> getMediaIdInList(int mediaListId, int page, int pageSize) {
        List<Media> elements = jdbcTemplate.query("SELECT * FROM listelement NATURAL JOIN media WHERE mediaListId = ? OFFSET ? LIMIT ?", new Object[]{mediaListId, pageSize * page, pageSize}, MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT DISTINCT COUNT(*) AS count FROM listelement WHERE mediaListId = ?", new Object[]{mediaListId}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public PageContainer<MediaList> getLastAddedLists(int page, int pageSize) {
        List<MediaList> elements = jdbcTemplate.query("SELECT * FROM medialist WHERE visibility = ? ORDER BY creationDate DESC OFFSET ? LIMIT ?", new Object[]{true, pageSize * page, pageSize}, MEDIA_LIST_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM medialist WHERE visibility = ?", COUNT_ROW_MAPPER, new Object[]{true})
                .stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public List<MediaList> getNLastAddedList(int amount) {
        return jdbcTemplate.query("SELECT * from medialist WHERE visibility = ? ORDER BY creationDate DESC LIMIT ?", new Object[]{true, amount}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public PageContainer<MediaList> getListsIncludingMediaId(int mediaId, int page, int pageSize) {
        List<MediaList> elements = jdbcTemplate.query("SELECT DISTINCT medialist.medialistid, medialist.userid, listname, description, creationdate, visibility, collaborative FROM listElement NATURAL JOIN" +
                " mediaList WHERE mediaId = ? AND visibility = ? OFFSET ? LIMIT ?", new Object[]{mediaId, true, pageSize * page, pageSize}, MEDIA_LIST_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT DISTINCT COUNT(*) AS count FROM listelement NATURAL JOIN medialist WHERE mediaId = ? AND visibility = ?", new Object[]{mediaId, true}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public List<MediaList> getListsContainingGenre(int genreId, int pageSize, int minMatches) {
        return jdbcTemplate.query("SELECT DISTINCT medialist.medialistid, medialist.userid, listname, description, creationdate, visibility, collaborative FROM mediaGenre NATURAL JOIN " +
                "listelement NATURAL JOIN mediaList WHERE genreId = ? AND visibility = ? GROUP BY mediaList.medialistid, medialist.listname, description, " +
                "creationdate  HAVING COUNT(mediaId) >= ? ORDER BY creationdate DESC LIMIT ?", new Object[]{genreId, true, minMatches, pageSize}, MEDIA_LIST_ROW_MAPPER);
    }

    @Override
    public MediaList createMediaList(int userId, String title, String description, boolean visibility, boolean collaborative) {
        Map<String, Object> data = new HashMap<>();
        Date localDate = new Date();
        data.put("userid", userId);
        data.put("listname", title);
        data.put("description", description);
        data.put("creationDate", localDate);
        data.put("visibility", visibility);
        data.put("collaborative", collaborative);
        KeyHolder key = mediaListjdbcInsert.executeAndReturnKeyHolder(data);
        return new MediaList((int) key.getKey(), userId, title, description, localDate, visibility, collaborative);
    }

    @Override
    public void addToMediaList(int mediaListId, int mediaId) throws MediaAlreadyInListException {
        Map<String, Object> data = new HashMap<>();
        data.put("mediaId", mediaId);
        data.put("mediaListId", mediaListId);
        try {
            listElementjdbcInsert.execute(data);
        } catch (DuplicateKeyException e) {
            LOGGER.error("Media already exists in MediaList");
            throw new MediaAlreadyInListException();
        }
    }

    @Override
    public void addToMediaList(int mediaListId, List<Integer> mediaIdList) throws MediaAlreadyInListException {
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
        jdbcTemplate.update("DELETE FROM medialist WHERE medialistid = ?", mediaListId);
    }

    @Override
    public void updateList(int mediaListId, String title, String description, boolean visibility, boolean collaborative) {
        jdbcTemplate.update("UPDATE medialist SET listname = ?, description = ?, visibility = ?, collaborative = ? WHERE medialistid = ?", title, description, visibility, collaborative, mediaListId);
    }

    @Override
    public Optional<MediaList> createMediaListCopy(int userId, int toCopyListId) {
        Map<String, Object> data = new HashMap<>();
        List<MediaList> fork = new ArrayList<>();
        //add to medialist table
        getMediaListById(toCopyListId).ifPresent((toCopy) -> {
            Date localDate = new Date();
            data.put("userid", userId);
            data.put("listname", "Copy from " + toCopy.getListName());
            data.put("description", toCopy.getDescription());
            data.put("creationDate", localDate);
            data.put("visibility", toCopy.isVisible());
            data.put("collaborative", toCopy.isCollaborative());
            KeyHolder key = mediaListjdbcInsert.executeAndReturnKeyHolder(data);
            List<Media> mediaList = getMediaIdInList(toCopyListId);
            List<Integer> mediaIdList = new ArrayList<>();
            for (Media media : mediaList) {
                mediaIdList.add(media.getMediaId());
            }
            try {
                addToMediaList((int) key.getKey(), mediaIdList);
            } catch (MediaAlreadyInListException e) {
               LOGGER.error("Media already exists in MediaList");
            }

            //add to forkedLists table
            Map<String, Object> forkData = new HashMap<>();
            forkData.put("originalistId", toCopyListId);
            forkData.put("forkedlistId", (int) key.getKey());
            forkData.put("originalOwnerId", toCopy.getUserId());
            forkData.put("forkerId", userId);
            forkedListsjdbcInsert.execute(forkData);

            fork.add(new MediaList((int) key.getKey(), userId, toCopy.getListName(), toCopy.getDescription(), localDate, toCopy.isVisible(), toCopy.isCollaborative()));
        });
        if (fork.isEmpty())
            return Optional.empty();
        return Optional.of(fork.get(0));
    }

    @Override
    public Optional<User> getListOwner(int listId) {
        return jdbcTemplate.query("SELECT DISTINCT * FROM medialist NATURAL JOIN users WHERE medialistid = ? ", new Object[]{listId}, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public boolean canEditList(int userId, int listId) {
        return jdbcTemplate.query("SELECT COUNT(*) FROM medialist LEFT JOIN collaborative c on medialist.medialistid = c.listid WHERE medialistid = ? AND ((userid = ?) OR (collaboratorid = ? AND accepted = ?))", new Object[]{listId, userId, userId, true}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0) > 0;
    }

    @Override
    public PageContainer<MediaList> getUserEditableLists(int userId, int page, int pageSize) {
        List<MediaList> editableLists = jdbcTemplate.query("((SELECT * FROM medialist WHERE userId = ?) UNION (SELECT m.* FROM collaborative c JOIN medialist m on c.listid = m.medialistid WHERE collaboratorid = ? AND accepted = ?)) OFFSET ? LIMIT ?", new Object[]{userId, userId, true, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
        int count = jdbcTemplate.query("SELECT COUNT(*) FROM ((SELECT * FROM medialist WHERE userId = ?) UNION (SELECT m.* FROM collaborative c JOIN medialist m on c.listid = m.medialistid WHERE collaboratorid = ? AND accepted = ?)) AS aux ", new Object[]{userId, userId, true}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(editableLists, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> getListForks(int listId, int page, int pageSize) {
        List<MediaList> forks = jdbcTemplate.query("SELECT m.* FROM forkedlists f JOIN medialist m ON f.forkedlistid = m.medialistid WHERE f.originalistid = ? AND m.visibility = ? OFFSET ? LIMIT ?", new Object[]{listId, true, page * pageSize, pageSize}, MEDIA_LIST_ROW_MAPPER);
        int count = jdbcTemplate.query("SELECT COUNT(m.*) FROM forkedlists f JOIN medialist m ON f.forkedlistid = m.medialistid WHERE f.originalistid = ? AND m.visibility = ?", new Object[]{listId, true}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(forks, page, pageSize, count);
    }


    @Override
    public Optional<MediaList> getForkedFrom(int listId) {
        return jdbcTemplate.query("SELECT * FROM medialist JOIN forkedlists ON medialist.medialistid = forkedlists.originalistid WHERE forkedlistid = ? AND visibility = ?", new Object[]{listId, true}, MEDIA_LIST_ROW_MAPPER).stream().findFirst();
    }


}
