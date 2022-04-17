package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.staff.Role;
import ar.edu.itba.paw.models.staff.RoleType;
import ar.edu.itba.paw.models.staff.Studio;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.dto.output.*;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.form.CommentForm;
import ar.edu.itba.paw.webapp.form.FilterForm;
import ar.edu.itba.paw.webapp.utilities.FilterUtils;
import ar.edu.itba.paw.webapp.utilities.NormalizerUtils;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static ar.edu.itba.paw.webapp.utilities.ListCoverImpl.getListCover;

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
    private FavoriteService favoriteService;
    @Autowired
    private WatchService watchService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private StaffService staffService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaController.class);

    private static final int itemsPerPage = 12;
    private static final int itemsPerContainer = 6;
    private static final int listsPerPage = 4;
    private static final int lastAddedAmount = 6;
    private static final int defaultValue = 1;

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @GET
    @Produces(value={javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getMedias(@QueryParam("page") @DefaultValue(defaultPage) int page,
                              @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize,
                              @QueryParam("type") List<String> types,
                              @QueryParam("genres") List<String> genres,
                              @QueryParam("sort-type") @Pattern(regexp = "(?i)DATE|TITLE(?i)|POPULARITY(?i)") @DefaultValue("TITLE") String sortType,
                              @QueryParam("decade") @Size(max = 4) @Pattern(regexp = "ALL|19[0-9]0|20[0-2]0") String decade,
                              @QueryParam("query") @Size(max=100) @Pattern(regexp = "[^/><%]+") String term,
                              @QueryParam("not-in-list") Integer listId
    ){
        final List<MediaType> mediaTypes = NormalizerUtils.getNormalizedMediaType(types);
        final List<Genre> genreList = NormalizerUtils.getNormalizedGenres(genres);
        final SortType normalizedSortType =  NormalizerUtils.getNormalizedSortType(sortType);
        LocalDateTime startYear = null;
        LocalDateTime lastYear = null;
        if(decade != null && decade.compareTo("ALL") > 0) {
            startYear = LocalDateTime.of(Integer.parseInt(decade),1,1,0,0);
            lastYear = LocalDateTime.of(Integer.parseInt(decade) + 9,12,31,0,0);
        }
        final PageContainer<Media> listMedia = mediaService.getMediaByFilters(mediaTypes,page-1,pageSize,normalizedSortType,genreList,startYear,lastYear,term, listId);
        if(listMedia.getElements().isEmpty()){
            LOGGER.info("GET /media: Returning empty list");
            return Response.noContent().build();
        }

        final List<MediaDto> mediaDtoList = MediaDto.fromMediaList(uriInfo,listMedia.getElements(),userService.getCurrentUser().orElse(null));
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaDto>>(mediaDtoList){});
        ResponseUtils.setPaginationLinks(response,listMedia,uriInfo);

        LOGGER.info("GET /media: Returning page {} with {} results ", listMedia.getCurrentPage(), listMedia.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value={javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getMedia(@PathParam("id") int mediaId ){
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final User user = userService.getCurrentUser().orElse(null);

        LOGGER.info("GET /media/{}: Returning media {} {}", mediaId, mediaId, media.getTitle());
        return Response.ok(MediaDto.fromMedia(uriInfo,media, user)).build();

    }

    @GET
    @Path("/{id}/genres")
    @Produces(value={javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getMediaGenres(@PathParam("id") int mediaId){
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final List<Genre> genres = media.getGenres();

        LOGGER.info("GET /media/{}/genres: Returning genres from media {} {}", mediaId, mediaId, media.getTitle());
        final List<GenreDto> genresDtos = GenreDto.fromGenreList(uriInfo,genres);
        return Response.ok(new GenericEntity<List<GenreDto>>(genresDtos){}).build();
    }

    @GET
    @Path("/{id}/lists")
    @Produces(value={javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getMediaLists(@PathParam("id") int mediaId,
                                  @QueryParam("page") @DefaultValue(defaultPage) int page,
                                  @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize){
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final PageContainer<MediaList> lists = listsService.getListsIncludingMedia(media,page,pageSize);

        if(lists.getElements().isEmpty()){
            LOGGER.info("GET /media: Returning empty list");
            return Response.noContent().build();
        }

        final List<ListPeekDto> listsDto = ListPeekDto.fromMediaListList(uriInfo,lists.getElements(),media);
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ListPeekDto>>(listsDto){});
        ResponseUtils.setPaginationLinks(response,lists,uriInfo);

        LOGGER.info("GET /media: Returning page {} with {} results ", lists.getCurrentPage(), lists.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{id}/studios")
    @Produces(value={javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getMediaStudios(@PathParam("id") int mediaId){
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final PageContainer<Studio> studios = new PageContainer<>(media.getStudios(),Integer.parseInt(defaultPage), Integer.parseInt(defaultPageSize), media.getStudios().size());

        if(studios.getElements().isEmpty()){
            LOGGER.info("GET /media: Returning empty list");
            return Response.noContent().build();
        }

        final List<MediaStudioDto> listsDto = MediaStudioDto.fromStudioList(uriInfo,studios.getElements(), media);
        final Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<MediaStudioDto>>(listsDto){});
        ResponseUtils.setPaginationLinks(responseBuilder,studios,uriInfo);
        LOGGER.info("GET /media: Returning {} studios", studios.getElements().size());
        return responseBuilder.build();
    }

    @GET
    @Path("/{id}/staff")
    @Produces(value={javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getMediaStaff(@PathParam("id") int mediaId,
                                  @QueryParam("type") String roleType){
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final RoleType role = NormalizerUtils.getNormalizedRoleType(roleType);
        final List<? extends Role> staffMembers;

        if(role == RoleType.ACTOR)
            staffMembers = media.getActorList();
        else
            staffMembers = media.getDirectorList();


        if(staffMembers.isEmpty()){
            LOGGER.info("GET /media: Returning empty list");
            return Response.noContent().build();
        }

        final List<MediaStaffDto> listsDto = MediaStaffDto.fromStaffList(uriInfo,staffMembers, media);
        final Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<MediaStaffDto>>(listsDto){});

        LOGGER.info("GET /media: Returning {} staff members", staffMembers.size());
        return responseBuilder.build();
    }

    @GET
    @Path("/{id}/comments")
    @Produces(value={javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getComments(@PathParam("id") int mediaId,
                                @QueryParam("page") @DefaultValue(defaultPage) int page,
                                @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize){
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);

        final PageContainer<MediaComment> mediaComments = commentService.getMediaComments(media, page, pageSize);

        if (mediaComments.getElements().isEmpty()) {
            LOGGER.info("GET /media/{}/comments: Returning empty list.", mediaId);
            return Response.noContent().build();
        }

        final List<MediaCommentDto> mediaCommentDtoList = MediaCommentDto.fromMediaCommentList(uriInfo, mediaComments.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaCommentDto>>(mediaCommentDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, mediaComments, uriInfo);

        LOGGER.info("GET /media/{}/comments: Returning page {} with {} results.", mediaId, mediaComments.getCurrentPage(), mediaComments.getElements().size());
        return response.build();
    }


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
