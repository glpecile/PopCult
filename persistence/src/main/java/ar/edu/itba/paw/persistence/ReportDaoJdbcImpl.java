package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ReportDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ReportDaoJdbcImpl implements ReportDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertListReport;
    private final SimpleJdbcInsert jdbcInsertListCommentReport;
    private final SimpleJdbcInsert jdbcInsertMediaCommentReport;

    private static final RowMapper<ListReport> LIST_REPORT_ROW_MAPPER = RowMappers.LIST_REPORT_ROW_MAPPER;
    private static final RowMapper<ListCommentReport> LIST_COMMENT_REPORT_ROW_MAPPER = RowMappers.LIST_COMMENT_REPORT_ROW_MAPPER;
    private static final RowMapper<MediaCommentReport> MEDIA_COMMENT_REPORT_ROW_MAPPER = RowMappers.MEDIA_COMMENT_REPORT_ROW_MAPPER;
    private static final RowMapper<Integer> COUNT_ROW_MAPPER = RowMappers.COUNT_ROW_MAPPER;

    @Autowired
    public ReportDaoJdbcImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertListReport = new SimpleJdbcInsert(ds).withTableName("listReport").usingGeneratedKeyColumns("reportId");
        jdbcInsertListCommentReport = new SimpleJdbcInsert(ds).withTableName("listCommentReport").usingGeneratedKeyColumns("reportId");
        jdbcInsertMediaCommentReport = new SimpleJdbcInsert(ds).withTableName("mediaCommentReport").usingGeneratedKeyColumns("reportId");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS listReport (" +
                "reportId SERIAL PRIMARY KEY," +
                "listId INT NOT NULL," +
                "report TEXT NOT NULL," +
                "date DATE NOT NULL," +
                "FOREIGN KEY (listId) REFERENCES mediaList(mediaListId) ON DELETE CASCADE)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS listCommentReport (" +
                "reportId SERIAL PRIMARY KEY," +
                "listId INT NOT NULL," +
                "commentId INT NOT NULL," +
                "report TEXT NOT NULL," +
                "date DATE NOT NULL," +
                "FOREIGN KEY (listId) REFERENCES mediaList(mediaListId) ON DELETE CASCADE," +
                "FOREIGN KEY (commentId) REFERENCES listComment(commentId) ON DELETE CASCADE)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS mediaCommentReport (" +
                "reportId SERIAL PRIMARY KEY," +
                "mediaId INT NOT NULL," +
                "commentId INT NOT NULL," +
                "report TEXT NOT NULL," +
                "date DATE NOT NULL," +
                "FOREIGN KEY (mediaId) REFERENCES media(mediaId) ON DELETE CASCADE," +
                "FOREIGN KEY (commentId) REFERENCES mediaComment(commentId) ON DELETE CASCADE)");
    }

    @Override
    public void reportList(int listId, String report) {
        Map<String, Object> data = new HashMap<>();
        data.put("listId", listId);
        data.put("report", report);
        data.put("date", new Date());
        jdbcInsertListReport.execute(data);
    }

    @Override
    public void reportListComment(int listId, int commentId, String report) {
        Map<String, Object> data = new HashMap<>();
        data.put("listId", listId);
        data.put("commentId", commentId);
        data.put("report", report);
        data.put("date", new Date());
        jdbcInsertListCommentReport.execute(data);
    }

    @Override
    public void reportMediaComment(int mediaId, int commentId, String report) {
        Map<String, Object> data = new HashMap<>();
        data.put("mediaId", mediaId);
        data.put("commentId", commentId);
        data.put("report", report);
        data.put("data", new Date());
        jdbcInsertMediaCommentReport.execute(data);
    }

    @Override
    public Optional<ListReport> getListReportById(int reportId) {
        return jdbcTemplate.query("SELECT * FROM listReport JOIN mediaList ON listreport.listid = medialist.medialistid WHERE reportId = ?", new Object[]{reportId}, LIST_REPORT_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Optional<ListCommentReport> getListCommentReportById(int reportId) {
        return jdbcTemplate.query("SELECT * FROM listCommentReport NATURAL JOIN listComment WHERE reportId = ?", new Object[]{reportId}, LIST_COMMENT_REPORT_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Optional<MediaCommentReport> getMediaCommentReportById(int reportId) {
        return jdbcTemplate.query("SELECT * FROM mediaCommentReport NATURAL JOIN mediaComment WHERE reportId = ?", new Object[]{reportId}, MEDIA_COMMENT_REPORT_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public PageContainer<ListReport> getListReports(int page, int pageSize) {
        List<ListReport> listReportList = jdbcTemplate.query("SELECT * FROM listReport JOIN mediaList ON listreport.listid = medialist.medialistid ORDER BY date DESC OFFSET ? LIMIT ?",
                new Object[]{page * pageSize, pageSize},
                LIST_REPORT_ROW_MAPPER);
        int listReportCount = jdbcTemplate.query("SELECT COUNT(*) FROM listReport", COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<>(listReportList, page, pageSize, listReportCount);
    }

    @Override
    public PageContainer<ListCommentReport> getListCommentReports(int page, int pageSize) {
        List<ListCommentReport> listCommentReportList = jdbcTemplate.query("SELECT * FROM listCommentReport NATURAL JOIN listComment ORDER BY date DESC OFFSET ? LIMIT ?",
                new Object[]{page * pageSize, pageSize},
                LIST_COMMENT_REPORT_ROW_MAPPER);
        int listReportCount = jdbcTemplate.query("SELECT COUNT(*) FROM listCommentReport", COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<>(listCommentReportList, page, pageSize, listReportCount);
    }

    @Override
    public PageContainer<MediaCommentReport> getMediaCommentReports(int page, int pageSize) {
        List<MediaCommentReport> mediaCommentReportList = jdbcTemplate.query("SELECT * FROM mediaCommentReport NATURAL JOIN mediaComment ORDER BY date DESC OFFSET ? LIMIT ?",
                new Object[]{page * pageSize, pageSize},
                MEDIA_COMMENT_REPORT_ROW_MAPPER);
        int listReportCount = jdbcTemplate.query("SELECT COUNT(*) FROM mediaCommentReport", COUNT_ROW_MAPPER)
                .stream().findFirst().orElse(0);
        return new PageContainer<>(mediaCommentReportList, page, pageSize, listReportCount);
    }

    @Override
    public void deleteListReport(int reportId) {
        jdbcTemplate.update("DELETE FROM listReport WHERE reportId = ?", reportId);
    }

    @Override
    public void deleteListCommentReport(int reportId) {
        jdbcTemplate.update("DELETE FROM listCommentReport WHERE reportId = ?", reportId);
    }

    @Override
    public void deleteMediaCommentReport(int reportId) {
        jdbcTemplate.update("DELETE FROM mediaCommentReport WHERE reportId = ?", reportId);
    }
}
