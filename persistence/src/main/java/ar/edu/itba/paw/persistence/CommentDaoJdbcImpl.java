package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CommentDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.Comment;
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
public class CommentDaoJdbcImpl implements CommentDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertMediaComment;
    private final SimpleJdbcInsert jdbcInsertListComment;

    private static final RowMapper<Comment> COMMENT_ROW_MAPPER = RowMappers.COMMENT_ROW_MAPPER;

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;


    @Autowired
    public CommentDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertMediaComment = new SimpleJdbcInsert(ds).withTableName("mediacomment").usingGeneratedKeyColumns("commentid");
        jdbcInsertListComment = new SimpleJdbcInsert(ds).withTableName("listcomment").usingGeneratedKeyColumns("commentid");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS mediacomment (" +
                "commentId SERIAL PRIMARY KEY," +
                "userId INT NOT NULL," +
                "mediaId INT NOT NULL," +
                "description TEXT," +
                "FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE SET NULL," +
                "FOREIGN KEY (mediaId) REFERENCES media(mediaId) ON DELETE CASCADE)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS listcomment (" +
                "commentId SERIAL PRIMARY KEY," +
                "userId INT NOT NULL," +
                "listId INT NOT NULL," +
                "description TEXT," +
                "FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE SET NULL," +
                "FOREIGN KEY (listId) REFERENCES medialist(medialistid) ON DELETE CASCADE)");
    }

    @Override
    public Comment addCommentToMedia(int userId, int mediaId, String comment) {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("mediaId", mediaId);
        data.put("description", comment);
        KeyHolder keyHolder = jdbcInsertMediaComment.executeAndReturnKeyHolder(data);
        return new Comment((int) keyHolder.getKey(), userId,"", comment);
    }

    @Override
    public Comment addCommentToList(int userId, int listId, String comment) {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("listId", listId);
        data.put("description", comment);
        KeyHolder keyHolder = jdbcInsertListComment.executeAndReturnKeyHolder(data);
        return new Comment((int) keyHolder.getKey(), userId,"", comment);
    }

    @Override
    public PageContainer<Comment> getMediaComments(int mediaId, int page, int pageSize) {
        List<Comment> mediaComments = jdbcTemplate.query("SELECT * FROM mediacomment NATURAL JOIN users WHERE mediaId = ? OFFSET ? LIMIT ?", new Object[]{mediaId, page * pageSize, pageSize}, COMMENT_ROW_MAPPER);
        int commentsAmount = jdbcTemplate.query("SELECT COUNT(*) FROM mediacomment NATURAL JOIN users WHERE mediaId = ?", new Object[]{mediaId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(mediaComments, page, pageSize, commentsAmount);
    }

    @Override
    public PageContainer<Comment> getListComments(int listId, int page, int pageSize) {
        List<Comment> listComments =  jdbcTemplate.query("SELECT * FROM listcomment NATURAL JOIN users WHERE listId = ? OFFSET ? LIMIT ?", new Object[]{listId, page * pageSize, pageSize}, COMMENT_ROW_MAPPER);
        int commentsAmount = jdbcTemplate.query("SELECT COUNT(*) FROM listcomment NATURAL JOIN users WHERE listId = ?", new Object[]{listId}, COUNT_ROW_MAPPER).stream().findFirst().orElse(0);
        return new PageContainer<>(listComments, page, pageSize, commentsAmount);
    }

    @Override
    public void deleteCommentFromList(int commentId) {
        jdbcTemplate.update("DELETE FROM listcomment WHERE commentid = ?", commentId);
    }

    @Override
    public void deleteCommentFromMedia(int commentId) {
        jdbcTemplate.update("DELETE FROM mediacomment WHERE commentid = ?", commentId);
    }
}
