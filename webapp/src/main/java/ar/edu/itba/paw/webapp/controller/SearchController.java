package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    private static final int itemsPerPage = 12;

    private static final int listsPerPage = 4;

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView search(HttpServletRequest request,
                               @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                               final BindingResult errors,
                               @RequestParam(value = "sort", defaultValue = "title") final String sortType,
                               @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.info("Search term: {}", searchForm.getTerm());
        if(errors.hasErrors()) {
            LOGGER.info("Redirecting to: {}", request.getHeader("referer"));
            return new ModelAndView("redirect: " + request.getHeader("referer"));
        }
        final ModelAndView mav = new ModelAndView("search");
        final PageContainer<Media> searchResults = searchService.searchMediaByTitle(searchForm.getTerm(), page - 1, itemsPerPage, SortType.valueOf(sortType.toUpperCase()).ordinal());
        mav.addObject("searchResultsContainer", searchResults);

        final Map<String, String> queries = new HashMap<>();
        queries.put("term", searchForm.getTerm());
        String urlBase = UriComponentsBuilder.newInstance().path("/search").query("term={term}").buildAndExpand(queries).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }
}
