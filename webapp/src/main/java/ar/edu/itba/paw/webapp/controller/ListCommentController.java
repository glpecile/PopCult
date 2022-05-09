package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.interfaces.ReportService;
import ar.edu.itba.paw.interfaces.exceptions.CommentAlreadyReportedException;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.report.ListCommentReport;
import ar.edu.itba.paw.webapp.dto.input.ReportDto;
import ar.edu.itba.paw.webapp.dto.output.ListCommentDto;
import ar.edu.itba.paw.webapp.dto.validation.annotations.NotEmptyBody;
import ar.edu.itba.paw.webapp.exceptions.CommentNotFoundException;
import ar.edu.itba.paw.webapp.mediaType.VndType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

@Path("lists-comments")
@Component
public class ListCommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ReportService reportService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListCommentController.class);

    @GET
    @Path("/{id}")
    @Produces(value = {VndType.APPLICATION_LISTS_COMMENTS})
    public Response getListComment(@PathParam("id") int listCommentId) {
        final ListComment listComment = commentService.getListCommentById(listCommentId).orElseThrow(CommentNotFoundException::new);

        LOGGER.info("GET /{}: Returning list comment {}", uriInfo.getPath(), listCommentId);
        return Response.ok(ListCommentDto.fromListComment(uriInfo, listComment)).build(); //List Comment DTO missing
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteListComment(@PathParam("id") int listCommentId) {
        final ListComment listComment = commentService.getListCommentById(listCommentId).orElseThrow(CommentNotFoundException::new);
        commentService.deleteCommentFromList(listComment);

        LOGGER.info("DELETE /{}: list comment {} deleted successfully", uriInfo.getPath(), listCommentId);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/reports")
    @Produces(value = {VndType.APPLICATION_LISTS_COMMENTS_REPORTS})
    @Consumes(value = {VndType.APPLICATION_LISTS_COMMENTS_REPORTS})
    public Response reportListComment(@PathParam("id") int listCommentId,
                                      @Valid @NotEmptyBody ReportDto reportDto) throws CommentAlreadyReportedException {
        final ListComment listComment = commentService.getListCommentById(listCommentId).orElseThrow(CommentNotFoundException::new);

        final Optional<ListCommentReport> listCommentReport = reportService.reportListComment(listComment, reportDto.getReport());

        if (listCommentReport.isPresent()) {
            LOGGER.info("POST /{}: Report created with id {}", uriInfo.getPath(), listCommentReport.get().getReportId());
            return Response.created(uriInfo.getBaseUriBuilder().path("lists-comments-reports").path(String.valueOf(listCommentReport.get().getReportId())).build()).build();
        } else {
            LOGGER.info("POST /{}: Comment {} deleted", uriInfo.getPath(), listCommentId);
            return Response.noContent().build();
        }
    }
}
