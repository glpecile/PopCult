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
import java.util.stream.Collectors;


@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @Autowired
    private ListsService listsService;

    private static final int itemsPerPage = 12;

    private static final int listsPerPage = 12;

    private static final int minimumMediaMatches = 2; //minimum amount of media on a list that must match for it to be showed

    private static final int firstPage = 0;

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView search(HttpServletRequest request,
                               @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                               final BindingResult errors,
                               @RequestParam(value = "page", defaultValue = "1") final int page
                               ) {
        LOGGER.info("Search term: {}", searchForm.getTerm());
        if(errors.hasErrors()) {
            LOGGER.info("Redirecting to: {}", request.getHeader("referer"));
            return new ModelAndView("redirect: " + request.getHeader("referer"));
        }
        //final String normalizedGenre = searchForm.getGenres().get(0).replaceAll("\\s+", "").toUpperCase();
        //final String normalizedMediaType = searchForm.getMediaTypes().get(0).replaceAll("\\s+", "").toUpperCase();
        //final int genreOrdinal = Genre.valueOf(normalizedGenre).ordinal();
        final ModelAndView mav = new ModelAndView("search");
//        final List<Integer> genres = new ArrayList<>();
//        genres.add(genreOrdinal);
        final List<Integer> genres = searchForm.getGenres().stream().map(Genre::valueOf).map(Genre::ordinal).collect(Collectors.toList());
        final List<Integer> mediaTypes = searchForm.getMediaTypes().stream().map(MediaType::valueOf).map(MediaType::ordinal).collect(Collectors.toList());
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//        try {
            //final Date fromDate = f.parse("1960-10-10");
            final Date fromDate = null;
            //final Date toDate = f.parse("2010-10-10");
            final Date toDate = null;
            final PageContainer<Media> searchMediaResults = searchService.searchMediaByTitle(searchForm.getTerm(),page-1,itemsPerPage, mediaTypes,SortType.valueOf(searchForm.getSortType().toUpperCase()).ordinal(), genres, fromDate, toDate);
            final PageContainer<MediaList> searchMediaListResults = searchService.searchListMediaByName(searchForm.getTerm(),page-1,listsPerPage, SortType.valueOf(searchForm.getSortType().toUpperCase()).ordinal(), 1, minimumMediaMatches);
            final List<ListCover> listCovers = ListCoverImpl.getListCover(searchMediaListResults.getElements(),listsService);
            final PageContainer<ListCover> listCoversContainer = new PageContainer<>(listCovers,searchMediaListResults.getCurrentPage(),searchMediaListResults.getPageSize(),searchMediaListResults.getTotalCount());
            mav.addObject("searchFilmsContainer", searchMediaResults);
            mav.addObject("listCoversContainer", listCoversContainer);
            mav.addObject("sortTypes", Arrays.stream(SortType.values()).map(SortType::getName).map(String::toUpperCase).collect(Collectors.toList()));
            mav.addObject("genreTypes",Arrays.stream(Genre.values()).map(Genre::getGenre).map(String::toUpperCase).collect(Collectors.toList()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


        return mav;
    }

    @RequestMapping(value = "/search/series", method = {RequestMethod.GET})
    public ModelAndView searchSeries(HttpServletRequest request,
                               @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                               final BindingResult errors,
                               @RequestParam(value = "sort", defaultValue = "title") final String sortType,
                               @RequestParam(value = "genre", defaultValue = "all") final String genre,
                               @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.info("Search term: {}", searchForm.getTerm());
        if(errors.hasErrors()) {
            LOGGER.info("Redirecting to: {}", request.getHeader("referer"));
            return new ModelAndView("redirect: " + request.getHeader("referer"));
        }
        final String normalizedGenre = genre.replaceAll("\\s+", "").toUpperCase();
        final int genreOrdinal = Genre.valueOf(normalizedGenre).ordinal();
        final ModelAndView mav = new ModelAndView("searchSeries");
        final List<Integer> genres = new ArrayList<>();
        genres.add(genreOrdinal);
        final List<Integer> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.SERIE.ordinal());
        final PageContainer<Media> searchSeriesResults = searchService.searchMediaByTitle(searchForm.getTerm(),page-1,itemsPerPage, mediaTypes,SortType.valueOf(sortType.toUpperCase()).ordinal(), genres, null, null);
        mav.addObject("searchSeriesContainer", searchSeriesResults);
        mav.addObject("term", searchForm.getTerm());
        mav.addObject("sortTypes", Arrays.stream(SortType.values()).map(SortType::getName).map(String::toLowerCase).collect(Collectors.toList()));
        mav.addObject("genreTypes",Arrays.stream(Genre.values()).map(Genre::getGenre).map(String::toLowerCase).collect(Collectors.toList()));
        final Map<String, String> queries = new HashMap<>();
        queries.put("term", searchForm.getTerm());
        queries.put("sort", sortType);
        queries.put("genre", genre);
        String urlBase = UriComponentsBuilder.newInstance().path("/search/series").query("term={term}&sort={sort}&genre={genre}").buildAndExpand(queries).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }

    @RequestMapping(value = "/search/films", method = {RequestMethod.GET})
    public ModelAndView searchFilms(HttpServletRequest request,
                                    @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                    final BindingResult errors,
                                    @RequestParam(value = "sort", defaultValue = "title") final String sortType,
                                    @RequestParam(value = "genre", defaultValue = "all") final String genre,
                                    @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.info("Search term: {}", searchForm.getTerm());
        if(errors.hasErrors()) {
            LOGGER.info("Redirecting to: {}", request.getHeader("referer"));
            return new ModelAndView("redirect: " + request.getHeader("referer"));
        }
        final String normalizedGenre = genre.replaceAll("\\s+", "").toUpperCase();
        final int genreOrdinal = Genre.valueOf(normalizedGenre).ordinal() + 1;
        final ModelAndView mav = new ModelAndView("searchFilms");
        final List<Integer> genres = new ArrayList<>();
        genres.add(genreOrdinal);
        final List<Integer> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.MOVIE.ordinal());
        final PageContainer<Media> searchSeriesResults = searchService.searchMediaByTitle(searchForm.getTerm(),page-1,itemsPerPage, mediaTypes,SortType.valueOf(sortType.toUpperCase()).ordinal(),genres, null, null);
        mav.addObject("searchFilmsContainer", searchSeriesResults);
        mav.addObject("term", searchForm.getTerm());
        mav.addObject("sortTypes", Arrays.stream(SortType.values()).map(SortType::getName).collect(Collectors.toList()));
        mav.addObject("genreTypes",Arrays.stream(Genre.values()).map(Genre::getGenre).collect(Collectors.toList()));
        final Map<String, String> queries = new HashMap<>();
        queries.put("term", searchForm.getTerm());
        queries.put("sort", sortType);
        queries.put("genre", genre);
        String urlBase = UriComponentsBuilder.newInstance().path("/search/films").query("term={term}&sort={sort}&genre={genre}").buildAndExpand(queries).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }

    @RequestMapping(value = "/search/lists", method = {RequestMethod.GET})
    public ModelAndView searchLists(HttpServletRequest request,
                                    @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                    final BindingResult errors,
                                    @RequestParam(value = "sort", defaultValue = "title") final String sortType,
                                    @RequestParam(value = "genre", defaultValue = "all") final String genre,
                                    @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.info("Search term: {}", searchForm.getTerm());
        if(errors.hasErrors()) {
            LOGGER.info("Redirecting to: {}", request.getHeader("referer"));
            return new ModelAndView("redirect: " + request.getHeader("referer"));
        }
        final String normalizedGenre = genre.replaceAll("\\s+", "").toUpperCase();
        final int genreOrdinal = Genre.valueOf(normalizedGenre).ordinal() + 1;
        final ModelAndView mav = new ModelAndView("searchLists");
        final PageContainer<MediaList> searchMediaListResults = searchService.searchListMediaByName(searchForm.getTerm(),page - 1,listsPerPage, SortType.valueOf(sortType.toUpperCase()).ordinal(), genreOrdinal, minimumMediaMatches);
        final List<ListCover> listCovers = ListCoverImpl.getListCover(searchMediaListResults.getElements(),listsService);
        final PageContainer<ListCover> listCoversContainer = new PageContainer<>(listCovers,searchMediaListResults.getCurrentPage(),searchMediaListResults.getPageSize(),searchMediaListResults.getTotalCount());
        mav.addObject("searchListsContainer", listCoversContainer);
        mav.addObject("term", searchForm.getTerm());
        mav.addObject("sortTypes", Arrays.stream(SortType.values()).map(SortType::getName).collect(Collectors.toList()));
        mav.addObject("genreTypes",Arrays.stream(Genre.values()).map(Genre::getGenre).collect(Collectors.toList()));
        final Map<String, String> queries = new HashMap<>();
        queries.put("term", searchForm.getTerm());
        queries.put("sort", sortType);
        queries.put("genre", genre);
        String urlBase = UriComponentsBuilder.newInstance().path("/search/lists").query("term={term}&sort={sort}&genre={genre}").buildAndExpand(queries).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }
}
