package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.StudioService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Studio;
import ar.edu.itba.paw.webapp.dto.output.MediaDto;
import ar.edu.itba.paw.webapp.dto.output.StaffDto;
import ar.edu.itba.paw.webapp.dto.output.StudioDto;
import ar.edu.itba.paw.webapp.exceptions.StudioNotFoundException;
import ar.edu.itba.paw.webapp.mediaType.VndType;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("studios")
@Component
public class StudioController {

    @Autowired
    private StudioService studioService;
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudioController.class);

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {VndType.APPLICATION_STUDIOS})
    public Response getStudios(@QueryParam("page") @DefaultValue(defaultPage) int page,
                               @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final PageContainer<Studio> studioPageContainer = studioService.getAllStudios(page, pageSize);
        if (studioPageContainer.getElements().isEmpty()) {
            LOGGER.info("GET /{} : Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }
        final List<StudioDto> studioDtoList = StudioDto.fromStudioList(uriInfo, studioPageContainer.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<StudioDto>>(studioDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, studioPageContainer, uriInfo);

        LOGGER.info("GET /{} : Returning page {} with {} results", uriInfo.getPath(), page, studioPageContainer.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {VndType.APPLICATION_STUDIOS})
    public Response getStudio(@PathParam("id") int studioId) {
        final Studio studio = studioService.getById(studioId).orElseThrow(StudioNotFoundException::new);

        final Response.ResponseBuilder response = Response.ok(StudioDto.fromStudio(uriInfo, studio));
        ResponseUtils.setUnconditionalCache(response);

        LOGGER.info("GET /{} : Returning studio {} {}", uriInfo.getPath(), studioId, studio.getName());
        return response.build();
    }

    @GET
    @Path("/{id}/image")
    public Response getStudioImage(@PathParam("id") int studioId) throws URISyntaxException {
        final Studio studio = studioService.getById(studioId).orElseThrow(StudioNotFoundException::new);

        LOGGER.info("GET /{}: Redirecting to image location", uriInfo.getPath());
        return Response.noContent().status(Response.Status.SEE_OTHER).location(new URI(studio.getImage())).build();
    }

    @GET
    @Path("/{id}/media")
    @Produces(value = {VndType.APPLICATION_MEDIA})
    public Response getStudioMedia(@PathParam("id") int studioId,
                                   @QueryParam("page") @DefaultValue(defaultPage) int page,
                                   @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final Studio studio = studioService.getById(studioId).orElseThrow(StudioNotFoundException::new);
        final PageContainer<Media> mediaByStudio = studioService.getMediaByStudio(studio, page, pageSize);

        if (mediaByStudio.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<MediaDto> mediaDtoList = MediaDto.fromMediaList(uriInfo, mediaByStudio.getElements(), userService.getCurrentUser().orElse(null));
        final Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<MediaDto>>(mediaDtoList) {
        });
        ResponseUtils.setPaginationLinks(responseBuilder, mediaByStudio, uriInfo);

        LOGGER.info(" GET /{}: Returning page {} with {} results", uriInfo.getPath(), page, mediaByStudio.getElements().size());
        return responseBuilder.build();
    }
}
