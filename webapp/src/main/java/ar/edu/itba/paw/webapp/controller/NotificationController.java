package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.models.comment.Notification;
import ar.edu.itba.paw.webapp.dto.output.NotificationDto;
import ar.edu.itba.paw.webapp.exceptions.NotificationNotFoundException;
import ar.edu.itba.paw.webapp.mediaType.VndType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("notifications")
@Component
public class NotificationController {

    @Autowired
    private CommentService commentService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    @GET
    @Path("/{id}")
    @Produces(value = {VndType.APPLICATION_NOTIFICATIONS})
    public Response getNotification(@PathParam("id") int notificationId) {
        final Notification notification = commentService.getListCommentNotification(notificationId).orElseThrow(NotificationNotFoundException::new);

        LOGGER.info("GET /{}: Returning notification {}", uriInfo.getPath(), notificationId);
        return Response.ok(NotificationDto.fromNotification(uriInfo, notification)).build();
    }

    @PUT
    @Path("/{id}")
    public Response setNotificationAsOpened(@PathParam("id") int notificationId) {
        final Notification notification = commentService.getListCommentNotification(notificationId).orElseThrow(NotificationNotFoundException::new);

        commentService.setListCommentNotificationAsOpened(notification);

        LOGGER.info("PUT /{}: Notification {} marked as opened", uriInfo.getPath(), notificationId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteNotification(@PathParam("id") int notificationId) {
        final Notification notification = commentService.getListCommentNotification(notificationId).orElseThrow(NotificationNotFoundException::new);

        commentService.deleteListCommentNotification(notification);

        LOGGER.info("DELETE /{}: Notification {} deleted", uriInfo.getPath(), notificationId);
        return Response.noContent().build();
    }
}
