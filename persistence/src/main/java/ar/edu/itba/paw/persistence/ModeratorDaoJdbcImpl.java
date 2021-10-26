package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ModeratorDao;
import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.ModRequest;
import ar.edu.itba.paw.models.user.UserRole;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

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
        List<User> moderators = jdbcTemplate.query("SELECT * FROM users WHERE role = ? OFFSET ? LIMIT ?", new Object[]{UserRole.MOD.ordinal(), page * pageSize, pageSize}, USER_ROW_MAPPER);
        int moderatorsCount = jdbcTemplate.query("SELECT COUNT(*) FROM users WHERE role = ?", new Object[]{UserRole.MOD.ordinal()}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<User>(moderators, page, pageSize, moderatorsCount);
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
    public ModRequest addModRequest(User user) throws ModRequestAlreadyExistsException {
        final Map<String, Object> args = new HashMap<>();
        args.put("userId", user.getUserId());
        args.put("date", new Date());
        try {
            jdbcInsert.execute(args);
        } catch (DuplicateKeyException e) {
            LOGGER.error("Mod request was already sent.");
            throw new ModRequestAlreadyExistsException();
        }
        return new ModRequest(null, user, new Date());
    }

    @Override
    public void removeRequest(User user) {
        jdbcTemplate.update("DELETE FROM modRequests WHERE userId = ?", user.getUserId());
    }
}
