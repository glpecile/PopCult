package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.report.MediaCommentReport;
import ar.edu.itba.paw.webapp.dto.output.ReportMediaCommentDto;
import ar.edu.itba.paw.webapp.exceptions.CommentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReportNotFoundException;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("lists-comments")
@Component
public class ListCommentController {

    @Autowired
    private CommentService commentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListCommentController.class);

    @GET
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getListComment(@PathParam("id") int listCommentId) {
        final ListComment listComment = commentService.getListCommentById(listCommentId).orElseThrow(CommentNotFoundException::new);

        LOGGER.info("GET /lists-comments/{} (Returning list comment {}", listCommentId, listCommentId);
        return Response.ok().build(); //List Comment DTO missing
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

}
