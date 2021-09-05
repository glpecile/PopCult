package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.StaffDao;
import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.Director;
import ar.edu.itba.paw.models.staff.RoleType;
import ar.edu.itba.paw.models.staff.StaffMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StaffDaoJdbcImpl implements StaffDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert staffMemberjdbcInsert;
    private final SimpleJdbcInsert directorjdbcInsert;
    private final SimpleJdbcInsert castjdbcInsert;

    private static final RowMapper<StaffMember> STAFF_MEMBER_ROW_MAPPER =
            (rs, rowNum) -> new StaffMember(rs.getInt("staffMemberId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("image"));

    private static final RowMapper<Actor> ACTOR_ROW_MAPPER =
            (rs, rowNum) -> new Actor(new StaffMember(rs.getInt("staffMemberId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("image")),
                    rs.getString("characterName"));

    private static final RowMapper<Integer> MEDIA_ID_ROW_MAPPER =
            (rs, rowNum) -> rs.getInt("mediaId");

    @Autowired
    public StaffDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        staffMemberjdbcInsert = new SimpleJdbcInsert(ds).withTableName("staffMember").usingGeneratedKeyColumns("staffMemberId");
        directorjdbcInsert = new SimpleJdbcInsert(ds).withTableName("director");
        castjdbcInsert = new SimpleJdbcInsert(ds).withTableName("cast");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS staffMember (" +
                "staffMemberId SERIAL PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "description TEXT," +
                "image TEXT)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS director (" +
                "mediaId INTEGER," +
                "staffMemberId INTEGER," +
                "FOREIGN KEY(mediaId) References media(mediaId) ON DELETE CASCADE," +
                "FOREIGN KEY(staffMemberId) References staffMember(staffMemberId) ON DELETE CASCADE )");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS crew (" +
                "mediaId INTEGER," +
                "staffMemberId INTEGER," +
                "characterName VARCHAR(100) NOT NULL," +
                "FOREIGN KEY(mediaId) References media(mediaId) ON DELETE CASCADE," +
                "FOREIGN KEY(staffMemberId) References staffMember(staffMemberId) ON DELETE CASCADE)");
    }

    @Override
    public Optional<StaffMember> getById(int staffMemberId) {
        return jdbcTemplate.query("SELECT * FROM staffMember WHERE staffMemberId = ?", new Object[]{staffMemberId}, STAFF_MEMBER_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public List<StaffMember> getPersonList() {
        return jdbcTemplate.query("SELECT * FROM staffMember", STAFF_MEMBER_ROW_MAPPER);
    }

    @Override
    public List<Integer> getMediaByDirector(int staffMemberId, int page, int pageSize) {
        return jdbcTemplate.query("SELECT mediaId FROM staffMember NATURAL JOIN director " +
                "WHERE staffMemberId = ? " +
                "OFFSET ? LIMIT ?", new Object[]{staffMemberId, pageSize * page, pageSize}, MEDIA_ID_ROW_MAPPER);
    }

    @Override
    public List<Integer> getMediaByActor(int staffMemberId, int page, int pageSize) {
        return jdbcTemplate.query("SELECT mediaId FROM staffMember NATURAL JOIN crew " +
                "WHERE staffMemberId = ? " +
                "OFFSET ? LIMIT ?", new Object[]{staffMemberId, pageSize * page, pageSize}, MEDIA_ID_ROW_MAPPER);
    }
    @Override
    public List<Integer> getMediaByRoleType(int staffMemberId, int page, int pageSize, int roleType) {
        if(roleType == RoleType.ACTOR.ordinal())
            return getMediaByActor(staffMemberId,page,pageSize);
        return getMediaByDirector(staffMemberId,page,pageSize);
    }
    @Override
    public List<Director> getDirectorsByMedia(int mediaId) {
        return jdbcTemplate.query("SELECT * FROM staffMember NATURAL JOIN director " +
                        "WHERE mediaId = ? ORDER BY name ASC", new Object[]{mediaId}, STAFF_MEMBER_ROW_MAPPER)
                .stream().map(Director::new).collect(Collectors.toList());
    }

    @Override
    public List<Actor> getActorsByMedia(int mediaId) {
        return jdbcTemplate.query("SELECT * FROM staffMember NATURAL JOIN crew " +
                "WHERE mediaId = ? ORDER BY name ASC", new Object[]{mediaId}, ACTOR_ROW_MAPPER);
    }
}
