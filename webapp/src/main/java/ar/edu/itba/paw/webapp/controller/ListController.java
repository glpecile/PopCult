package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.CollaboratorRequestAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.ListAlreadyReportedException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.report.ListReport;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.input.*;
import ar.edu.itba.paw.webapp.dto.output.ListCommentDto;
import ar.edu.itba.paw.webapp.dto.output.ListDto;
import ar.edu.itba.paw.webapp.dto.output.MediaInListDto;
import ar.edu.itba.paw.webapp.dto.output.UserCollaboratorDto;
import ar.edu.itba.paw.webapp.dto.validation.annotations.NotEmptyBody;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.mediaType.VndType;
import ar.edu.itba.paw.webapp.utilities.NormalizerUtils;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private CollaborativeListService collaborativeListService;
    @Autowired
    private ReportService reportService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListController.class);

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";
    private static final int minMediaWithGenre = 1; //TODO check this number

    @GET
    @Produces(value = {VndType.APPLICATION_LISTS})
    public Response getLists(@QueryParam("page") @DefaultValue(defaultPage) int page,
                             @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize,
                             @QueryParam("genres") List<String> genres,
                             @QueryParam("sort-type") @Pattern(regexp = "(?i)DATE|TITLE(?i)|POPULARITY(?i)") @DefaultValue("TITLE") String sortType,
                             @QueryParam("decade") @Size(max = 4) @Pattern(regexp = "ALL|19[0-9]0|20[0-2]0") String decade,
                             @QueryParam("query") @Size(max = 100) @Pattern(regexp = "[^/><%]+") String term) {
        final List<Genre> genreList = NormalizerUtils.getNormalizedGenres(genres);
        final SortType normalizedSortType = NormalizerUtils.getNormalizedSortType(sortType);
        LocalDateTime startYear = NormalizerUtils.getStartYear(decade);
        LocalDateTime lastYear = NormalizerUtils.getLastYear(decade);
//        if (decade != null && decade.compareTo("ALL") > 0) {
//            startYear = LocalDateTime.of(Integer.parseInt(decade), 1, 1, 0, 0);
//            lastYear = LocalDateTime.of(Integer.parseInt(decade) + 9, 12, 31, 0, 0);
//        }
        final PageContainer<MediaList> mediaListPageContainer = listsService.getMediaListByFilters(page, pageSize, normalizedSortType, genreList, minMediaWithGenre, startYear, lastYear, term);

        if (mediaListPageContainer.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }
        final List<ListDto> listDtoList = ListDto.fromListList(uriInfo, mediaListPageContainer.getElements(), userService.getCurrentUser().orElse(null));
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ListDto>>(listDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, mediaListPageContainer, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results ", uriInfo.getPath(), mediaListPageContainer.getCurrentPage(), mediaListPageContainer.getElements().size());
        return response.build();
    }

    @POST
    @Produces(value = {VndType.APPLICATION_LISTS})
    @Consumes(value = {VndType.APPLICATION_LISTS})
    public Response createList(@Valid @NotEmptyBody ListInputDto listDto) {
        final User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        final MediaList mediaList = listsService.createMediaList(user, listDto.getName(), listDto.getDescription(), listDto.isVisible(), listDto.isCollaborative());

        LOGGER.info("POST /{}: List {} created with id {}", uriInfo.getPath(), mediaList.getListName(), mediaList.getMediaListId());
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(mediaList.getMediaListId())).build()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {VndType.APPLICATION_LISTS})
    public Response getList(@PathParam("id") int listId) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final User user = userService.getCurrentUser().orElse(null);

        LOGGER.info("GET /{}: Returning list {} {}", uriInfo.getPath(), listId, mediaList.getListName());
        return Response.ok(ListDto.fromList(uriInfo, mediaList, user)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = {VndType.APPLICATION_LISTS})
    public Response editList(@PathParam("id") int listId,
                             @Valid ListInputDto listDto) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        listsService.updateList(mediaList, listDto.getName(), listDto.getDescription(), listDto.isVisible(), listDto.isCollaborative());

        LOGGER.info("PUT /{}: list {} updated", uriInfo.getPath(), listId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteList(@PathParam("id") int listId) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        listsService.deleteList(mediaList);

        LOGGER.info("DELETE /{}: list {} deleted", uriInfo.getPath(), listId);
        return Response.noContent().build();
    }

    /**
     * Media In List
     */
    @GET
    @Path("/{id}/media")
    @Produces(value = {VndType.APPLICATION_MEDIA})
    public Response getMediaInList(@PathParam("id") int listId,
                                   @QueryParam("page") @DefaultValue(defaultPage) int page,
                                   @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        final PageContainer<Media> mediaInList = listsService.getMediaInList(mediaList, page, pageSize);

        if (mediaInList.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<MediaInListDto> mediaInListDtoList = MediaInListDto.fromMediaList(uriInfo, mediaList, mediaInList.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaInListDto>>(mediaInListDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, mediaInList, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), mediaInList.getCurrentPage(), mediaInList.getElements().size());
        return response.build();
    }

    @PATCH
    @Path("/{listId}/media")
    @Consumes(value = {VndType.APPLICATION_LISTS_PATCH_MEDIA})
    public Response manageMedia(@PathParam("listId") int listId,
                                @Valid @NotEmptyBody PatchMediaInListDto patchMediaInListDto) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final List<Media> mediaToAdd = mediaService.getById(patchMediaInListDto.getAdd());
        final List<Media> mediaToRemove = mediaService.getById(patchMediaInListDto.getRemove());

        listsService.manageMedia(mediaList, mediaToAdd, mediaToRemove);

        LOGGER.info("PATCH /{}: media {} added to list {}.", uriInfo.getPath(), mediaToAdd.stream().map(Media::getTitle).collect(Collectors.toList()), listId);
        LOGGER.info("PATCH /{}: media {} removed from list {}.", uriInfo.getPath(), mediaToRemove.stream().map(Media::getTitle).collect(Collectors.toList()), listId);
        return Response.noContent().build();
    }

    @GET
    @Path("/{listId}/media/{mediaId}")
    @Produces(value = {VndType.APPLICATION_MEDIA})
    public Response isMediaInList(@PathParam("listId") int listId,
                                  @PathParam("mediaId") int mediaId) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        if (!listsService.mediaAlreadyInList(mediaList, media)) {
            LOGGER.info("GET /{}: media {} is not in list {}.", uriInfo.getPath(), mediaId, listId);
            return Response.noContent().status(Response.Status.NOT_FOUND).build();
        }

        LOGGER.info("GET /{}: media {} is in list {}.", uriInfo.getPath(), mediaId, listId);
        return Response.ok(MediaInListDto.fromMedia(uriInfo, mediaList, media)).build();
    }

    @PUT
    @Path("/{listId}/media/{mediaId}")
    public Response addMediaToList(@PathParam("listId") int listId,
                                   @PathParam("mediaId") int mediaId) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        listsService.addToMediaList(mediaList, media);

        LOGGER.info("PUT /{}: media {} added to list {}.", uriInfo.getPath(), mediaId, listId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{listId}/media/{mediaId}")
    public Response removeMediaFromList(@PathParam("listId") int listId,
                                        @PathParam("mediaId") int mediaId) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        listsService.deleteMediaFromList(mediaList, media);

        LOGGER.info("DELETE /{}: media {} removed from list {}.", uriInfo.getPath(), mediaId, listId);
        return Response.noContent().build();
    }

    /**
     * Comments
     */
    @GET
    @Path("/{id}/comments")
    @Produces(value = {VndType.APPLICATION_LISTS_COMMENTS})
    public Response getListComments(@PathParam("id") int listId,
                                    @QueryParam("page") @DefaultValue(defaultPage) int page,
                                    @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        final PageContainer<ListComment> listComments = commentService.getListComments(mediaList, page, pageSize);

        if (listComments.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<ListCommentDto> listCommentDtoList = ListCommentDto.fromListCommentList(uriInfo, listComments.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ListCommentDto>>(listCommentDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, listComments, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), listComments.getCurrentPage(), listComments.getElements().size());
        return response.build();
    }

    @POST
    @Path("/{id}/comments")
    @Produces(value = {VndType.APPLICATION_LISTS_COMMENTS})
    @Consumes(value = {VndType.APPLICATION_LISTS_COMMENTS})
    public Response createListComment(@PathParam("id") int listId,
                                      @Valid @NotEmptyBody CommentInputDto commentInputDto) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);

        final ListComment listComment = commentService.addCommentToList(user, mediaList, commentInputDto.getBody());

        LOGGER.info("POST /{}: Comment created with id {}", uriInfo.getPath(), listComment.getCommentId());
        return Response.created(uriInfo.getBaseUriBuilder().path("lists-comments").path(String.valueOf(listComment.getCommentId())).build()).build();
    }

    /**
     * Collaborators
     */
    @GET
    @Path("/{id}/collaborators")
    @Produces(value = {VndType.APPLICATION_COLLABORATORS})
    public Response getListCollaborators(@PathParam("id") int listId,
                                         @QueryParam("page") @DefaultValue(defaultPage) int page,
                                         @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        final PageContainer<Request> collaborators = collaborativeListService.getListCollaborators(mediaList, page, pageSize);

        if (collaborators.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<UserCollaboratorDto> userCollaboratorDtoList = UserCollaboratorDto.fromRequestList(uriInfo, collaborators.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<UserCollaboratorDto>>(userCollaboratorDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, collaborators, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), collaborators.getCurrentPage(), collaborators.getElements().size());
        return response.build();
    }

    @PATCH
    @Path("/{listId}/collaborators")
    @Consumes(value = {VndType.APPLICATION_LISTS_PATCH_COLLABORATORS})
    public Response manageCollaborators(@PathParam("listId") int listId,
                                        @Valid @NotEmptyBody PatchCollaboratorsDto patchCollaboratorsDto) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        List<User> usersToAdd = userService.getByUsernames(patchCollaboratorsDto.getAdd());
        List<User> usersToRemove = userService.getByUsernames(patchCollaboratorsDto.getRemove());

        collaborativeListService.manageCollaborators(mediaList, usersToAdd, usersToRemove);

        LOGGER.info("PATCH /{}: users {} added to list {} as collaborators.", uriInfo.getPath(), usersToAdd.stream().map(User::getUsername).collect(Collectors.toList()), listId);
        LOGGER.info("PATCH /{}: users {} removed from list {} as collaborators.", uriInfo.getPath(), usersToRemove.stream().map(User::getUsername).collect(Collectors.toList()), listId);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/collaborators/{username}")
    @Produces(value = {VndType.APPLICATION_COLLABORATORS})
    public Response isCollaborator(@PathParam("id") int listId,
                                   @PathParam("username") String username) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        final Request request = collaborativeListService.getUserListCollabRequest(mediaList, user).orElseThrow(RequestNotFoundException::new);

        LOGGER.info("GET /{}: Returning user {} collaboration in list {}", uriInfo.getPath(), username, listId);
        return Response.ok(UserCollaboratorDto.fromRequest(uriInfo, request)).build();
    }

    @PUT
    @Path("/{id}/collaborators/{username}")
    public Response addCollaborator(@PathParam("id") int listId,
                                    @PathParam("username") String username) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);

        collaborativeListService.addCollaborator(mediaList, user);

        LOGGER.info("PUT /{}: User {} added to list {} as collaborator", uriInfo.getPath(), username, listId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/collaborators/{username}")
    public Response removeFromCollaborators(@PathParam("id") int listId,
                                            @PathParam("username") String username) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final User user = userService.getByUsername(username).orElseThrow(UserNotFoundException::new);
        final Request request = collaborativeListService.getUserListCollabRequest(mediaList, user).orElseThrow(RequestNotFoundException::new);

        collaborativeListService.deleteCollaborator(request);

        LOGGER.info("DELETE /{}: User {} removed from list {} as collaborator", uriInfo.getPath(), username, listId);
        return Response.noContent().build();
    }

    /**
     * Forks
     */
    @GET
    @Path("/{id}/forks")
    @Produces(value = {VndType.APPLICATION_LISTS})
    public Response getListFork(@PathParam("id") int listId,
                                @QueryParam("page") @DefaultValue(defaultPage) int page,
                                @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        final PageContainer<MediaList> listForks = listsService.getListForks(mediaList, page, pageSize);

        if (listForks.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<ListDto> listDtoList = ListDto.fromListList(uriInfo, listForks.getElements(), userService.getCurrentUser().orElse(null));
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ListDto>>(listDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, listForks, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), listForks.getCurrentPage(), listForks.getElements().size());
        return response.build();
    }

    @POST
    @Path("/{id}/forks")
    @Produces(value = {VndType.APPLICATION_LISTS})
    public Response createListFork(@PathParam("id") int listId) {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);

        final MediaList forkedList = listsService.createMediaListCopy(user, mediaList);

        LOGGER.info("POST /{}: List {} created with id {} forked from list {}", uriInfo.getPath(), forkedList.getListName(), forkedList.getMediaListId(), listId);
        return Response.created(uriInfo.getBaseUriBuilder().path("lists").path(String.valueOf(forkedList.getMediaListId())).build()).build();
    }

    /**
     * Collaborator Requests
     */
    @POST
    @Path("/{id}/requests")
    @Produces(value = {VndType.APPLICATION_COLLABORATORS_REQUESTS})
    public Response createListRequest(@PathParam("id") int listId) throws CollaboratorRequestAlreadyExistsException {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);
        final User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);

        Request request = collaborativeListService.makeNewRequest(mediaList, user);

        LOGGER.info("POST /{}: Collaboration request created with id {} for list {} and user {}", uriInfo.getPath(), request.getCollabId(), listId, user.getUsername());
        return Response.created(uriInfo.getBaseUriBuilder().path("collab-requests").path(String.valueOf(request.getCollabId())).build()).build();
    }

    /**
     * Reports
     */
    @POST
    @Path("/{id}/reports")
    @Produces(value = {VndType.APPLICATION_LISTS_REPORTS})
    public Response createListReport(@PathParam("id") int listId,
                                     @Valid @NotEmptyBody ReportDto reportDto) throws ListAlreadyReportedException {
        final MediaList mediaList = listsService.getMediaListById(listId).orElseThrow(ListNotFoundException::new);

        Optional<ListReport> listReport = reportService.reportList(mediaList, reportDto.getReport());

        if (listReport.isPresent()) {
            LOGGER.info("POST /{}: Report created with id {}", uriInfo.getPath(), listReport.get().getReportId());
            return Response.created(uriInfo.getBaseUriBuilder().path("lists-reports").path(String.valueOf(listReport.get().getReportId())).build()).build();
        } else {
            LOGGER.info("POST /{}: List {} deleted", uriInfo.getPath(), listId);
            return Response.noContent().build();
        }
    }
}
