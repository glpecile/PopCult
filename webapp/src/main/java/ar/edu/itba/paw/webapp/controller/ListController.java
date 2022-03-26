package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.input.ListDto;
import ar.edu.itba.paw.webapp.exceptions.EmptyBodyException;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
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

@Path("lists")
@Component
public class ListController {

    @Autowired
    private UserService userService;
    @Autowired
    private ListsService listsService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListController.class);

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getLists() {
        //TODO
        return null;
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createList(@Valid ListDto listDto) {
        if (listDto == null) {
            throw new EmptyBodyException();
        }

        final User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        final MediaList mediaList = listsService.createMediaList(user, listDto.getName(), listDto.getDescription(), listDto.isVisible(), listDto.isCollaborative());

        LOGGER.info("POST /lists: List {} created with id {}", mediaList.getListName(), mediaList.getMediaListId());
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(mediaList.getMediaListId())).build()).build();
    }
}
