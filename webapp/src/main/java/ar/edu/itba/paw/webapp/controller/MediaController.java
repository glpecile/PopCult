package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.MediaAlreadyInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.MediaComment;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.exceptions.CommentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.MediaNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.CommentForm;
import ar.edu.itba.paw.webapp.form.FilterForm;
import ar.edu.itba.paw.webapp.utilities.FilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.itba.paw.webapp.utilities.ListCoverImpl.getListCover;


@Controller
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

    private static final int itemsPerPage = 12;
    private static final int itemsPerContainer = 6;
    private static final int listsPerPage = 4;
    private static final int lastAddedAmount = 6;
    private static final int defaultValue = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaController.class);

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
        mav.addObject("mediaListContainer", mediaList);//TODO esto me parece que no se usa
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
        } catch (MediaAlreadyInListException e) {
            LOGGER.error("Media {} is already in list {}.", mediaId, mediaListId);
            return new ModelAndView("redirect:/media/" + mediaId).addObject("alreadyInList", true);//TODO mostrar el mensaje
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
        watchService.addWatchedMedia(media, user);
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
    public ModelAndView films(HttpServletRequest request,@RequestParam(value = "page", defaultValue = "1") final int page,
                              @Valid @ModelAttribute("filterForm") final FilterForm filterForm,
                              final BindingResult errors){
        LOGGER.debug("Trying to access films");
        if(errors.hasErrors()){
            LOGGER.info("Redirecting to: {}", request.getHeader("referer"));
            return new ModelAndView("redirect: " + request.getHeader("referer"));
        }
        final ModelAndView mav = new ModelAndView("principal/primary/films");
        final List<Genre> genres = filterForm.getGenres().stream().map(g -> g.replaceAll("\\s+", "")).map(Genre::valueOf).collect(Collectors.toList());
        final List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.FILMS);
        final PageContainer<Media> mostLikedFilms = favoriteService.getMostLikedMedia(MediaType.FILMS, 0, itemsPerContainer);
        final PageContainer<Media> mediaListContainer = mediaService.getMediaByFilters(mediaTypes,page-1,itemsPerPage, SortType.valueOf(filterForm.getSortType().toUpperCase()),genres,filterForm.getStartYear(), filterForm.getLastYear());
        mav.addObject("mostLikedFilms", mostLikedFilms.getElements());
        mav.addObject("mediaListContainer", mediaListContainer);
        mav.addObject("sortTypes", FilterUtils.getSortTypes(messageSource));
        mav.addObject("genreTypes", FilterUtils.getGenres(messageSource));
        mav.addObject("decadesType", FilterUtils.getDecades(messageSource));
        LOGGER.info("Access to films successfully");
        return mav;
    }



    @RequestMapping("/media/series")
    public ModelAndView series(HttpServletRequest request,@RequestParam(value = "page", defaultValue = "1") final int page,
                               @Valid @ModelAttribute("filterForm") final FilterForm filterForm,
                               final BindingResult errors) throws ParseException{
        LOGGER.debug("Trying to access series");
        if(errors.hasErrors()){
            LOGGER.info("Redirecting to: {}", request.getHeader("referer"));
            return new ModelAndView("redirect: " + request.getHeader("referer"));
        }
        final ModelAndView mav = new ModelAndView("principal/primary/series");
        final PageContainer<Media> mostLikedSeries = favoriteService.getMostLikedMedia(MediaType.SERIE, 0, itemsPerContainer);
        final List<Genre> genres = filterForm.getGenres().stream().map(g -> g.replaceAll("\\s+", "")).map(Genre::valueOf).collect(Collectors.toList());
        final List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.SERIE);
        final PageContainer<Media> mediaListContainer = mediaService.getMediaByFilters(mediaTypes,page-1,itemsPerPage, SortType.valueOf(filterForm.getSortType().toUpperCase()),genres,filterForm.getStartYear(), filterForm.getLastYear());
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
