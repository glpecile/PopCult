package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.interfaces.exceptions.MediaAlreadyInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.input.CommentInputDto;
import ar.edu.itba.paw.webapp.dto.input.ListInputDto;
import ar.edu.itba.paw.webapp.dto.output.ListCommentDto;
import ar.edu.itba.paw.webapp.dto.output.ListDto;
import ar.edu.itba.paw.webapp.dto.output.MediaInListDto;
import ar.edu.itba.paw.webapp.exceptions.EmptyBodyException;
import ar.edu.itba.paw.webapp.exceptions.ListNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.MediaNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("lists")
@Component
public class ListController {

    @Autowired
    private UserService userService;
    @Autowired
    private ListsService listsService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private CommentService commentService;

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
    public Response createList(@Valid ListInputDto listDto) {
        if (listDto == null) {
            throw new EmptyBodyException();
        }

        final User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        final MediaList mediaList = listsService.createMediaList(user, listDto.getName(), listDto.getDescription(), listDto.isVisible(), listDto.isCollaborative());

        LOGGER.info("POST /lists: List {} created with id {}", mediaList.getListName(), mediaList.getMediaListId());
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(mediaList.getMediaListId())).build()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getList(@PathParam("id") int listId) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final User user = userService.getCurrentUser().orElse(null);

        LOGGER.info("GET /lists/{}: Returning list {} {}", listId, listId, mediaList.getListName());
        return Response.ok(ListDto.fromList(uriInfo, mediaList, user)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response editList(@PathParam("id") int listId,
                             @Valid ListInputDto listDto) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        listsService.updateList(mediaList, listDto.getName(), listDto.getDescription(), listDto.isVisible(), listDto.isCollaborative());

        LOGGER.info("PUT /lists/{}: list {} updated", listId, listId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteList(@PathParam("id") int listId) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        listsService.deleteList(mediaList);

        LOGGER.info("DELETE /lists/{}: list {} deleted", listId, listId);
        return Response.noContent().build();
    }

    /**
     * Media In List
     */
    @GET
    @Path("/{id}/media")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getMediaInList(@PathParam("id") int listId,
                                   @QueryParam("page") @DefaultValue(defaultPage) int page,
                                   @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        final PageContainer<Media> mediaInList = listsService.getMediaInList(mediaList, page, pageSize);

        if (mediaInList.getElements().isEmpty()) {
            LOGGER.info("GET /lists/{}/media: Returning empty list.", listId);
            return Response.noContent().build();
        }

        final List<MediaInListDto> mediaInListDtoList = MediaInListDto.fromMediaList(uriInfo, mediaList, mediaInList.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaInListDto>>(mediaInListDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, mediaInList, uriInfo);

        LOGGER.info("GET /lists/{}/media: Returning page {} with {} results.", listId, mediaInList.getCurrentPage(), mediaInList.getElements().size());
        return response.build();
    }

    @PUT
    @Path("/{listId}/media/{mediaId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response addMediaToList(@PathParam("listId") int listId,
                                   @PathParam("mediaId") int mediaId) throws MediaAlreadyInListException {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        listsService.addToMediaList(mediaList, media);

        LOGGER.info("PUT /lists/{}/media/{}: media {} added to list {}.", listId, mediaId, mediaId, listId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{listId}/media/{mediaId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response removeMediaFromList(@PathParam("listId") int listId,
                                        @PathParam("mediaId") int mediaId) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        listsService.deleteMediaFromList(mediaList, media);

        LOGGER.info("DELETE /lists/{}/media/{}: media {} removed from list {}.", listId, mediaId, mediaId, listId);
        return Response.noContent().build();
    }

    /**
     * Comments
     */
    @GET
    @Path("/{id}/comments")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getListComments(@PathParam("id") int listId,
                                    @QueryParam("page") @DefaultValue(defaultPage) int page,
                                    @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        final PageContainer<ListComment> listComments = commentService.getListComments(mediaList, page, pageSize);

        if (listComments.getElements().isEmpty()) {
            LOGGER.info("GET /lists/{}/comments: Returning empty list.", listId);
            return Response.noContent().build();
        }

        final List<ListCommentDto> listCommentDtoList = ListCommentDto.fromListCommentList(uriInfo, listComments.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ListCommentDto>>(listCommentDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, listComments, uriInfo);

        LOGGER.info("GET /lists/{}/comments: Returning page {} with {} results.", listId, listComments.getCurrentPage(), listComments.getElements().size());
        return response.build();
    }

    @POST
    @Path("/{id}/comments")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createListComments(@PathParam("id") int listId,
                                       @Valid CommentInputDto commentInputDto) {
        if (commentInputDto == null) {
            throw new EmptyBodyException();
        }

        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);

        final ListComment listComment = commentService.addCommentToList(user, mediaList, commentInputDto.getBody());

        LOGGER.info("POST /lists/{}/comments: Comment created with id {}", listId, listComment.getCommentId());
        return Response.created(uriInfo.getBaseUriBuilder().path("lists-comments").path(String.valueOf(listComment.getCommentId())).build()).build();
    }

    /**
     * Collaborators
     */

    /**
     * Forks
     */

    /**
     * Collaborator Requests
     */

    /**
     * Reports
     */
}
