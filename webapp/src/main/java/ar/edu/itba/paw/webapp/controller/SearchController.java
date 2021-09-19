package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.search.SortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;



@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    private static final int itemsPerPage = 12;

    private static final int listsPerPage = 4;

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam(value="content", defaultValue = "") final String content, @RequestParam(value="sort", defaultValue = "title") final String sortType,@RequestParam(value = "page", defaultValue = "1") final int page){
        final ModelAndView mav = new ModelAndView("search");
        final PageContainer<Media> searchResults = searchService.searchMediaByTitle(content,page-1,itemsPerPage, SortType.valueOf(sortType.toUpperCase()).ordinal());
        final Map<String,String> queries = new HashMap<>();
        queries.put("content",content);
        queries.put("sort", sortType);
        //queries.put("page", Integer.toString(page));
        mav.addObject("searchResultsContainer", searchResults);
        String urlBase = UriComponentsBuilder.newInstance().path("/search").query("content={content}&sort={sort}").buildAndExpand(queries).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }
    private String createURL(Map<String, String> queries){
        StringBuilder toReturn = new StringBuilder("/search");
        toReturn.append("?");
        if(queries.isEmpty())
            return toReturn.toString();
        for (String key:
                queries.keySet()) {
            String value = queries.get(key);
            toReturn.append(key).append("=").append(value).append("&");
        }
        return toReturn.toString();
    }


}
