package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.GenreService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.webapp.dto.output.GenreDto;
import ar.edu.itba.paw.webapp.dto.output.ListDto;
import ar.edu.itba.paw.webapp.dto.output.MediaDto;
import ar.edu.itba.paw.webapp.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.webapp.mediaType.VndType;
import ar.edu.itba.paw.webapp.utilities.NormalizerUtils;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collections;
import java.util.List;

@Path("genres")
@Component
public class GenreController {

    @Autowired
    private GenreService genreService;
    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreController.class);

    private static final int minimumMediaMatches = 2; //minimum amount of media on a list that must match for it to be showed
    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @GET
    @Produces(value = {VndType.APPLICATION_GENRES})
    public Response getGenres() {
        List<GenreDto> genreDtos = GenreDto.fromGenreList(uriInfo, genreService.getAllGenre());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<GenreDto>>(genreDtos) {
        });
        ResponseUtils.setUnconditionalCache(response);

        LOGGER.info("GET /{}: Returning all genre types", uriInfo.getPath());
        return response.build();
    }

    @GET
    @Path("{type}")
    @Produces(value = {VndType.APPLICATION_GENRES})
    public Response getGenre(@PathParam("type") final String genreType) {
        final Genre normalizedGenre = NormalizerUtils.getNormalizedGenres(Collections.singletonList(genreType)).stream().findFirst().orElseThrow(GenreNotFoundException::new);

        final Response.ResponseBuilder response = Response.ok(GenreDto.fromGenre(uriInfo, normalizedGenre));
        ResponseUtils.setUnconditionalCache(response);

        LOGGER.info("GET /{}: Returning genre {} {}", uriInfo.getPath(), normalizedGenre.getGenre(), normalizedGenre.getOrdinal());
        return response.build();
    }

    @GET
    @Path("{type}/media")
    @Produces(value = {VndType.APPLICATION_MEDIA})
    public Response getGenreMedia(@PathParam("type") final String genreType,
                                  @QueryParam("page") @DefaultValue(defaultPage) int page,
                                  @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final Genre normalizedGenre = NormalizerUtils.getNormalizedGenres(Collections.singletonList(genreType)).stream().findFirst().orElseThrow(GenreNotFoundException::new);
        final PageContainer<Media> mediaPageContainer = genreService.getMediaByGenre(normalizedGenre, page, pageSize);
        if (mediaPageContainer.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<MediaDto> mediaDtos = MediaDto.fromMediaList(uriInfo, mediaPageContainer.getElements(), userService.getCurrentUser().orElse(null));
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MediaDto>>(mediaDtos) {
        });
        ResponseUtils.setPaginationLinks(response, mediaPageContainer, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results", uriInfo.getPath(), mediaPageContainer.getCurrentPage(), mediaPageContainer.getElements().size());
        return response.build();
    }

    @GET
    @Path("{type}/lists")
    @Produces(value = {VndType.APPLICATION_LISTS})
    public Response getGenreLists(@PathParam("type") final String genreType,
                                  @QueryParam("page") @DefaultValue(defaultPage) int page,
                                  @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final Genre normalizedGenre = NormalizerUtils.getNormalizedGenres(Collections.singletonList(genreType)).stream().findFirst().orElseThrow(GenreNotFoundException::new);
        final PageContainer<MediaList> listPageContainer = genreService.getListsContainingGenre(normalizedGenre, page, pageSize, minimumMediaMatches);
        if (listPageContainer.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }
        final List<ListDto> listDtos = ListDto.fromListList(uriInfo, listPageContainer.getElements(), userService.getCurrentUser().orElse(null));
        final Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<ListDto>>(listDtos) {
        });
        ResponseUtils.setPaginationLinks(responseBuilder, listPageContainer, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results", uriInfo.getPath(), listPageContainer.getCurrentPage(), listPageContainer.getElements().size());
        return responseBuilder.build();
    }
}
