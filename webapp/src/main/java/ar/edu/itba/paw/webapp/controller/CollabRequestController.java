package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CollaborativeListService;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.webapp.dto.output.CollaboratorRequestDto;
import ar.edu.itba.paw.webapp.dto.output.NotificationDto;
import ar.edu.itba.paw.webapp.exceptions.RequestNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("collab-requests")
public class CollabRequestController {

    @Autowired
    private CollaborativeListService collaborativeListService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(CollabRequestController.class);

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getCollaborationRequest(@PathParam("id") int requestId) {
        final Request request = collaborativeListService.getById(requestId).orElseThrow(RequestNotFoundException::new);

        LOGGER.info("GET /collab-requests/{}: Returning notification {}", requestId, requestId);
        return Response.ok(CollaboratorRequestDto.fromRequest(uriInfo, request)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response acceptCollaborationRequest(@PathParam("id") int requestId) {
        final Request request = collaborativeListService.getById(requestId).orElseThrow(RequestNotFoundException::new);

        collaborativeListService.acceptRequest(request);

        LOGGER.info("PUT /collab-requests/{}: Collaboration request {} accepted.", requestId, requestId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteCollaborationRequest(@PathParam("id") int requestId) {
        final Request request = collaborativeListService.getById(requestId).orElseThrow(RequestNotFoundException::new);

        collaborativeListService.rejectRequest(request);

        LOGGER.info("DELETE /collab-requests/{}: Collaboration request {} rejected.", requestId, requestId);
        return Response.noContent().build();
    }
}
