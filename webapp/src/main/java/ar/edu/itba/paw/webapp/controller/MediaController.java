package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CommentService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Produces(value = {javax.ws.rs.core.MediaType.APPLICATION_JSON})
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
        final PageContainer<Media> listMedia = mediaService.getMediaByFilters(mediaTypes, page , pageSize, normalizedSortType, genreList, startYear, lastYear, term, listId);

        if (listMedia.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<MediaDto> mediaDtoList = MediaDto.fromMediaList(uriInfo, listMedia.getElements(), userService.getCurrentUser().orElse(null));
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaDto>>(mediaDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, listMedia, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results ", uriInfo.getPath(), listMedia.getCurrentPage(), listMedia.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getMedia(@PathParam("id") int mediaId) {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final User user = userService.getCurrentUser().orElse(null);

        LOGGER.info("GET /{}: Returning media {} {}", uriInfo.getPath(), mediaId, media.getTitle());
        return Response.ok(MediaDto.fromMedia(uriInfo, media, user)).build();
    }

    @GET
    @Path("/{id}/image")
    @Produces(value = {javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getMediaImage(@PathParam("id") int mediaId) throws URISyntaxException {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        return Response.status(Response.Status.SEE_OTHER).location(new URI(media.getImage())).build();
    }


    @GET
    @Path("/{id}/genres")
    @Produces(value = {javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getMediaGenres(@PathParam("id") int mediaId) {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final List<Genre> genres = media.getGenres();

        if (genres.isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<GenreDto> genreDtoList = GenreDto.fromGenreList(uriInfo, genres);

        LOGGER.info("GET /{}: Returning genres from media {} {}", uriInfo.getPath(), mediaId, media.getTitle());
        return Response.ok(new GenericEntity<List<GenreDto>>(genreDtoList) {
        }).build();
    }

    @GET
    @Path("/{id}/lists")
    @Produces(value = {javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getMediaLists(@PathParam("id") int mediaId,
                                  @QueryParam("page") @DefaultValue(defaultPage) int page,
                                  @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final PageContainer<MediaList> lists = listsService.getListsIncludingMedia(media, page, pageSize);

        if (lists.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<ListDto> listsDto = ListDto.fromListList(uriInfo, lists.getElements(),userService.getCurrentUser().orElse(null));
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ListDto>>(listsDto) {
        });
        ResponseUtils.setPaginationLinks(response, lists, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results ", uriInfo.getPath(), lists.getCurrentPage(), lists.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{id}/studios")
    @Produces(value = {javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getMediaStudios(@PathParam("id") int mediaId) {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final List<Studio> studios = media.getStudios();

        if (studios.isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<StudioDto> studioDtoList = StudioDto.fromStudioList(uriInfo, studios);

        LOGGER.info("GET /{}: Returning studios from media {} {}", uriInfo.getPath(), mediaId, media.getTitle());
        return Response.ok(new GenericEntity<List<StudioDto>>(studioDtoList) {
        }).build();
    }

    @GET
    @Path("/{id}/staff")
    @Produces(value = {javax.ws.rs.core.MediaType.APPLICATION_JSON})
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

        LOGGER.info("GET /{}: Returning staff members from media {} {}", uriInfo.getPath(), mediaId, media.getTitle());
        return Response.ok(new GenericEntity<List<StaffDto>>(listsDto) {
        }).build();
    }

    @GET
    @Path("/{id}/comments")
    @Produces(value = {javax.ws.rs.core.MediaType.APPLICATION_JSON})
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
    @Produces(value = {javax.ws.rs.core.MediaType.APPLICATION_JSON})
    @Consumes(value = {javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response createMediaComment(@PathParam("id") int mediaId,
                                       @Valid @NotEmptyBody CommentInputDto commentInputDto) {
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);

        final MediaComment mediaComment = commentService.addCommentToMedia(user, media, commentInputDto.getBody());

        LOGGER.info("POST /{}: Comment created with id {}", uriInfo.getPath(), mediaComment.getCommentId());
        return Response.created(uriInfo.getBaseUriBuilder().path("media-comments").path(String.valueOf(mediaComment.getCommentId())).build()).build();
    }
}
/*
    @RequestMapping("/")
    public ModelAndView home() {
        LOGGER.debug("Trying to access home.");
        final ModelAndView mav = new ModelAndView("principal/primary/home");
        final PageContainer<Media> latestFilmsContainer = mediaService.getLatestMediaList(MediaType.FILMS, 0, itemsPerContainer);
        final PageContainer<Media> latestSeriesContainer = mediaService.getLatestMediaList(MediaType.SERIE, 0, itemsPerContainer);
        final List<ListCover> recentlyAddedCovers = getListCover(listsService.getLastAddedLists(0, lastAddedAmount).getElements(), listsService);
        mav.addObject("latestFilmsList", latestFilmsContainer.getElements());
        mav.addObject("latestSeriesList", latestSeriesContainer.getElements());
        mav.addObject("recentlyAddedLists", recentlyAddedCovers);
        mav.addObject("scrollItemsAmount", itemsPerContainer);

        userService.getCurrentUser().ifPresent(user -> {
            final PageContainer<Media> discoveryFilmContainer = favoriteService.getRecommendationsBasedOnFavMedia(MediaType.FILMS, user, 0, itemsPerContainer);
            final PageContainer<Media> discoverySeriesContainer = favoriteService.getRecommendationsBasedOnFavMedia(MediaType.SERIE, user, 0, itemsPerContainer);
            final PageContainer<MediaList> discoveryListsContainer = favoriteService.getRecommendationsBasedOnFavLists(user, defaultValue - 1, lastAddedAmount);
            final List<ListCover> discoveryListsCovers = getListCover(discoveryListsContainer.getElements(), listsService);
            mav.addObject("discoveryFilmContainer", discoveryFilmContainer);
            mav.addObject("discoverySeriesContainer", discoverySeriesContainer);
            mav.addObject("discoveryListsCovers", discoveryListsCovers);
        });
        LOGGER.info("Home was accessed successfully");
        return mav;
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.GET})
    public ModelAndView mediaDescription(@PathVariable("mediaId") final int mediaId,
                                         @ModelAttribute("commentForm") CommentForm commentForm) {
        LOGGER.debug("Trying to access media {} description", mediaId);
        final ModelAndView mav = new ModelAndView("media/mediaDescription");
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final PageContainer<MediaList> mediaList = listsService.getListsIncludingMedia(media, defaultValue - 1, listsPerPage);
        final List<ListCover> relatedListsCover = getListCover(mediaList.getElements(), listsService);
        final PageContainer<MediaComment> mediaCommentsContainer = commentService.getMediaComments(media, defaultValue - 1, itemsPerPage);
        mav.addObject("media", media);
        mav.addObject("relatedLists", relatedListsCover);
        mav.addObject("mediaCommentsContainer", mediaCommentsContainer);
        userService.getCurrentUser().ifPresent(user -> {
            mav.addObject("currentUser", user);
            mav.addObject("isFavoriteMedia", favoriteService.isFavorite(media, user));
            mav.addObject("isWatchedMedia", watchService.isWatched(media, user));
            mav.addObject("isToWatchMedia", watchService.isToWatch(media, user));
            final List<MediaList> userLists = listsService.getUserEditableLists(user, defaultValue - 1, itemsPerPage).getElements();
            mav.addObject("userLists", userLists);
        });
        LOGGER.info("Access to media {} description was successful", mediaId);
        return mav;
    }

    @RequestMapping(value = "/media/{mediaId}/lists")
    public ModelAndView mediaLists(@PathVariable("mediaId") final int mediaId,
                                   @RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("media/mediaRelatedLists");
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final PageContainer<MediaList> mediaList = listsService.getListsIncludingMedia(media, page - 1, itemsPerPage);
        final List<ListCover> relatedListsCover = getListCover(mediaList.getElements(), listsService);
        mav.addObject("media", media);
        mav.addObject("relatedLists", relatedListsCover);
        mav.addObject("mediaListContainer", mediaList);
        return mav;
    }

    @RequestMapping(value = "/media/{mediaId}/comments")
    public ModelAndView mediaComments(@PathVariable("mediaId") final int mediaId,
                                      @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.debug("Trying to access media {} comments", mediaId);
        final ModelAndView mav = new ModelAndView("media/mediaCommentDetails");
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final PageContainer<MediaComment> mediaCommentsContainer = commentService.getMediaComments(media, page - 1, itemsPerPage);
        userService.getCurrentUser().ifPresent(user -> mav.addObject("currentUser", user));
        mav.addObject("media", media);
        mav.addObject("mediaCommentsContainer", mediaCommentsContainer);
        LOGGER.info("Access to media {} comments was successful", mediaId);
        return mav;
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "comment")
    public ModelAndView addComment(@PathVariable("mediaId") final int mediaId,
                                   @Valid @ModelAttribute("commentForm") final CommentForm form,
                                   final BindingResult errors) {
        LOGGER.debug("Trying to add a comment to media {}", mediaId);
        if (errors.hasErrors()) {
            LOGGER.error("Adding comment to media {} failed.", mediaId);
            return mediaDescription(mediaId, form);
        }
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        commentService.addCommentToMedia(user, media, form.getBody());
        LOGGER.info("Comment added to media {}", mediaId);
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}/deleteComment/{commentId}", method = {RequestMethod.DELETE, RequestMethod.POST}, params = "currentURL")
    public ModelAndView deleteComment(@PathVariable("mediaId") final int mediaId,
                                      @PathVariable("commentId") int commentId,
                                      @RequestParam("currentURL") final String currentURL) {
        LOGGER.debug("Trying to delete comment from media {}", mediaId);
        MediaComment comment = commentService.getMediaCommentById(commentId).orElseThrow(CommentNotFoundException::new);
        commentService.deleteCommentFromMedia(comment);
        LOGGER.info("Comment from media {} deleted", mediaId);
        return new ModelAndView("redirect:/media/" + mediaId + currentURL);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST})
    public ModelAndView addMediaToList(@PathVariable("mediaId") final int mediaId,
                                       @RequestParam("mediaListId") final int mediaListId) {
        LOGGER.debug("Trying to add media {} to list {}", mediaId, mediaListId);
        MediaList mediaList = listsService.getMediaListById(mediaListId).orElseThrow(MediaNotFoundException::new);
        Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        try {
            listsService.addToMediaList(mediaList, media);
        } catch (RuntimeException e) {
            LOGGER.error("Media {} is already in list {}.", mediaId, mediaListId);
            return new ModelAndView("redirect:/media/" + mediaId);
        }
        LOGGER.error("Media {} was added to list {} successfully", mediaId, mediaListId);
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "addFav")
    public ModelAndView addMediaToFav(@PathVariable("mediaId") final int mediaId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        LOGGER.debug("{} is trying to add media {} to favorites", user.getUsername(), mediaId);
        favoriteService.addMediaToFav(media, user);
        LOGGER.info("{} added media {} to favorites", user.getUsername(), mediaId);
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "deleteFav")
    public ModelAndView deleteMediaFromFav(@PathVariable("mediaId") final int mediaId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        LOGGER.debug("{} is trying to delete media {} from favorites", user.getUsername(), mediaId);
        favoriteService.deleteMediaFromFav(media, user);
        LOGGER.info("{} deleted media {} from favorites", user.getUsername(), mediaId);
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "addWatched")
    public ModelAndView addMediaToWatched(@PathVariable("mediaId") final int mediaId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        LOGGER.debug("{} is trying to add media {} to watched", user.getUsername(), mediaId);
//        watchService.addWatchedMedia(media, user);
        LOGGER.info("{} added media {} to watched", user.getUsername(), mediaId);
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "deleteWatched")
    public ModelAndView deleteMediaFromWatched(@PathVariable("mediaId") final int mediaId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        LOGGER.debug("{} is trying to delete media {} from watched", user.getUsername(), mediaId);
        watchService.deleteWatchedMedia(media, user);
        LOGGER.info("{} deleted media {} from watched", user.getUsername(), mediaId);
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "addWatchlist")
    public ModelAndView addMediaToWatchlist(@PathVariable("mediaId") final int mediaId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        LOGGER.debug("{} is trying to add media {} to watch list", user.getUsername(), mediaId);
        watchService.addMediaToWatch(media, user);
        LOGGER.info("{} added media {} to watch list", user.getUsername(), mediaId);
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "deleteWatchlist")
    public ModelAndView deleteMediaFromWatchlist(@PathVariable("mediaId") final int mediaId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        LOGGER.debug("{} is trying to delete media {} from watch list", user.getUsername(), mediaId);
        watchService.deleteToWatchMedia(media, user);
        LOGGER.info("{} deleted media {} from watch list", user.getUsername(), mediaId);
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/films")
    public ModelAndView films(@RequestParam(value = "page", defaultValue = "1") final int page,
                              @Valid @ModelAttribute("filterForm") final FilterForm filterForm,
                              final BindingResult errors){
        LOGGER.debug("Trying to access films");
        if(errors.hasErrors()){
            LOGGER.warn("Invalid FilterForm, redirecting to /media/films.");
            return new ModelAndView("redirect:/media/films");
        }
        final ModelAndView mav = new ModelAndView("principal/primary/films");
        final List<Genre> genres = NormalizerUtils.getNormalizedGenres(filterForm.getGenres());
        final SortType sortType = NormalizerUtils.getNormalizedSortType(filterForm.getSortType());
        final List<MediaType> mediaTypes = Collections.singletonList(MediaType.FILMS);
        final PageContainer<Media> mostLikedFilms = favoriteService.getMostLikedMedia(MediaType.FILMS, 0, itemsPerContainer);
        final PageContainer<Media> mediaListContainer = mediaService.getMediaByFilters(mediaTypes,page-1,itemsPerPage, sortType,genres,filterForm.getStartYear(), filterForm.getLastYear(), null, 0 );
        mav.addObject("mostLikedFilms", mostLikedFilms.getElements());
        mav.addObject("mediaListContainer", mediaListContainer);
        mav.addObject("sortTypes", FilterUtils.getSortTypes(messageSource));
        mav.addObject("genreTypes", FilterUtils.getGenres(messageSource));
        mav.addObject("decadesType", FilterUtils.getDecades(messageSource));
        LOGGER.info("Access to films successfully");
        return mav;
    }



    @RequestMapping("/media/series")
    public ModelAndView series(@RequestParam(value = "page", defaultValue = "1") final int page,
                               @Valid @ModelAttribute("filterForm") final FilterForm filterForm,
                               final BindingResult errors){
        LOGGER.debug("Trying to access series");
        if(errors.hasErrors()){
            LOGGER.warn("Invalid FilterForm, redirecting to /media/series.");
            return new ModelAndView("redirect:media/series");
        }
        final ModelAndView mav = new ModelAndView("principal/primary/series");
        final PageContainer<Media> mostLikedSeries = favoriteService.getMostLikedMedia(MediaType.SERIE, 0, itemsPerContainer);
        final List<Genre> genres = NormalizerUtils.getNormalizedGenres(filterForm.getGenres());
        final SortType sortType = NormalizerUtils.getNormalizedSortType(filterForm.getSortType());
        final List<MediaType> mediaTypes = Collections.singletonList(MediaType.SERIE);
        final PageContainer<Media> mediaListContainer = mediaService.getMediaByFilters(mediaTypes,page-1,itemsPerPage, sortType,genres,filterForm.getStartYear(), filterForm.getLastYear(), null, 0);
        mav.addObject("mostLikedSeries", mostLikedSeries.getElements());
        mav.addObject("mediaListContainer", mediaListContainer);
        mav.addObject("sortTypes", FilterUtils.getSortTypes(messageSource));
        mav.addObject("genreTypes", FilterUtils.getGenres(messageSource));
        mav.addObject("decadesType", FilterUtils.getDecades(messageSource));
        LOGGER.info("Access to series successfully");
        return mav;
    }

    @RequestMapping(value = {"/media/series","/media/films"}, method = {RequestMethod.GET}, params = "clear")
    public ModelAndView clearFilters(HttpServletRequest request, @ModelAttribute("filterForm") final FilterForm filterForm){
        return new ModelAndView("redirect:" + request.getHeader("referer").replaceAll("\\?.*",""));
    }
}
*/