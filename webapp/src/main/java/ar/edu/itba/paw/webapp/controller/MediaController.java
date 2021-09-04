package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.StaffService;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.Director;
import ar.edu.itba.paw.webapp.exceptions.MediaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MediaController {

    @Autowired
    private MediaService mediaService;
    @Autowired
    private StaffService staffService;

    private static final int itemsPerPage = 12;

    @RequestMapping("/")
    public ModelAndView home() {
        final ModelAndView mav = new ModelAndView("home");
        final List<Media> filmsLatest = mediaService.getLatestMediaList(MediaType.MOVIE.ordinal(), 0,itemsPerPage);
        final List<Media> seriesLatest = mediaService.getLatestMediaList(MediaType.SERIE.ordinal(), 0,itemsPerPage);
        final List<Media> mediaList = mediaService.getMediaList(0,itemsPerPage);
        mav.addObject("mediaList", mediaList);
        mav.addObject("filmsList", filmsLatest);
        mav.addObject("seriesList", seriesLatest);
        return mav;
    }

    @RequestMapping("/media/{mediaId}")
    public ModelAndView mediaDescription(@PathVariable("mediaId") final int mediaId) {
        final ModelAndView mav = new ModelAndView("mediaDescription");
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        final List<Director> directorList = staffService.getDirectorsByMedia(mediaId);
        final List<Actor> actorList = staffService.getActorsByMedia(mediaId);
        mav.addObject("media", media);
        mav.addObject("directorList", directorList);
        mav.addObject("actorList", actorList);
        return mav;
    }

    @RequestMapping("/media/films")
    public ModelAndView films(@RequestParam(value = "page", defaultValue = "0") final int page) {
        final ModelAndView mav = new ModelAndView("home");
        final List<Media> mediaList = mediaService.getMediaList(MediaType.MOVIE.ordinal(), page, itemsPerPage);
        mav.addObject("mediaList", mediaList);
        return mav;
    }

    @RequestMapping("/media/series")
    public ModelAndView series(@RequestParam(value = "page", defaultValue = "0") final int page) {
        final ModelAndView mav = new ModelAndView("home");
        final List<Media> mediaList = mediaService.getMediaList(MediaType.SERIE.ordinal(), page, itemsPerPage);
        mav.addObject("mediaList", mediaList);
        return mav;
    }
}
