package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CollaborativeListService;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.webapp.dto.output.CollaboratorRequestDto;
import ar.edu.itba.paw.webapp.exceptions.RequestNotFoundException;
import ar.edu.itba.paw.webapp.mediaType.VndType;
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
    @Produces(value = {VndType.APPLICATION_COLLABORATORS_REQUESTS})
    public Response getCollaborationRequest(@PathParam("id") int requestId) {
        final Request request = collaborativeListService.getById(requestId).orElseThrow(RequestNotFoundException::new);

        LOGGER.info("GET /{}: Returning notification {}", uriInfo.getPath(), requestId);
        return Response.ok(CollaboratorRequestDto.fromRequest(uriInfo, request)).build();
    }

    @PUT
    @Path("/{id}")
    public Response acceptCollaborationRequest(@PathParam("id") int requestId) {
        final Request request = collaborativeListService.getById(requestId).orElseThrow(RequestNotFoundException::new);

        collaborativeListService.acceptRequest(request);

        LOGGER.info("PUT /{}: Collaboration request {} accepted.", uriInfo.getPath(), requestId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCollaborationRequest(@PathParam("id") int requestId) {
        final Request request = collaborativeListService.getById(requestId).orElseThrow(RequestNotFoundException::new);

        collaborativeListService.rejectRequest(request);

        LOGGER.info("DELETE /{}: Collaboration request {} rejected.", uriInfo.getPath(), requestId);
        return Response.noContent().build();
    }
}
