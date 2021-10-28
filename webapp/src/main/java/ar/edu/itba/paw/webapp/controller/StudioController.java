package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.StudioService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Studio;
import ar.edu.itba.paw.webapp.exceptions.StudioNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Controller
public class StudioController {
    @Autowired
    MediaService mediaService;
    @Autowired
    StudioService studioService;

    private static final int itemsPerPage = 12;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudioController.class);

    @RequestMapping("/studio/{studioId}")
    public ModelAndView studio(@PathVariable(value = "studioId") final int studioId,
                               @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.debug("Trying to access studio {}", studioId);
        final ModelAndView mav = new ModelAndView("principal/secondary/studio");
        final Studio studio = studioService.getById(studioId).orElseThrow(StudioNotFoundException::new);
        final PageContainer<Media> mediaPageContainer = studioService.getMediaByStudio(studio, page - 1, itemsPerPage);
        mav.addObject("studio", studio);
        mav.addObject("mediaPageContainer", mediaPageContainer);
        final Map<String, Integer> map = new HashMap<>();
        map.put("studioId", studioId);
        String urlBase = UriComponentsBuilder.newInstance().path("/studio/{studioId}").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        LOGGER.info("Studio {} accessed", studioId);
        return mav;
    }
}
