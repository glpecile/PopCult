package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.interfaces.ReportService;
import ar.edu.itba.paw.interfaces.exceptions.CommentAlreadyReportedException;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import ar.edu.itba.paw.webapp.dto.input.ReportDto;
import ar.edu.itba.paw.webapp.dto.output.MediaCommentDto;
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

@Path("media-comments")
@Component
public class MediaCommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ReportService reportService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaCommentController.class);

    @GET
    @Path("/{id}")
    @Produces(value = {VndType.APPLICATION_MEDIA_COMMENTS})
    public Response getMediaComment(@PathParam("id") int mediaCommentId) {
        final MediaComment mediaCommentReport = commentService.getMediaCommentById(mediaCommentId).orElseThrow(CommentNotFoundException::new);

        LOGGER.info("GET /{}: Returning media comment report {}", uriInfo.getPath(), mediaCommentId);
        return Response.ok(MediaCommentDto.fromMediaComment(uriInfo, mediaCommentReport)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteMediaComment(@PathParam("id") int mediaCommentId) {
        final MediaComment mediaComment = commentService.getMediaCommentById(mediaCommentId).orElseThrow(CommentNotFoundException::new);
        commentService.deleteCommentFromMedia(mediaComment);

        LOGGER.info("DELETE /{}: list comment {} deleted successfully", uriInfo.getPath(), mediaCommentId);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/reports")
    @Produces(value = {VndType.APPLICATION_MEDIA_COMMENTS_REPORTS})
    @Consumes(value = {VndType.APPLICATION_MEDIA_COMMENTS_REPORTS})
    public Response reportMediaComment(@PathParam("id") int mediaCommentId,
                                       @Valid @NotEmptyBody ReportDto reportDto) throws CommentAlreadyReportedException {
        final MediaComment mediaComment = commentService.getMediaCommentById(mediaCommentId).orElseThrow(CommentNotFoundException::new);

        final Optional<MediaCommentReport> mediaCommentReport = reportService.reportMediaComment(mediaComment, reportDto.getReport());

        if (mediaCommentReport.isPresent()) {
            LOGGER.info("POST /{}: Report created with id {}", uriInfo.getPath(), mediaCommentReport.get().getReportId());
            return Response.created(uriInfo.getBaseUriBuilder().path("media-comments-reports").path(String.valueOf(mediaCommentReport.get().getReportId())).build()).build();
        } else {
            LOGGER.info("POST /{}: Comment {} deleted", uriInfo.getPath(), mediaCommentId);
            return Response.noContent().build();
        }
    }
}
