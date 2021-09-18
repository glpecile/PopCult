package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.PageContainer;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

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

    private static final int itemsPerPage = 12;
    private static final int itemsPerContainer = 6;
    private static final int listsPerPage = 4;

    @RequestMapping("/")
    public ModelAndView home(@RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("home");
        final PageContainer<Media> filmsLatest = mediaService.getLatestMediaList(MediaType.MOVIE.ordinal(),0,itemsPerContainer);
        final PageContainer<Media> seriesLatest = mediaService.getLatestMediaList(MediaType.SERIE.ordinal(), 0, itemsPerContainer);
        final PageContainer<Media> mediaList = mediaService.getMediaList(page - 1, itemsPerPage);
        final Integer mediaCount = mediaService.getMediaCount().orElse(0);
        mav.addObject("currentPage", mediaList.getCurrentPage()+1);
        mav.addObject("filmsList", filmsLatest.getElements());
        mav.addObject("seriesList", seriesLatest.getElements());
        mav.addObject("mediaListContainer", mediaList);
        return mav;
    }

    @RequestMapping("/media/{mediaId}")
    public ModelAndView mediaDescription(@PathVariable("mediaId") final int mediaId, @RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("mediaDescription");
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final List<String> genreList = genreService.getGenreByMediaId(mediaId);
        final List<Studio> studioList = studioService.getStudioByMediaId(mediaId);
        final List<Director> directorList = staffService.getDirectorsByMedia(mediaId);
        final List<Actor> actorList = staffService.getActorsByMedia(mediaId);
        final PageContainer<MediaList> mediaList = listsService.getListsIncludingMediaId(mediaId, page - 1, listsPerPage);
        final List<ListCover> relatedListsCover = getListCover(mediaList.getElements(), listsService);
        mav.addObject("media", media);
        mav.addObject("genreList", genreList);
        mav.addObject("studioList", studioList);
        mav.addObject("directorList", directorList);
        mav.addObject("actorList", actorList);
        mav.addObject("relatedListsAmount", relatedListsCover.size());
        mav.addObject("actorsAmount", actorList.size());
        mav.addObject("directorsAmount", directorList.size());
        mav.addObject("studiosAmount", studioList.size());
        mav.addObject("genresAmount", genreList.size());
        mav.addObject("relatedLists", relatedListsCover);
        mav.addObject("mediaListContainer", mediaList);
        userService.getCurrentUser().ifPresent(user -> {
            mav.addObject("isFavoriteMedia", favoriteService.isFavorite(mediaId, user.getUserId()));
            mav.addObject("isWatchedMedia", watchService.isWatched(mediaId, user.getUserId()));
            mav.addObject("isToWatchMedia", watchService.isToWatch(mediaId, user.getUserId()));
            final List<MediaList> userLists = listsService.getMediaListByUserId(user.getUserId());
            mav.addObject("userLists", userLists);
        });

        return mav;
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST})
    public ModelAndView addMediaToList(@PathVariable("mediaId") final int mediaId, @RequestParam("mediaListId") final int mediaListId) {
        listsService.addToMediaList(mediaListId, mediaId);
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
        final PageContainer<Media> latestFilms = mediaService.getLatestMediaList(MediaType.MOVIE.ordinal(), 0, itemsPerContainer);
        final PageContainer<Media> mediaList = mediaService.getMediaList(MediaType.MOVIE.ordinal(), page - 1, itemsPerPage);
        mav.addObject("latestFilmsContainer", latestFilms);
        mav.addObject("mediaListContainer",mediaList);
        return mav;
    }

    @RequestMapping("/media/series")
    public ModelAndView series(@RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("series");
        final Integer mediaCount = mediaService.getMediaCountByMediaType(MediaType.SERIE.ordinal()).orElse(0);
        final PageContainer<Media> latestSeries = mediaService.getLatestMediaList(MediaType.SERIE.ordinal(), 0, itemsPerContainer);
        final PageContainer<Media> mediaList = mediaService.getMediaList(MediaType.SERIE.ordinal(), page - 1, itemsPerPage);
        mav.addObject("mediaListContainer", mediaList);
        mav.addObject("latestSeriesContainer", latestSeries);
        return mav;
    }
}
