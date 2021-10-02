package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.StaffDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
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

    private static final RowMapper<StaffMember> STAFF_MEMBER_ROW_MAPPER = RowMappers.STAFF_MEMBER_ROW_MAPPER;

    private static final RowMapper<Actor> ACTOR_ROW_MAPPER = RowMappers.ACTOR_ROW_MAPPER;

    private static final RowMapper<Integer> MEDIA_ID_ROW_MAPPER = RowMappers.MEDIA_ID_ROW_MAPPER;

    private static final RowMapper<Media> MEDIA_ROW_MAPPER = RowMappers.MEDIA_ROW_MAPPER;

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;

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
                "mediaId INT NOT NULL," +
                "staffMemberId INT NOT NULL," +
                "FOREIGN KEY(mediaId) References media(mediaId) ON DELETE CASCADE," +
                "FOREIGN KEY(staffMemberId) References staffMember(staffMemberId) ON DELETE CASCADE )");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS crew (" +
                "mediaId INT NOT NULL," +
                "staffMemberId INT NOT NULL," +
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
    public PageContainer<Media> getMediaByDirector(int staffMemberId, int page, int pageSize) {
                                                List<Media> elements = jdbcTemplate.query("SELECT * FROM director NATURAL JOIN media " +
                                                        "WHERE staffMemberId = ? " +
                                                        "OFFSET ? LIMIT ?", new Object[]{staffMemberId, pageSize * page, pageSize}, MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM director " +
                        "WHERE staffMemberId = ?", new Object[]{staffMemberId}, COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public PageContainer<Media> getMediaByActor(int staffMemberId, int page, int pageSize) {
        List<Media> elements = jdbcTemplate.query("SELECT * FROM crew NATURAL JOIN media " +
                "WHERE staffMemberId = ? " +
                "OFFSET ? LIMIT ?", new Object[]{staffMemberId, pageSize * page, pageSize}, MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM crew " +
                "WHERE staffMemberId = ?", new Object[]{staffMemberId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
    }

    @Override
    public PageContainer<Media> getMedia(int staffMemberId, int page, int pageSize) {
        List<Media> elements = jdbcTemplate.query("(SELECT DISTINCT(mediaid),type,title,description,image,length,releasedate,seasons,country  FROM director NATURAL JOIN media WHERE staffMemberId = ?)" +
                "UNION" +
                "(SELECT DISTINCT(mediaid),type,title,description,image,length,releasedate,seasons,country FROM crew NATURAL JOIN media WHERE staffMemberId = ?)" +
                "OFFSET ? LIMIT ?", new Object[]{staffMemberId, staffMemberId, pageSize * page, pageSize}, MEDIA_ROW_MAPPER);
        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM (" +
                "(SELECT director.mediaId FROM director WHERE staffmemberid = ?)" +
                "UNION" +
                "(SELECT crew.mediaId FROM crew WHERE staffmemberid = ?)) AS aux", new Object[]{staffMemberId, staffMemberId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(elements, page, pageSize, totalCount);
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


    @Override
    public Optional<Integer> getMediaCountByActor(int staffMemberId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM crew " +
                "WHERE staffMemberId = ?", new Object[]{staffMemberId}, COUNT_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<Integer> getMediaCountByDirector(int staffMemberId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM director " +
                "WHERE staffMemberId = ?", new Object[]{staffMemberId}, COUNT_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<Integer> getMediaCount(int staffMemberId) {
        return jdbcTemplate.query("SELECT COUNT(*) AS count FROM (" +
                "(SELECT director.mediaId FROM director WHERE staffmemberid = ?)" +
                "UNION" +
                "(SELECT crew.mediaId FROM crew WHERE staffmemberid = ?)) AS aux", new Object[]{staffMemberId, staffMemberId}, COUNT_ROW_MAPPER).stream().findFirst();
    }

}
