package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    private static final int itemsPerPage = 12;

    private static final int listsPerPage = 4;

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam(value="content") final String content,@RequestParam(value = "page", defaultValue = "1") final int page){
        final ModelAndView mav = new ModelAndView("search");
        final List<Media> searchResults = searchService.searchMediaByTitle(content,0,listsPerPage);
        final Integer searchResultsCount = searchService.getCountSearchMediaByTitle(content).orElse(0);
        mav.addObject("searchResults", searchResults);
        mav.addObject("currentPage", page);
        mav.addObject("searchPages", (int) Math.ceil((double) searchResultsCount / itemsPerPage));
        return mav;
    }


}
