package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ModeratorService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.ModRequest;
import ar.edu.itba.paw.webapp.dto.output.ModRequestDto;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("mods-requests")
public class ModRequestController {

    @Autowired
    private ModeratorService moderatorService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ModRequestController.class);

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getModRequests(@QueryParam("page") @DefaultValue(defaultPage) int page,
                                   @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final PageContainer<ModRequest> modRequests = moderatorService.getModRequests(page, pageSize);

        if (modRequests.getElements().isEmpty()) {
            LOGGER.info("GET /mods-requests: Returning empty list");
            return Response.noContent().build();
        }
        final List<ModRequestDto> modRequestDtoList = ModRequestDto.fromModRequestList(uriInfo, modRequests.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<ModRequestDto>>(modRequestDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, modRequests, uriInfo);
        LOGGER.info("GET /mods-requests: Returning page {} with {} results", modRequests.getCurrentPage(), modRequests.getElements().size());
        return response.build();
    }
}
