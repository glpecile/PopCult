package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ImageDao;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ImageDaoJdbcImpl implements ImageDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Image> IMAGE_ROW_MAPPER = RowMappers.IMAGE_ROW_MAPPER;


    @Autowired
    public ImageDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("image").usingGeneratedKeyColumns("imageid");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS image(" +
                "imageid SERIAL PRIMARY KEY," +
                "photoBlob BYTEA," +
                "imageContentLength INT," +
                "imageContentType TEXT)");
    }

    @Override
    public Optional<Image> getImage(int imageId) {
        return jdbcTemplate.query("SELECT * FROM image WHERE imageId = ?", new Object[]{imageId}, IMAGE_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<Image> uploadImage(byte[] photoBlob, Integer imageContentLength, String imageContentType) {
        final Map<String, Object> data = new HashMap<>();
        data.put("photoBlob", photoBlob);
        data.put("imageContentLength", imageContentLength);
        data.put("imageContentType", imageContentType);
        KeyHolder key = jdbcInsert.executeAndReturnKeyHolder(data);
        return Optional.of(new Image((int) key.getKey(), photoBlob, imageContentLength, imageContentType));
    }
}
