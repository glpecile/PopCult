package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.GenreService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GenreController {

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
        final ModelAndView mav = new ModelAndView("genreView");
        final String normalizedGenre = genre.replaceAll("\\s+", "").toUpperCase();
        final int genreOrdinal = Genre.valueOf(normalizedGenre).ordinal() + 1;
        final List<Integer> mediaIdList = genreService.getMediaByGenre(genreOrdinal, page - 1, itemsPerPage);
        final List<Media> mediaList = mediaService.getById(mediaIdList);
        final Integer mediaCount = genreService.getMediaCountByGenre(genreOrdinal).orElse(0);
        final List<MediaList> genreLists = listsService.getListsContainingGenre(genreOrdinal, listInPage, minimumMediaMatches);
        final List<ListCover> listCovers = new ArrayList<>();
        ListsController.getListCover(genreLists, listCovers, listsService, mediaService);
        mav.addObject("genreName", Genre.valueOf(normalizedGenre).getGenre());
        mav.addObject("mediaList", mediaList);
        mav.addObject("mediaCount", mediaCount);
        mav.addObject("mediaPages", (int)Math.ceil((double)mediaCount / itemsPerPage));
        mav.addObject("currentPage", page);
        mav.addObject("genreLists", listCovers);
        return mav;
    }
}
