package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CollaborativeListsDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CollaborativeListsDaoJdbcImpl implements CollaborativeListsDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertCollab;
//    private final SimpleJdbcInsert jdbcInsertRequest;

    private static final RowMapper<Request> REQUEST_ROW_MAPPER = RowMappers.REQUEST_ROW_MAPPER;

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;

    @Autowired
    public CollaborativeListsDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertCollab = new SimpleJdbcInsert(ds).withTableName("collaborative").usingGeneratedKeyColumns("collabid");
//        jdbcInsertRequest = new SimpleJdbcInsert(ds).withTableName("request");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS collaborative (" +
                "collabId SERIAL PRIMARY KEY," +
                "listId INT NOT NULL," +
                "collaboratorId INT NOT NULL," +
                "accepted BOOLEAN," +
//                "collabType INT," +
                "FOREIGN KEY(listId) REFERENCES medialist(medialistid) ON DELETE CASCADE," +
                "FOREIGN KEY(collaboratorId) REFERENCES users(userid) ON DELETE CASCADE," +
                "UNIQUE(listId, collaboratorId))");

//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS request (" +
//                "collabId INT," +
//                "requestMessage TEXT," +
//                "requestType INT NOT NULL," +
//                "FOREIGN KEY(collabId) REFERENCES collaborative(collabId) ON DELETE CASCADE)");

    }

    @Override
    public Request makeNewRequest(int listId, int userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("listId", listId);
        data.put("collaboratorId", userId);
//        data.put("collabType", null);
        data.put("accepted", false);
        int key = jdbcInsertCollab.executeAndReturnKey(data).intValue();

//        Map<String, Object> map = new HashMap<>();
//        map.put("collabId", key);
//        map.put("requestMessage", message);
//        map.put("requestType", collabType);
//        jdbcInsertRequest.execute(map);
//        return new Request(key, "", message, collabType);
        return null;
    }

    // requests from a user       return jdbcTemplate.query("SELECT * FROM medialist m JOIN (SELECT * FROM request r JOIN collaborative c ON r.collabid = c.collabid) aux ON m.medialistid = aux.listid JOIN users u on aux.collaboratorid = u.userid",new Object[]{userId},REQUEST_ROW_MAPPER);
    @Override
    public PageContainer<Request> getRequestsByUserId(int userId, int page, int pageSize) {
        List<Request> requestList = jdbcTemplate.query("SELECT collabid, collaboratorid, username, listid, listname, accepted  FROM (medialist m JOIN collaborative c ON m.medialistid = c.listid) JOIN users u on u.userid= c.collaboratorid AND m.userid = ? WHERE accepted = ? OFFSET ? LIMIT ?", new Object[]{userId, false, page * pageSize, pageSize}, REQUEST_ROW_MAPPER);
        int count = jdbcTemplate.query("SELECT COUNT(*) FROM (medialist m JOIN collaborative c ON m.medialistid = c.listid) JOIN users u on u.userid= c.collaboratorid AND m.userid = ? WHERE accepted = ?", new Object[]{userId, false}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(requestList, page, pageSize, count);
    }

    @Override
    public void acceptRequest(int collabId) {
        jdbcTemplate.update("UPDATE collaborative SET accepted = ? WHERE collabId = ?", true, collabId);
    }

    @Override
    public void rejectRequest(int collabId) {
        jdbcTemplate.update("DELETE FROM collaborative WHERE collabid = ?", collabId);

    }

    @Override
    public PageContainer<Request> getListCollaborators(int listId, int page, int pageSize) {
        List<Request> requestList = jdbcTemplate.query("SELECT * FROM (medialist m JOIN collaborative c ON m.medialistid = c.listid) JOIN users u on u.userid= c.collaboratorid AND medialistid = ? WHERE accepted = ? OFFSET ? LIMIT ?", new Object[]{listId, true, page * pageSize, pageSize}, REQUEST_ROW_MAPPER);
        int count = jdbcTemplate.query("SELECT COUNT(*) FROM (medialist m JOIN collaborative c ON m.medialistid = c.listid) JOIN users u on u.userid= c.collaboratorid AND medialistid = ? WHERE accepted = ?", new Object[]{listId, true}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(requestList, page, pageSize, count);
    }

    @Override
    public Optional<Request> getById(int collabId) {
        return jdbcTemplate.query("SELECT * FROM (medialist m JOIN collaborative c ON m.medialistid = c.listid) JOIN users u on u.userid= c.collaboratorid WHERE collabid = ?", new Object[]{collabId}, REQUEST_ROW_MAPPER).stream().findFirst();
    }
}
