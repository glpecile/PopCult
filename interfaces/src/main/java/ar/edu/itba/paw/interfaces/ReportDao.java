package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.CommentAlreadyReportedException;
import ar.edu.itba.paw.interfaces.exceptions.ListAlreadyReportedException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import ar.edu.itba.paw.models.user.User;

import java.util.Optional;

public interface ReportDao {

    ListReport reportList(MediaList mediaList, User reporter, String report) throws ListAlreadyReportedException;

    ListCommentReport reportListComment(ListComment listComment, User reporter, String report) throws CommentAlreadyReportedException;

    MediaCommentReport reportMediaComment(MediaComment mediaComment, User reporter, String report) throws CommentAlreadyReportedException;

    Optional<ListReport> getListReportById(int reportId);

    Optional<ListCommentReport> getListCommentReportById(int reportId);

    Optional<MediaCommentReport> getMediaCommentReportById(int reportId);

    PageContainer<ListReport> getListReports(int page, int pageSize);

    PageContainer<ListCommentReport> getListCommentReports(int page, int pageSize);

    PageContainer<MediaCommentReport> getMediaCommentReports(int page, int pageSize);

    void deleteListReport(ListReport listReport);

    void deleteListCommentReport(ListCommentReport listCommentReport);

    void deleteMediaCommentReport(MediaCommentReport mediaCommentReport);
}
