package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.GenreService;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class GenreController {

    @Autowired
    MediaService mediaService;
    @Autowired
    GenreService genreService;

    private static final int itemsPerPage = 12;

    @RequestMapping("/genre/{genre}")
    public ModelAndView genre(@PathVariable(value = "genre") final String genre,
                              @RequestParam(value = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("genreView");
        final String normalizedGenre = genre.replaceAll("\\s+","").toUpperCase();
        final int genreOrdinal = Genre.valueOf(normalizedGenre).ordinal() + 1;
        final List<Integer> mediaIdList = genreService.getMediaByGenre(genreOrdinal, page - 1, itemsPerPage);
        final List<Media> mediaList = mediaService.getById(mediaIdList);
        final Integer mediaCount = genreService.getMediaCountByGenre(genreOrdinal).orElse(0);
        mav.addObject("genreName", Genre.valueOf(normalizedGenre).getGenre());
        mav.addObject("mediaList", mediaList);
        mav.addObject("mediaCount", mediaCount);
        mav.addObject("mediaPages", mediaCount / itemsPerPage + 1);
        mav.addObject("currentPage", page);
        return mav;
    }
}
