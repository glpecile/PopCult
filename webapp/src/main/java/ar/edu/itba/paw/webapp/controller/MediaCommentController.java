package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.webapp.dto.output.MediaCommentDto;
import ar.edu.itba.paw.webapp.dto.output.ReportMediaCommentDto;
import ar.edu.itba.paw.webapp.exceptions.CommentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class MediaCommentController {
    @Autowired
    private CommentService commentService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaCommentController.class);

    @GET
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getMediaComment(@PathParam("id") int mediaCommentId) {
        final MediaComment mediaCommentReport = commentService.getMediaCommentById(mediaCommentId).orElseThrow(CommentNotFoundException::new);

        LOGGER.info("GET /media-comment({}: Returning media comment report {}", mediaCommentId, mediaCommentId);
        return Response.ok(MediaCommentDto.fromMediaCommentDto(uriInfo, mediaCommentReport)).build();
    }

    
}
