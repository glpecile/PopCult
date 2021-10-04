package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.webapp.form.SearchForm;
import ar.edu.itba.paw.webapp.utilities.ListCoverImpl;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @Autowired
    private ListsService listsService;

    private static final int itemsPerPage = 12;

    private static final int listsPerPage = 12;

    private static final int minimumMediaMatches = 2; //minimum amount of media on a list that must match for it to be showed

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView search(HttpServletRequest request,
                               @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                               final BindingResult errors,
                               @RequestParam(value = "page", defaultValue = "1") final int page
                               ) throws ParseException {
        LOGGER.info("Search term: {}", searchForm.getTerm());
        if(errors.hasErrors()) {
            LOGGER.info("Redirecting to: {}", request.getHeader("referer"));
            return new ModelAndView("redirect: " + request.getHeader("referer"));
        }
        final ModelAndView mav = new ModelAndView("search");
        final List<Integer> genres = searchForm.getGenres().stream().map(g -> g.replaceAll("\\s+", "")).map(Genre::valueOf).map(Genre::ordinal).collect(Collectors.toList());
        final List<Integer> mediaTypes = searchForm.getMediaTypes().stream().map(MediaType::valueOf).map(MediaType::ordinal).collect(Collectors.toList());
        final PageContainer<Media> searchMediaResults = searchService.searchMediaByTitle(searchForm.getTerm(),page-1,itemsPerPage, mediaTypes,SortType.valueOf(searchForm.getSortType().toUpperCase()).ordinal(), genres, searchForm.getDecade(), searchForm.getLastYear());
        final PageContainer<MediaList> searchMediaListResults = searchService.searchListMediaByName(searchForm.getTerm(),page-1,listsPerPage, SortType.valueOf(searchForm.getSortType().toUpperCase()).ordinal(), 1, minimumMediaMatches);
        final List<ListCover> listCovers = ListCoverImpl.getListCover(searchMediaListResults.getElements(),listsService);
        final PageContainer<ListCover> listCoversContainer = new PageContainer<>(listCovers,searchMediaListResults.getCurrentPage(),searchMediaListResults.getPageSize(),searchMediaListResults.getTotalCount());
        final List<String> decades = new ArrayList<>();
        decades.add("ALL");
        for (Integer i : IntStream.range(0, 11).map(x -> (10 * x) + 1920).toArray()) {
            decades.add(Integer.toString(i));
        }
        mav.addObject("mediaTypes", Arrays.stream(MediaType.values()).map(MediaType::getType).map(String::toUpperCase).collect(Collectors.toList()));
        mav.addObject("searchFilmsContainer", searchMediaResults);
        mav.addObject("listCoversContainer", listCoversContainer);
        mav.addObject("sortTypes", Arrays.stream(SortType.values()).map(SortType::getName).map(String::toUpperCase).collect(Collectors.toList()));
        mav.addObject("genreTypes",Arrays.stream(Genre.values()).map(Genre::getGenre).map(String::toUpperCase).collect(Collectors.toList()));
        mav.addObject("decades", decades);
        return mav;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET}, params = "clear")
    public ModelAndView clearFilters(@ModelAttribute("searchForm") final SearchForm searchForm){
        return new ModelAndView("redirect:/search?term=" + searchForm.getTerm());
    }
}
