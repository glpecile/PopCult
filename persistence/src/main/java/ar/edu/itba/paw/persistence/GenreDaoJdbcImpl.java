//package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.interfaces.GenreDao;
//import ar.edu.itba.paw.models.PageContainer;
//import ar.edu.itba.paw.models.lists.MediaList;
//import ar.edu.itba.paw.models.media.Genre;
//import ar.edu.itba.paw.models.media.Media;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.util.List;
//
//@Repository
//public class GenreDaoJdbcImpl implements GenreDao {
//    private final JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert genrejdbcInsert;
//
//    private static final RowMapper<String> STRING_NAME_ROW_MAPPER = RowMappers.STRING_NAME_ROW_MAPPER;
//
//    private static final RowMapper<Media> MEDIA_ROW_MAPPER = RowMappers.MEDIA_ROW_MAPPER;
//
//    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;
//
//    @Autowired
//    public GenreDaoJdbcImpl(final DataSource ds) {
//        jdbcTemplate = new JdbcTemplate(ds);
//        genrejdbcInsert = new SimpleJdbcInsert(ds).withTableName("genre").usingGeneratedKeyColumns("genreId");
//    }
//
//    @Override
//    public PageContainer<Media> getMediaByGenre(Genre genre, int page, int pageSize) {
//        List<Media> elements = jdbcTemplate.query("SELECT * FROM mediaGenre NATURAL JOIN media WHERE genreId = ? OFFSET ? LIMIT ?", new Object[] {genre, pageSize * page, pageSize},
//                MEDIA_ROW_MAPPER);
//        int totalCount = jdbcTemplate.query("SELECT COUNT(*) AS count FROM mediaGenre WHERE genreId = ?", new Object[] {genre}, COUNT_ROW_MAPPER)
//                .stream().findFirst().orElse(0);
//        return new PageContainer<>(elements,page,pageSize,totalCount);
//    }
//
//    @Override
//    public PageContainer<MediaList> getListsContainingGenre(Genre genre, int page, int pageSize, int minMatches, boolean visibility) {
//        return null;
//    }
//}
