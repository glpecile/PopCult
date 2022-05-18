package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.StaffService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.RoleType;
import ar.edu.itba.paw.models.staff.StaffMember;
import ar.edu.itba.paw.webapp.dto.output.GenreDto;
import ar.edu.itba.paw.webapp.dto.output.MediaDto;
import ar.edu.itba.paw.webapp.dto.output.StaffDto;
import ar.edu.itba.paw.webapp.exceptions.StaffNotFoundException;
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

@Path("staff")
@Component
public class StaffMemberController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StaffMemberController.class);

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {VndType.APPLICATION_STAFF})
    public Response getAllStaffMembers(@QueryParam("page") @DefaultValue(defaultPage) int page,
                                       @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize) {
        final PageContainer<StaffMember> staffMembers = staffService.getAllStaff(page, pageSize);
        if (staffMembers.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }
        final List<StaffDto> staffDtoList = StaffDto.fromStaffList(uriInfo, staffMembers.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<StaffDto>>(staffDtoList) {
        });
        ResponseUtils.setPaginationLinks(response, staffMembers, uriInfo);

        LOGGER.info("GET /{}: Returning page {} with {} results", uriInfo.getPath(), page, staffMembers.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {VndType.APPLICATION_STAFF})
    public Response getStaff(@PathParam("id") int staffId) {
        final StaffMember staffMember = staffService.getById(staffId).orElseThrow(StaffNotFoundException::new);

        final Response.ResponseBuilder response = Response.ok(StaffDto.fromStaff(uriInfo, staffMember));
        ResponseUtils.setUnconditionalCache(response);

        LOGGER.info("GET /{}: Returning staff {} {}", uriInfo.getPath(), staffId, staffMember.getName());
        return response.build();
    }

    @GET
    @Path("/{id}/media")
    @Produces(value = {VndType.APPLICATION_MEDIA})
    public Response getStaffMedia(@PathParam("id") int staffId,
                                  @QueryParam("page") @DefaultValue(defaultPage) int page,
                                  @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize,
                                  @QueryParam("role") String role) {
        final StaffMember staffMember = staffService.getById(staffId).orElseThrow(StaffNotFoundException::new);
        final RoleType normalizedRole = role != null ? RoleType.valueOf(role.toUpperCase()) : null;
        final PageContainer<Media> staffMedia = staffService.getMediaByRoleType(staffMember, page, pageSize, normalizedRole);

        if (staffMedia.getElements().isEmpty()) {
            LOGGER.info("GET /{}: Returning empty list", uriInfo.getPath());
            return Response.noContent().build();
        }

        final List<MediaDto> mediaDtoList = MediaDto.fromMediaList(uriInfo, staffMedia.getElements(), userService.getCurrentUser().orElse(null));
        final Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<MediaDto>>(mediaDtoList) {
        });
        ResponseUtils.setPaginationLinks(responseBuilder, staffMedia, uriInfo);

        LOGGER.info(" GET /{}: Returning page {} for role {} with {} results", uriInfo.getPath(), page, role, staffMedia.getElements().size());
        return responseBuilder.build();
    }

    @GET
    @Path("/{id}/image")
    public Response getImage(@PathParam("id") int staffId) throws URISyntaxException {
        final StaffMember staffMember = staffService.getById(staffId).orElseThrow(StaffNotFoundException::new);
        LOGGER.info("GET /{}: Redirecting to image location.", uriInfo.getPath());
        return Response.noContent().status(Response.Status.SEE_OTHER).location(new URI(staffMember.getImage())).build();
    }
}
