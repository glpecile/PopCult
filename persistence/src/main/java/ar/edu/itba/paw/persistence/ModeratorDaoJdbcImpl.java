package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ModeratorDao;
import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import sun.jvm.hotspot.debugger.linux.LinuxOopHandle;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ModeratorDaoJdbcImpl implements ModeratorDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<User> USER_ROW_MAPPER = RowMappers.USER_ROW_MAPPER;

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;

    private static final Logger LOGGER = LoggerFactory.getLogger(ModeratorDaoJdbcImpl.class);

    @Autowired
    public ModeratorDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("modRequests");
    }

    @Override
    public PageContainer<User> getModerators(int page, int pageSize) {
        List<User> moderators = jdbcTemplate.query("SELECT * FROM users WHERE role = ? OFFSET ? LIMIT ?", new Object[]{Roles.MOD.ordinal(), page * pageSize, pageSize}, USER_ROW_MAPPER);
        int moderatorsCount = jdbcTemplate.query("SELECT COUNT(*) FROM users WHERE role = ?", new Object[]{Roles.MOD.ordinal()}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<User>(moderators, page, pageSize, moderatorsCount);
    }

    @Override
    public void promoteToMod(int userId) {
        jdbcTemplate.update("UPDATE users SET role = ? WHERE userid = ?", Roles.MOD.ordinal(), userId);
    }

    @Override
    public void removeMod(int userId) {
        jdbcTemplate.update("UPDATE users SET role = ? WHERE userid = ?", Roles.USER.ordinal(), userId);
    }

    @Override
    public PageContainer<User> getModRequesters(int page, int pageSize) {
        List<User> moderators = jdbcTemplate.query("SELECT * FROM users NATURAL JOIN modRequests ORDER BY date DESC OFFSET ? LIMIT ?", new Object[]{page * pageSize, pageSize},
                USER_ROW_MAPPER);
        int moderatorsCount = jdbcTemplate.query("SELECT COUNT(*) FROM users NATURAL JOIN modRequests", new Object[]{}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<User>(moderators, page, pageSize, moderatorsCount);
    }

    @Override
    public void addModRequest(int userId) throws ModRequestAlreadyExistsException {
        final Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        args.put("date", new Date());
        try {
            jdbcInsert.execute(args);
        } catch (DuplicateKeyException e) {
            LOGGER.error("Mod request was already sent.");
            throw new ModRequestAlreadyExistsException();
        }
    }

    @Override
    public void removeRequest(int userId) {
        jdbcTemplate.update("DELETE FROM modRequests WHERE userId = ?", userId);
    }
}
