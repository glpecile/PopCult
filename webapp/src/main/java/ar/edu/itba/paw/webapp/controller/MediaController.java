package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.Director;
import ar.edu.itba.paw.models.staff.Studio;
import ar.edu.itba.paw.webapp.exceptions.MediaNotFoundException;
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

    private static final int itemsPerPage = 12;
    private static final int itemsPerContainer = 6;
    private static final int listsPerPage = 4;

    @RequestMapping("/")
    public ModelAndView home(@RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("home");
        final List<Media> filmsLatest = mediaService.getLatestMediaList(MediaType.MOVIE.ordinal(), 0, itemsPerContainer);
        final List<Media> seriesLatest = mediaService.getLatestMediaList(MediaType.SERIE.ordinal(), 0, itemsPerContainer);
        final List<Media> mediaList = mediaService.getMediaList(page - 1, itemsPerPage);
        final Integer mediaCount = mediaService.getMediaCount().orElse(0);
        mav.addObject("filmsList", filmsLatest);
        mav.addObject("seriesList", seriesLatest);
        mav.addObject("mediaList", mediaList);
        mav.addObject("mediaPages", (int) Math.ceil((double) mediaCount / itemsPerPage));
        mav.addObject("currentPage", page);
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
        final List<MediaList> mediaList = listsService.getListsIncludingMediaId(mediaId, page - 1, listsPerPage);
        final List<ListCover> relatedListsCover = getListCover(mediaList, listsService, mediaService);
        final int popularListsAmount = listsService.getListCountFromMedia(mediaId).orElse(0);
        final List<MediaList> userLists = listsService.getMediaListByUserId(1);
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
        mav.addObject("popularListPages", (int) Math.ceil((double) popularListsAmount / itemsPerPage));
        mav.addObject("currentPage", page);
        mav.addObject("userLists", userLists);
        return mav;
    }

    @RequestMapping(value = "/media/{mediaId}", method = {RequestMethod.POST})
    public ModelAndView addMediaToList(@PathVariable("mediaId") final int mediaId, @RequestParam("mediaListId") final int mediaListId) {
        listsService.addToMediaList(mediaListId, mediaId);
        return new ModelAndView("redirect:/media/" + mediaId);
    }


    @RequestMapping("/media/films")
    public ModelAndView films(@RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("films");
        final List<Media> latestFilms = mediaService.getLatestMediaList(MediaType.MOVIE.ordinal(), 0, itemsPerContainer);
        final List<Media> mediaList = mediaService.getMediaList(MediaType.MOVIE.ordinal(), page - 1, itemsPerPage);
        final Integer mediaCount = mediaService.getMediaCountByMediaType(MediaType.MOVIE.ordinal()).orElse(0);
        mav.addObject("latestFilms", latestFilms);
        mav.addObject("mediaList", mediaList);
        mav.addObject("mediaPages", (int) Math.ceil((double) mediaCount / itemsPerPage));
        mav.addObject("currentPage", page);
        return mav;
    }

    @RequestMapping("/media/series")
    public ModelAndView series(@RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("series");
        final List<Media> latestSeries = mediaService.getLatestMediaList(MediaType.SERIE.ordinal(), 0, itemsPerContainer);
        final List<Media> mediaList = mediaService.getMediaList(MediaType.SERIE.ordinal(), page - 1, itemsPerPage);
        final Integer mediaCount = mediaService.getMediaCountByMediaType(MediaType.SERIE.ordinal()).orElse(0);
        mav.addObject("latestSeries", latestSeries);
        mav.addObject("mediaList", mediaList);
        mav.addObject("mediaPages", (int) Math.ceil((double) mediaCount / itemsPerPage));
        mav.addObject("currentPage", page);
        return mav;
    }
}
