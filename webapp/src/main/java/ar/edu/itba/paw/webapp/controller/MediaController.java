package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.ImageSize;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.staff.Role;
import ar.edu.itba.paw.models.staff.RoleType;
import ar.edu.itba.paw.models.staff.Studio;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.input.CommentInputDto;
import ar.edu.itba.paw.webapp.dto.output.*;
import ar.edu.itba.paw.webapp.dto.validation.annotations.NotEmptyBody;
import ar.edu.itba.paw.webapp.exceptions.MediaNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.mediaType.VndType;
import ar.edu.itba.paw.webapp.utilities.NormalizerUtils;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

@Path("media")
@Component
public class MediaController {

    @Autowired
    private MediaService mediaService;
    @Autowired
    private ListsService listsService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaController.class);

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @GET
    @Produces(value = {VndType.APPLICATION_MEDIA})
    public Response getMedias(@QueryParam("page") @DefaultValue(defaultPage) int page,
                              @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize,
                              @QueryParam("type") List<String> types,
                              @QueryParam("genres") List<String> genres,
                              @QueryParam("sort-type") @Pattern(regexp = "(?i)DATE|TITLE(?i)|POPULARITY(?i)") @DefaultValue("TITLE") String sortType,
                              @QueryParam("decade") @Size(max = 4) @Pattern(regexp = "ALL|19[0-9]0|20[0-2]0") String decade,
                              @QueryParam("query") @Size(max = 100) @Pattern(regexp = "[^/><%]+") String term,
                              @QueryParam("not-in-list") Integer listId) {
        final List<MediaType> mediaTypes = NormalizerUtils.getNormalizedMediaType(types);
        final List<Genre> genreList = NormalizerUtils.getNormalizedGenres(genres);
        final SortType normalizedSortType = NormalizerUtils.getNormalizedSortType(sortType);
        LocalDateTime startYear = NormalizerUtils.getStartYear(decade);
        LocalDateTime lastYear = NormalizerUtils.getLastYear(decade);
        final PageContainer<Media> mediaList = mediaService.getMediaByFilters(mediaTypes, page, pageSize, normalizedSortType, genreList, startYear, lastYear, term, listId);

        if (mediaList.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<MediaDto> mediaDtoList = MediaDto.fromMediaList(uriInfo, mediaList.getElements(), userService.getCurrentUser().orElse(null));
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaDto>>(mediaDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, mediaList, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results ", uriInfo.getPath(), mediaList.getCurrentPage(), mediaList.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {VndType.APPLICATION_MEDIA})
    public Response getMedia(@PathParam("id") int mediaId) {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final User user = userService.getCurrentUser().orElse(null);

        LOGGER.info("GET /{}: Returning media {} {}", uriInfo.getPath(), mediaId, media.getTitle());
        return Response.ok(MediaDto.fromMedia(uriInfo, media, user)).build();
    }

    @GET
    @Path("/{id}/image")
    public Response getMediaImage(@PathParam("id") int mediaId,
                                  @QueryParam("size") @DefaultValue("md") String size ) throws URISyntaxException {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        Response.ResponseBuilder response = Response.noContent();
        ResponseUtils.setUnconditionalCache(response);
        return response.status(Response.Status.SEE_OTHER).location(new URI(media.getImage(ImageSize.valueOf(size.toLowerCase())))).build();
    }


    @GET
    @Path("/{id}/genres")
    @Produces(value = {VndType.APPLICATION_GENRES})
    public Response getMediaGenres(@PathParam("id") int mediaId) {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final List<Genre> genres = media.getGenres();

        if (genres.isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<GenreDto> genreDtoList = GenreDto.fromGenreList(uriInfo, genres);

        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<GenreDto>>(genreDtoList) {
        });
        ResponseUtils.setUnconditionalCache(response);

        LOGGER.info("GET /{}: Returning genres from media {} {}", uriInfo.getPath(), mediaId, media.getTitle());
        return response.build();
    }

    @GET
    @Path("/{id}/lists")
    @Produces(value = {VndType.APPLICATION_LISTS})
    public Response getMediaLists(@PathParam("id") int mediaId,
                                  @QueryParam("page") @DefaultValue(defaultPage) int page,
                                  @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final PageContainer<MediaList> lists = listsService.getListsIncludingMedia(media, page, pageSize);

        if (lists.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<ListDto> listsDto = ListDto.fromListList(uriInfo, lists.getElements(), userService.getCurrentUser().orElse(null));
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ListDto>>(listsDto) {
        });
        ResponseUtils.setPaginationLinks(response, lists, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results ", uriInfo.getPath(), lists.getCurrentPage(), lists.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{id}/studios")
    @Produces(value = {VndType.APPLICATION_STUDIOS})
    public Response getMediaStudios(@PathParam("id") int mediaId) {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final List<Studio> studios = media.getStudios();

        if (studios.isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<StudioDto> studioDtoList = StudioDto.fromStudioList(uriInfo, studios);

        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<StudioDto>>(studioDtoList) {
        });
        ResponseUtils.setUnconditionalCache(response);

        LOGGER.info("GET /{}: Returning studios from media {} {}", uriInfo.getPath(), mediaId, media.getTitle());
        return response.build();
    }

    @GET
    @Path("/{id}/staff")
    @Produces(value = {VndType.APPLICATION_STAFF})
    public Response getMediaStaff(@PathParam("id") int mediaId,
                                  @QueryParam("role") @NotNull String roleType) {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final RoleType role = NormalizerUtils.getNormalizedRoleType(roleType);
        final List<? extends Role> staffMembers;

        if (role == RoleType.ACTOR)
            staffMembers = media.getActorList();
        else
            staffMembers = media.getDirectorList();

        if (staffMembers.isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<StaffDto> listsDto = StaffDto.fromRoleList(uriInfo, staffMembers);

        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<StaffDto>>(listsDto) {
        });
        ResponseUtils.setUnconditionalCache(response);

        LOGGER.info("GET /{}: Returning staff members from media {} {}", uriInfo.getPath(), mediaId, media.getTitle());
        return response.build();
    }

    @GET
    @Path("/{id}/comments")
    @Produces(value = {VndType.APPLICATION_MEDIA_COMMENTS})
    public Response getMediaComments(@PathParam("id") int mediaId,
                                     @QueryParam("page") @DefaultValue(defaultPage) int page,
                                     @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        final PageContainer<MediaComment> mediaComments = commentService.getMediaComments(media, page, pageSize);

        if (mediaComments.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list.", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<MediaCommentDto> mediaCommentDtoList = MediaCommentDto.fromMediaCommentList(uriInfo, mediaComments.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaCommentDto>>(mediaCommentDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, mediaComments, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results.", uriInfo.getPath(), mediaComments.getCurrentPage(), mediaComments.getElements().size());
        return response.build();
    }

    @POST
    @Path("/{id}/comments")
    @Produces(value = {VndType.APPLICATION_MEDIA_COMMENTS})
    @Consumes(value = {VndType.APPLICATION_MEDIA_COMMENTS})
    public Response createMediaComment(@PathParam("id") int mediaId,
                                       @Valid @NotEmptyBody CommentInputDto commentInputDto) {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);

        final MediaComment mediaComment = commentService.addCommentToMedia(user, media, commentInputDto.getBody());

        LOGGER.info("POST /{}: Comment created with id {}", uriInfo.getPath(), mediaComment.getCommentId());
        return Response.created(uriInfo.getBaseUriBuilder().path("media-comments").path(String.valueOf(mediaComment.getCommentId())).build()).build();
    }
}
