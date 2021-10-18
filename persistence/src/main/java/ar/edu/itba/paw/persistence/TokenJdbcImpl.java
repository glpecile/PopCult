//package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.interfaces.TokenDao;
//import ar.edu.itba.paw.models.user.Token;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@Repository
//public class TokenJdbcImpl implements TokenDao {
//    private final JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert jdbcInsert;
//
//    private static final RowMapper<Token> TOKEN_ROW_MAPPER = RowMappers.TOKEN_ROW_MAPPER;
//
//    @Autowired
//    public TokenJdbcImpl(final DataSource ds) {
//        jdbcTemplate = new JdbcTemplate(ds);
//        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("token");
//    }
//
//    @Override
//    public void createToken(int userId, int type, String token, Date expiryDate) {
//        Map<String, Object> data = new HashMap<>();
//        data.put("userid", userId);
//        data.put("type", type);
//        data.put("token", token);
//        data.put("expiryDate", expiryDate);
//        jdbcInsert.execute(data);
//    }
//
//    @Override
//    public Optional<Token> getToken(String token) {
//        return jdbcTemplate.query("SELECT * FROM token WHERE token = ?", new Object[] {token}, TOKEN_ROW_MAPPER)
//                .stream().findFirst();
//    }
//
//    @Override
//    public void deleteToken(Token token) {
//        jdbcTemplate.update("DELETE FROM token WHERE token = ?", token.getToken());
//    }
//
//    @Override
//    public void renewToken(String token, Date newExpiryDate) {
//        jdbcTemplate.update("UPDATE token SET expirydate = ? WHERE token = ?", newExpiryDate, token);
//    }
//}
//
