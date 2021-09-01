package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.models.Media;
import ar.edu.itba.paw.webapp.exceptions.MediaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @RequestMapping("/")
    public ModelAndView home() {
        final ModelAndView mav = new ModelAndView("home");
        final List<Media> mediaList = mediaService.getMediaList();
        mav.addObject("mediaList", mediaList);
        return mav;
    }

    @RequestMapping("/media/{mediaId}")
    public ModelAndView mediaDescription(@PathVariable("mediaId") final int mediaId) {
        final ModelAndView mav = new ModelAndView("mediaDescription");
        final Media media = mediaService.getById(mediaId).orElseThrow(MediaNotFoundException::new);
        mav.addObject("media", media);
        return mav;
    }
}
