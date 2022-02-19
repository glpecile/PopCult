package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CollaborativeListService;
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
        //TODO
        return null;
    }

    @PUT
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response acceptCollaborationRequest(@PathParam("id") int requestId) {
        //TODO
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteCollaborationRequest(@PathParam("id") int requestId) {
        //TODO
        return null;
    }
}
