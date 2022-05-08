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
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Path("studios")
@Component
public class StudioController {

    @Autowired
    private StudioService studioService;
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudioController.class);

    private static final int itemsPerPage = 12;

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getStudios(@QueryParam("page") @DefaultValue(defaultPage) int page,
                      @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize){
        final PageContainer<Studio> studioPageContainer = studioService.getAllStudios(page,pageSize);
        if(studioPageContainer.getElements().isEmpty()){
            LOGGER.info("GET /{} : Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }
        final List<StudioDto> studioDtoList = StudioDto.fromStudioList(uriInfo,studioPageContainer.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<StudioDto>>(studioDtoList){});
        ResponseUtils.setPaginationLinks(response,studioPageContainer,uriInfo);

        LOGGER.info("GET /{} : Returning page {} with {} results", uriInfo.getPath(), page, studioPageContainer.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getStudio(@PathParam("id") int studioId){
        final Studio studio = studioService.getById(studioId).orElseThrow(StudioNotFoundException::new);

        LOGGER.info("GET /{} : Returning studio {} {}", uriInfo.getPath(), studioId, studio.getName());
        return Response.ok(StudioDto.fromStudio(uriInfo,studio)).build();
    }

    @GET
    @Path("/{id}/image")
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getStudioImage(@PathParam("id") int studioId) throws URISyntaxException{
        final Studio studio = studioService.getById(studioId).orElseThrow(StudioNotFoundException::new);

        LOGGER.info("GET /{}: Redirecting to image location", uriInfo.getPath());
        return Response.status(Response.Status.SEE_OTHER).location(new URI(studio.getImage())).build();
    }

    @GET
    @Path("/{id}/media")
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getStudioMedia(@PathParam("id") int studioId,
                                   @QueryParam("page") @DefaultValue(defaultPage) int page,
                                   @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize){
        final Studio studio = studioService.getById(studioId).orElseThrow(StudioNotFoundException::new);
        final PageContainer<Media> mediaByStudio = studioService.getMediaByStudio(studio,page,pageSize);

        if(mediaByStudio.getElements().isEmpty()){
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<MediaDto> mediaDtoList = MediaDto.fromMediaList(uriInfo,mediaByStudio.getElements(),userService.getCurrentUser().orElse(null));
        final Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<MediaDto>>(mediaDtoList){});
        ResponseUtils.setPaginationLinks(responseBuilder,mediaByStudio,uriInfo);

        LOGGER.info(" GET /{}: Returning page {} with {} results", uriInfo.getPath(), page, mediaByStudio.getElements().size());
        return responseBuilder.build();
    }



    @RequestMapping("/studio/{studioId}")
    public ModelAndView studio(@PathVariable(value = "studioId") final int studioId,
                               @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.debug("Trying to access studio {}", studioId);
        final ModelAndView mav = new ModelAndView("principal/secondary/studio");
        final Studio studio = studioService.getById(studioId).orElseThrow(StudioNotFoundException::new);
        final PageContainer<Media> mediaPageContainer = studioService.getMediaByStudio(studio, page - 1, itemsPerPage);
        mav.addObject("studio", studio);
        mav.addObject("mediaPageContainer", mediaPageContainer);
        LOGGER.info("Studio {} accessed", studioId);
        return mav;
    }
}
