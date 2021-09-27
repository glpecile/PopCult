package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CollaborativeListsDao;
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

@Repository
public class CollaborativeListsDaoJdbcImpl implements CollaborativeListsDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertCollab;
    private final SimpleJdbcInsert jdbcInsertRequest;

    private static final RowMapper<Request> REQUEST_ROW_MAPPER = RowMappers.REQUEST_ROW_MAPPER;


    @Autowired
    public CollaborativeListsDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertCollab = new SimpleJdbcInsert(ds).withTableName("collaborative").usingGeneratedKeyColumns("collabId");
        jdbcInsertRequest = new SimpleJdbcInsert(ds).withTableName("request");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS collaborative (" +
                "collabId SERIAL PRIMARY KEY," +
                "listId INT NOT NULL," +
                "collaboratorId INT NOT NULL," +
                "collabType INT," +
                "FOREIGN KEY(listId) REFERENCES medialist(medialistid) ON DELETE CASCADE," +
                "FOREIGN KEY(collaboratorId) REFERENCES users(userid) ON DELETE CASCADE)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS request (" +
                "collabId INT," +
                "requestTitle TEXT," +
                "requestBody TEXT," +
                "requestType INT NOT NULL," +
                "FOREIGN KEY(collabId) REFERENCES collaborative(collabId) ON DELETE CASCADE)");

    }

    @Override
    public Request makeNewRequest(int listId, int userId, String title, String body, int collabType) {
        Map<String, Object> data = new HashMap<>();
        data.put("listId", listId);
        data.put("collaboratorId", userId);
        data.put("collabType", null);
        int key = jdbcInsertCollab.executeAndReturnKey(data).intValue();

        Map<String, Object> map = new HashMap<>();
        map.put("collabId", key);
        map.put("requestTitle", title);
        map.put("requestBody", body);
        map.put("requestType", collabType);
        jdbcInsertRequest.execute(map);
        return new Request(key, "", title, body, collabType);
    }

    // requests from a user       return jdbcTemplate.query("SELECT * FROM medialist m JOIN (SELECT * FROM request r JOIN collaborative c ON r.collabid = c.collabid) aux ON m.medialistid = aux.listid JOIN users u on aux.collaboratorid = u.userid",new Object[]{userId},REQUEST_ROW_MAPPER);
    @Override
    public List<Request> getRequestsByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM medialist m JOIN (SELECT * FROM request r JOIN collaborative c ON r.collabid = c.collabid) aux ON m.medialistid = aux.listid JOIN users u on m.userid = ?",new Object[]{userId},REQUEST_ROW_MAPPER);
    }
}
