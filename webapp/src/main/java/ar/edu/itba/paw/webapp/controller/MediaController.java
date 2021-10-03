package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.MediaAlreadyInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.Director;
import ar.edu.itba.paw.models.staff.Studio;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.exceptions.MediaNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;
import ar.edu.itba.paw.webapp.form.CommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.edu.itba.paw.webapp.utilities.ListCoverImpl.getListCover;


@Controller
public class MediaController {

    @Autowired
    private MediaService mediaService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private StudioService studioService;
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

    private static final int itemsPerPage = 12;
    private static final int itemsPerContainer = 6;
    private static final int listsPerPage = 4;
    private static final int lastAddedAmount = 6;
    private static final int defaultValue = 1;

    @RequestMapping("/")
    public ModelAndView home(@RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("home");
        final PageContainer<Media> latestFilmsContainer = mediaService.getLatestMediaList(MediaType.MOVIE.ordinal(), 0, itemsPerContainer);
        final PageContainer<Media> latestSeriesContainer = mediaService.getLatestMediaList(MediaType.SERIE.ordinal(), 0, itemsPerContainer);
        final PageContainer<Media> mediaListContainer = mediaService.getMediaList(page - 1, itemsPerPage);
        final List<ListCover> recentlyAddedCovers = getListCover(listsService.getNLastAddedList(lastAddedAmount), listsService);
        mav.addObject("latestFilmsList", latestFilmsContainer.getElements());
        mav.addObject("latestSeriesList", latestSeriesContainer.getElements());
        mav.addObject("mediaListContainer", mediaListContainer);
        mav.addObject("recentlyAddedLists", recentlyAddedCovers);
        final Map<String, String> map = new HashMap<>();
        String urlBase = UriComponentsBuilder.newInstance().path("/").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.GET})
    public ModelAndView mediaDescription(@PathVariable("mediaId") final int mediaId, @RequestParam(value = "page", defaultValue = "1") final int page, @ModelAttribute("commentForm") CommentForm commentForm) {
        final ModelAndView mav = new ModelAndView("mediaDescription");
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final List<String> genreList = genreService.getGenreByMediaId(mediaId);
        final List<Studio> studioList = studioService.getStudioByMediaId(mediaId);
        final List<Director> directorList = staffService.getDirectorsByMedia(mediaId);
        final List<Actor> actorList = staffService.getActorsByMedia(mediaId);
        final PageContainer<MediaList> mediaList = listsService.getListsIncludingMediaId(mediaId, page - 1, listsPerPage);
        final List<ListCover> relatedListsCover = getListCover(mediaList.getElements(), listsService);
        final PageContainer<Comment> mediaCommentsContainer = commentService.getMediaComments(mediaId, defaultValue - 1, itemsPerPage);
        final Map<String, String> map = new HashMap<>();
        map.put("mediaId", Integer.toString(mediaId));
        mav.addObject("media", media);
        mav.addObject("genreList", genreList);
        mav.addObject("studioList", studioList);
        mav.addObject("directorList", directorList);
        mav.addObject("actorList", actorList);
        mav.addObject("relatedLists", relatedListsCover);
        mav.addObject("mediaListContainer", mediaList);
        String urlBase = UriComponentsBuilder.newInstance().path("/media/{mediaId}").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        mav.addObject("mediaCommentsContainer", mediaCommentsContainer);
        userService.getCurrentUser().ifPresent(user -> {
            mav.addObject("currentUser", user);
            mav.addObject("isFavoriteMedia", favoriteService.isFavorite(mediaId, user.getUserId()));
            mav.addObject("isWatchedMedia", watchService.isWatched(mediaId, user.getUserId()));
            mav.addObject("isToWatchMedia", watchService.isToWatch(mediaId, user.getUserId()));
            final List<MediaList> userLists = listsService.getMediaListByUserId(user.getUserId());
            mav.addObject("userLists", userLists);
        });

        return mav;
    }

    @RequestMapping(value = "/media/{mediaId}/comment", method = {RequestMethod.POST})
    public ModelAndView addComment(@PathVariable("mediaId") final int mediaId, @RequestParam("userId") int userId, @Valid @ModelAttribute("searchForm") final CommentForm form, final BindingResult errors) {
        if (errors.hasErrors())
            return mediaDescription(defaultValue, mediaId, form);
        commentService.addCommentToMedia(userId, mediaId, form.getBody());
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}/deleteComment/{commentId}", method = {RequestMethod.DELETE, RequestMethod.POST})
    public ModelAndView deleteComment(@PathVariable("mediaId") final int mediaId, @PathVariable("commentId") int commentId) {
        commentService.deleteCommentFromMedia(commentId);
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST})
    public ModelAndView addMediaToList(@PathVariable("mediaId") final int mediaId, @RequestParam("mediaListId") final int mediaListId) {
        try {
            listsService.addToMediaList(mediaListId, mediaId);
        } catch (MediaAlreadyInListException e) {
            return new ModelAndView("redirect:/media/" + mediaId).addObject("alreadyInList", true);//TODO mostrar el mensaje
        }
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "addFav")
    public ModelAndView addMediaToFav(@PathVariable("mediaId") final int mediaId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        favoriteService.addMediaToFav(mediaId, user.getUserId());
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "deleteFav")
    public ModelAndView deleteMediaFromFav(@PathVariable("mediaId") final int mediaId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        favoriteService.deleteMediaFromFav(mediaId, user.getUserId());
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "addWatched")
    public ModelAndView addMediaToWatched(@PathVariable("mediaId") final int mediaId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        watchService.addWatchedMedia(mediaId, user.getUserId());
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "deleteWatched")
    public ModelAndView deleteMediaFromWatched(@PathVariable("mediaId") final int mediaId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        watchService.deleteWatchedMedia(mediaId, user.getUserId());
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "addWatchlist")
    public ModelAndView addMediaToWatchlist(@PathVariable("mediaId") final int mediaId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        watchService.addMediaToWatch(mediaId, user.getUserId());
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST}, params = "deleteWatchlist")
    public ModelAndView deleteMediaFromWatchlist(@PathVariable("mediaId") final int mediaId) {
        User user = userService.getCurrentUser().orElseThrow(NoUserLoggedException::new);
        watchService.deleteToWatchMedia(mediaId, user.getUserId());
        return new ModelAndView("redirect:/media/" + mediaId);
    }

    @RequestMapping("/media/films")
    public ModelAndView films(@RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("films");
        final PageContainer<Media> mostLikedFilms = favoriteService.getMostLikedMedia(MediaType.MOVIE.ordinal(), 0, itemsPerContainer);
        final PageContainer<Media> mediaListContainer = mediaService.getMediaList(MediaType.MOVIE.ordinal(), page - 1, itemsPerPage);
        mav.addObject("mostLikedFilms", mostLikedFilms.getElements());
        mav.addObject("mediaListContainer", mediaListContainer);
        final Map<String, String> map = new HashMap<>();
        String urlBase = UriComponentsBuilder.newInstance().path("/media/films").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }

    @RequestMapping("/media/series")
    public ModelAndView series(@RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("series");
        final Integer mediaCount = mediaService.getMediaCountByMediaType(MediaType.SERIE.ordinal()).orElse(0);
        final PageContainer<Media> mostLikedSeries = favoriteService.getMostLikedMedia(MediaType.SERIE.ordinal(), 0, itemsPerContainer);
        final PageContainer<Media> mediaListContainer = mediaService.getMediaList(MediaType.SERIE.ordinal(), page - 1, itemsPerPage);
        mav.addObject("mostLikedSeries", mostLikedSeries.getElements());
        mav.addObject("mediaListContainer", mediaListContainer);
        final Map<String, String> map = new HashMap<>();
        String urlBase = UriComponentsBuilder.newInstance().path("/media/series").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }
}
