package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.GenreService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.ListCover;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.webapp.dto.output.GenreDto;
import ar.edu.itba.paw.webapp.dto.output.MediaDto;
import ar.edu.itba.paw.webapp.dto.output.MediaInGenreDto;
import ar.edu.itba.paw.webapp.dto.output.MediaInListDto;
import ar.edu.itba.paw.webapp.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.webapp.utilities.NormalizerUtils;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ar.edu.itba.paw.webapp.utilities.ListCoverImpl.getListCover;

@Path("genres")
@Component
public class GenreController {

    @Autowired
    private GenreService genreService;
    @Autowired
    private ListsService listsService;
    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreController.class);

    private static final int itemsPerPage = 12;
    private static final int listInPage = 4;
    private static final int minimumMediaMatches = 2; //minimum amount of media on a list that must match for it to be showed
    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";


    @GET
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getGenres(){
        List<GenreDto> genreDtos = GenreDto.fromGenreList(uriInfo, genreService.getAllGenre());
        final Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<GenreDto>>(genreDtos){});
        LOGGER.info("GET /genres: Returning all genre types");
        return responseBuilder.build();

    }

    @GET
    @Path("{type}")
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getGenre(@PathParam("type") final String genreType){
        final Genre normalizedGenre = NormalizerUtils.getNormalizedGenres(Collections.singletonList(genreType)).stream().findFirst().orElseThrow(GenreNotFoundException::new);

        LOGGER.info("GET /genres/{}: Returning genre {} {}", normalizedGenre.getGenre(), normalizedGenre.getGenre(), normalizedGenre.getOrdinal());
        return Response.ok(GenreDto.fromGenre(uriInfo,normalizedGenre)).build();

    }

    @GET
    @Path("{type}/media")
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getGenreMedia(@PathParam("type") final String genreType,
                                  @QueryParam("page") @DefaultValue(defaultPage) int page,
                                  @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize){
        final Genre normalizedGenre = NormalizerUtils.getNormalizedGenres(Collections.singletonList(genreType)).stream().findFirst().orElseThrow(GenreNotFoundException::new);
        final PageContainer<Media> mediaPageContainer = genreService.getMediaByGenre(normalizedGenre, page , itemsPerPage);
        if(mediaPageContainer.getElements().isEmpty()){
            LOGGER.info("GET /genres/{}/media: Returning empty list", normalizedGenre.getGenre());
            return Response.noContent().build();
        }

        final List<MediaInGenreDto> mediaDtos = MediaInGenreDto.fromMediaList(uriInfo,normalizedGenre,mediaPageContainer.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaInGenreDto>>(mediaDtos){});
        ResponseUtils.setPaginationLinks(response,mediaPageContainer,uriInfo);

        LOGGER.info("GET /genres/{}/media: Returning page {} with {} results", normalizedGenre.getGenre(), mediaPageContainer.getCurrentPage(), mediaPageContainer.getElements().size());
        return response.build();
    }

    @RequestMapping("/genre/{genre}")
    public ModelAndView genre(@PathVariable(value = "genre") final String genre,
                              @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.info("Genre {} accessed", genre);
        final ModelAndView mav = new ModelAndView("principal/secondary/genre");
        final Genre normalizedGenre = NormalizerUtils.getNormalizedGenres(Collections.singletonList(genre)).stream().findFirst().orElseThrow(GenreNotFoundException::new);
        final PageContainer<Media> mediaPageContainer = genreService.getMediaByGenre(normalizedGenre, page - 1, itemsPerPage);
        final PageContainer<MediaList> genreLists = genreService.getListsContainingGenre(normalizedGenre, 0, listInPage, minimumMediaMatches, true);
        final List<ListCover> listCovers = getListCover(genreLists.getElements(), listsService);

        mav.addObject("genre",normalizedGenre);
        mav.addObject("mediaPageContainer", mediaPageContainer);
        mav.addObject("genreLists", listCovers);
        return mav;
    }
}
