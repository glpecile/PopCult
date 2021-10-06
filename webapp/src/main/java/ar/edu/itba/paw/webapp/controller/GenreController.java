package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.GenreService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
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
import java.util.List;
import java.util.Map;

import static ar.edu.itba.paw.webapp.utilities.ListCoverImpl.getListCover;


@Controller
public class GenreController {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MediaService mediaService;
    @Autowired
    GenreService genreService;
    @Autowired
    private ListsService listsService;

    private static final int itemsPerPage = 12;
    private static final int listInPage = 4;
    private static final int minimumMediaMatches = 2; //minimum amount of media on a list that must match for it to be showed

    @RequestMapping("/genre/{genre}")
    public ModelAndView genre(@PathVariable(value = "genre") final String genre,
                              @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.info("Genre {} accessed", genre);
        final ModelAndView mav = new ModelAndView("genre");
        final String normalizedGenre = genre.replaceAll("\\s+", "").toUpperCase();
        final int genreOrdinal = Genre.valueOf(normalizedGenre).ordinal();
        final String genreName = Genre.valueOf(normalizedGenre).getGenre();
        final PageContainer<Media> mediaPageContainer = genreService.getMediaByGenre(genreOrdinal, page - 1, itemsPerPage);
        final List<MediaList> genreLists = listsService.getListsContainingGenre(genreOrdinal, listInPage, minimumMediaMatches);
        final List<ListCover> listCovers = getListCover(genreLists, listsService);
        mav.addObject("genreName", Genre.valueOf(normalizedGenre).getGenre());
        mav.addObject("mediaPageContainer", mediaPageContainer);
        mav.addObject("genreLists", listCovers);
        final Map<String, String> map = new HashMap<>();
        map.put("genreName", genreName);
        String urlBase = UriComponentsBuilder.newInstance().path("/genre/{genreName}").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }
}
