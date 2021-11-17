package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.webapp.form.FilterForm;
import ar.edu.itba.paw.webapp.form.SearchForm;
import ar.edu.itba.paw.webapp.utilities.FilterUtils;
import ar.edu.itba.paw.webapp.utilities.ListCoverImpl;
import ar.edu.itba.paw.webapp.utilities.NormalizerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    @Autowired
    private ListsService listsService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    private static final int itemsPerPage = 12;
    private static final int listsPerPage = 12;
    private static final int minimumMediaMatches = 2; //minimum amount of media on a list that must match for it to be showed

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView search(HttpServletRequest request,
                               @Valid @ModelAttribute("searchForm") final FilterForm searchForm,
                               final BindingResult errors,
                               @RequestParam(value = "page", defaultValue = "1") final int page
    ){
        LOGGER.info("Searching for term: {}", searchForm.getTerm());
        if (errors.hasErrors()) {
            LOGGER.info("Redirecting to: {}", request.getHeader("referer"));
            return new ModelAndView("redirect: " + request.getHeader("referer"));
        }
        final ModelAndView mav = new ModelAndView("principal/primary/search");
        final List<Genre> genres = NormalizerUtils.getNormalizedGenres(searchForm.getGenres());
        final List<MediaType> mediaTypes = NormalizerUtils.getNormalizedMediaType(searchForm.getMediaTypes());
        final SortType sortType = NormalizerUtils.getNormalizedSortType(searchForm.getSortType());
        final PageContainer<Media> searchMediaResults = mediaService.getMediaByFilters(mediaTypes, page - 1, itemsPerPage, SortType.valueOf(searchForm.getSortType().toUpperCase()), genres, searchForm.getStartYear(), searchForm.getLastYear(), searchForm.getTerm());
        final PageContainer<MediaList> searchMediaListResults = listsService.getMediaListByFilters(page - 1, listsPerPage, SortType.valueOf(searchForm.getSortType().toUpperCase()), genres, minimumMediaMatches, searchForm.getStartYear(), searchForm.getLastYear(), searchForm.getTerm());
        final List<ListCover> listCovers = ListCoverImpl.getListCover(searchMediaListResults.getElements(), listsService);
        final PageContainer<ListCover> listCoversContainer = new PageContainer<>(listCovers, searchMediaListResults.getCurrentPage(), searchMediaListResults.getPageSize(), searchMediaListResults.getTotalCount());

        mav.addObject("mediaTypes", FilterUtils.getMediaTypes(messageSource));
        mav.addObject("searchFilmsContainer", searchMediaResults);
        mav.addObject("listCoversContainer", listCoversContainer);
        mav.addObject("sortTypes", FilterUtils.getSortTypes(messageSource));
        mav.addObject("genreTypes", FilterUtils.getGenres(messageSource));
        mav.addObject("decades", FilterUtils.getDecades(messageSource));
        return mav;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET}, params = "clear")
    public ModelAndView clearFilters(@ModelAttribute("searchForm") final SearchForm searchForm) {
        return new ModelAndView("redirect:/search?term=" + searchForm.getTerm());
    }
}
