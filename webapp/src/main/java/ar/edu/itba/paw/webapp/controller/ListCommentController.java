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

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @GET
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getListComment(@PathParam("id") int listCommentId) {
        final ListComment listComment = commentService.getListCommentById(listCommentId).orElseThrow(CommentNotFoundException::new);

        LOGGER.info("GET /lists-comments/{}: Returning list comment {}", listCommentId, listCommentId);
        return Response.ok(ListCommentDto.fromListComment(uriInfo, listComment)).build(); //List Comment DTO missing
    }

    @DELETE
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteListComment(@PathParam("id") int listCommentId) {
        final ListComment listComment = commentService.getListCommentById(listCommentId).orElseThrow(CommentNotFoundException::new);
        commentService.deleteCommentFromList(listComment);

        LOGGER.info("DELETE /lists-comments/{}: list comment {} deleted successfully", listCommentId, listCommentId);
        return Response.noContent().build();
    }

    @POST
    @Path("{id}/reports")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response reportListComment(@PathParam("id") int listCommentId,
                                      @Valid @NotEmptyBody ReportDto reportDto) throws CommentAlreadyReportedException {
        final ListComment listComment = commentService.getListCommentById(listCommentId).orElseThrow(CommentNotFoundException::new);

        final Optional<ListCommentReport> listCommentReport = reportService.reportListComment(listComment, reportDto.getReport());

        if (listCommentReport.isPresent()) {
            LOGGER.info("POST /lists-comments/{}/reports: Report created with id {}", listCommentId, listCommentReport.get().getReportId());
            return Response.created(uriInfo.getBaseUriBuilder().path("lists-comments-reports").path(String.valueOf(listCommentReport.get().getReportId())).build()).build();
        } else {
            LOGGER.info("POST /lists-comments/{}/reports: Comment {} deleted", listCommentId, listCommentId);
            return Response.noContent().build();
        }
    }
}
