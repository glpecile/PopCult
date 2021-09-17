package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.VerificationTokenDao;
import ar.edu.itba.paw.models.user.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class VerificationTokenJdbcImpl implements VerificationTokenDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Token> TOKEN_ROW_MAPPER =
            (rs, rowNum) -> new Token(
                    rs.getInt("userId"),
                    rs.getString("token"),
                    rs.getDate("expiryDate"));

    @Autowired
    public VerificationTokenJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("verificationToken");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS verificationToken(" +
                "userId INT NOT NULL," +
                "token TEXT NOT NULL," +
                "expiryDate DATE NOT NULL," +
                "FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE" +
                ")");
    }

    @Override
    public void createVerificationToken(int userId, String token, Date expiryDate) {
        Map<String, Object> data = new HashMap<>();
        data.put("userid", userId);
        data.put("token", token);
        data.put("expiryDate", expiryDate);
        jdbcInsert.execute(data);
    }

    @Override
    public Optional<Token> getToken(String token) {
        return jdbcTemplate.query("SELECT * FROM verificationToken WHERE token = ?", new Object[] {token}, TOKEN_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public void deleteToken(Token token) {
        jdbcTemplate.update("DELETE FROM verificationToken WHERE token = ?", token.getToken());
    }
}

