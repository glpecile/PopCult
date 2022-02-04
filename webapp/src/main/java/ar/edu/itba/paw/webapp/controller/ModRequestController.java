package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ModeratorService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.comment.Notification;
import ar.edu.itba.paw.models.user.ModRequest;
import ar.edu.itba.paw.webapp.dto.output.ModRequestDto;
import ar.edu.itba.paw.webapp.dto.output.NotificationDto;
import ar.edu.itba.paw.webapp.exceptions.NotificationNotFoundException;
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

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getModRequest(@PathParam("id") int requestId) {
        ModRequest modRequest = moderatorService.getModRequest(requestId).orElseThrow(NotificationNotFoundException::new);

        LOGGER.info("GET /mods-requests/{}: Returning notification {}", requestId, requestId);
        return Response.ok(ModRequestDto.fromModRequest(uriInfo, modRequest)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response setNotificationAsOpened(@PathParam("id") int requestId) {
        ModRequest modRequest = moderatorService.getModRequest(requestId).orElseThrow(NotificationNotFoundException::new);

        moderatorService.promoteToMod(modRequest.getUser());

        LOGGER.info("PUT /mods-requests/{}: Mod request {} approved. {} is mod", requestId, requestId, modRequest.getUser().getUsername());
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteNotification(@PathParam("id") int requestId) {
        ModRequest modRequest = moderatorService.getModRequest(requestId).orElseThrow(NotificationNotFoundException::new);

        moderatorService.removeModRequest(modRequest);

        LOGGER.info("DELETE /mods-requests/{}: Mod request {} deleted", requestId, requestId);
        return Response.noContent().build();
    }
}
