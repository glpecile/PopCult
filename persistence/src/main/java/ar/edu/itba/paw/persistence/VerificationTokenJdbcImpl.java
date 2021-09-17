package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.VerificationTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class VerificationTokenJdbcImpl implements VerificationTokenDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public VerificationTokenJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("verificationToken");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS verificationToken(" +
                "userId INT NOT NULL," +
                "token TEXT NOT NULL," +
                "FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE" +
                ")");
    }

    @Override
    public void createVerificationToken(int userId, String token, Date expiryDate) {
        Map<String, Object> data = new HashMap<>();
        data.put("userid", userId);
        data.put("token", token);
        data.put("expiryDate", expiryDate);
        jdbcInsert.executeAndReturnKey(data);
    }
}

