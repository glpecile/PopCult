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


import java.util.List;

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
        //final List<Integer> mediaIdList = studioService.getMediaByStudio(studioId, page - 1, itemsPerPage);
        final PageContainer<Integer> mediaIdList = studioService.getMediaByStudio(studioId, page - 1, itemsPerPage);
        final List<Media> mediaList = mediaService.getById(mediaIdList.getElements());
        //final Integer mediaCount = studioService.getMediaCountByStudio(studioId).orElse(0);
        mav.addObject("studio", studio);
        mav.addObject("mediaList", mediaList);
        //mav.addObject("mediaCount", mediaCount);
        mav.addObject("mediaCount", mediaIdList.getTotalCount());
        //mav.addObject("mediaPages", (int)Math.ceil((double)mediaCount / itemsPerPage));
        mav.addObject("mediaPages", mediaIdList.getTotalPages());
        //mav.addObject("currentPage", page);
        mav.addObject("currentPage", mediaIdList.getCurrentPage()+1);

        return mav;
    }
}
