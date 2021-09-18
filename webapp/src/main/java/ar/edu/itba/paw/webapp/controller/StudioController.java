package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.StudioService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Studio;
import ar.edu.itba.paw.webapp.exceptions.StudioNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StudioController {
    @Autowired
    MediaService mediaService;
    @Autowired
    StudioService studioService;

    private static final int itemsPerPage = 12;

    @RequestMapping("/studio/{studioId}")
    public ModelAndView studio(@PathVariable(value = "studioId") final int studioId,
                               @RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("studioView");
        final Studio studio = studioService.getById(studioId).orElseThrow(StudioNotFoundException::new);
        final PageContainer<Integer> mediaIdList = studioService.getMediaByStudioIds(studioId, page - 1, itemsPerPage);
        final List<Media> mediaList = mediaService.getById(mediaIdList.getElements());
        final Map<String,Integer> map = new HashMap<>();
        map.put("studioId", studioId);
        mav.addObject("studio", studio);
        mav.addObject("mediaList", mediaList);
        mav.addObject("mediaIdListContainer", mediaIdList);
        String urlBase = UriComponentsBuilder.newInstance().path("/studio/{studioId}").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }
}
