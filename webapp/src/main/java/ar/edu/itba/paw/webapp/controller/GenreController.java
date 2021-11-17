package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.GenreService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.webapp.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.webapp.utilities.NormalizerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

import static ar.edu.itba.paw.webapp.utilities.ListCoverImpl.getListCover;

@Controller
public class GenreController {

    @Autowired
    private GenreService genreService;
    @Autowired
    private ListsService listsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreController.class);

    private static final int itemsPerPage = 12;
    private static final int listInPage = 4;
    private static final int minimumMediaMatches = 2; //minimum amount of media on a list that must match for it to be showed
    private static final int firstPage = 0;

    @RequestMapping("/genre/{genre}")
    public ModelAndView genre(@PathVariable(value = "genre") final String genre,
                              @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.info("Genre {} accessed", genre);
        final ModelAndView mav = new ModelAndView("principal/secondary/genre");
        final Genre normalizedGenre = NormalizerUtils.getNormalizedGenres(Collections.singletonList(genre)).stream().findFirst().orElseThrow(GenreNotFoundException::new);
        final PageContainer<Media> mediaPageContainer = genreService.getMediaByGenre(normalizedGenre, page - 1, itemsPerPage);
        final PageContainer<MediaList> genreLists = genreService.getListsContainingGenre(normalizedGenre, firstPage, listInPage, minimumMediaMatches, true);
        final List<ListCover> listCovers = getListCover(genreLists.getElements(), listsService);

        mav.addObject("genre",normalizedGenre);
        mav.addObject("mediaPageContainer", mediaPageContainer);
        mav.addObject("genreLists", listCovers);
        return mav;
    }
}
