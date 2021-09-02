package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.StaffDao;
import ar.edu.itba.paw.models.staff.StaffMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class StaffDaoJdbcImpl implements StaffDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert staffMemberjdbcInsert;
    private final SimpleJdbcInsert directorjdbcInsert;
    private final SimpleJdbcInsert castjdbcInsert;


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
                "FOREIGN KEY(mediaId) References media(mediaId)," +
                "FOREIGN KEY(staffMemberId) References staffMember(staffMemberId))");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS crew (" +
                "mediaId INTEGER," +
                "staffMemberId INTEGER," +
                "characterName VARCHAR(100) NOT NULL," +
                "FOREIGN KEY(mediaId) References media(mediaId)," +
                "FOREIGN KEY(staffMemberId) References staffMember(staffMemberId))");
    }

    @Override
    public Optional<StaffMember> getById(int personId) {
        return Optional.empty();
    }

    @Override
    public List<StaffMember> getPersonList() {
        return null;
    }

    @Override
    public List<StaffMember> getPersonMedia(int role, int mediaId, int pageSize) {
        return null;
    }
}
