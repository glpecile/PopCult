package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.StaffService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.RoleType;
import ar.edu.itba.paw.models.staff.StaffMember;
import ar.edu.itba.paw.webapp.dto.output.MediaDto;
import ar.edu.itba.paw.webapp.dto.output.MediaInStaffDto;
import ar.edu.itba.paw.webapp.dto.output.StaffDto;
import ar.edu.itba.paw.webapp.exceptions.StaffNotFoundException;
import ar.edu.itba.paw.webapp.utilities.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(StaffMemberController.class);

    private static final int itemsPerPage = 4;

    private static final String defaultPage = "1";
    private static final String defaultPageSize = "12";

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getAllStaffMembers(@QueryParam("page") @DefaultValue(defaultPage) int page,
                             @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize
                             ){
        final PageContainer<StaffMember> staffMembers = staffService.getAllStaff(page,pageSize);
        if(staffMembers.getElements().isEmpty()) {
            LOGGER.info("GET /staff: Returning empty list");
            return Response.noContent().build();
        }
        final List<StaffDto> staffDtoList = StaffDto.fromStaffList(uriInfo,staffMembers.getElements());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<List<StaffDto>>(staffDtoList){});
        ResponseUtils.setPaginationLinks(response,staffMembers,uriInfo);

        LOGGER.info("GET /staff: Returning page {} with {} results", page, staffMembers.getElements().size());
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getStaff(@PathParam("id") int staffId){
        final StaffMember staffMember = staffService.getById(staffId).orElseThrow(StaffNotFoundException::new);

        LOGGER.info("GET /staff/{}: Returning staff {} {}", staffId, staffId, staffMember.getName());
        return Response.ok(StaffDto.fromStaff(uriInfo,staffMember)).build();
    }

    @GET
    @Path("/{id}/media")
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getStaffMedia(@PathParam("id") int staffId,
                                  @QueryParam("page") @DefaultValue(defaultPage) int page,
                                  @QueryParam("page-size") @DefaultValue(defaultPageSize) int pageSize,
                                  @QueryParam("role") String role){
        final StaffMember staffMember = staffService.getById(staffId).orElseThrow(StaffNotFoundException::new);
        final RoleType normalizedRole = role != null ? RoleType.valueOf(role.toUpperCase()) : null;
        final PageContainer<Media> staffMedia = staffService.
                getMediaByRoleType(staffMember, page - 1, pageSize, normalizedRole);
        if(staffMedia.getElements().isEmpty()){
            LOGGER.info("GET /staff/{}/media: Returning empty list",staffId);
            return Response.noContent().build();
        }

        final List<MediaInStaffDto> mediaDtoList = MediaInStaffDto.fromStaffList(uriInfo,staffMember,normalizedRole,staffMedia.getElements());
        final Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<MediaInStaffDto>>(mediaDtoList){});
        ResponseUtils.setPaginationLinks(responseBuilder,staffMedia,uriInfo);

        LOGGER.info(" GET /staff/{}/media: Returning page {} for role {} with {} results", staffId,page,role,staffMedia.getElements().size());
        return responseBuilder.build();
    }

    @GET
    @Path("/{id}/image")
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getImage(@PathParam("id") int staffId) throws URISyntaxException {
        final StaffMember staffMember = staffService.getById(staffId).orElseThrow(StaffNotFoundException::new);
        LOGGER.info("GET /staff/{}/image: Redirecting to image location.", staffId);
        return Response.status(Response.Status.SEE_OTHER).location(new URI(staffMember.getImage())).build();
    }
    @RequestMapping("/staff/{staffMemberId}")
    public ModelAndView staffMemberProfile(@PathVariable("staffMemberId") final int staffMemberId,
                                           @RequestParam(value = "page", defaultValue = "1") final int page) {
        LOGGER.debug("Trying to access staff {}.", staffMemberId);
        final ModelAndView mav = new ModelAndView("principal/secondary/staffMemberProfile");
        final StaffMember member = staffService.getById(staffMemberId).orElseThrow(StaffNotFoundException::new);
        final PageContainer<Media> media = staffService.getMedia(member, page - 1, itemsPerPage);
        mav.addObject("member", member);
        mav.addObject("mediaContainer", media);

        LOGGER.info("Staff {} accessed.", staffMemberId);
        return mav;
    }

    @RequestMapping("/staff/{staffMemberId}/{roleType}")
    public ModelAndView staffMemberRoleType(@PathVariable("staffMemberId") final int staffMemberId,
                                            @PathVariable("roleType") final String roleType,
                                            @RequestParam(value = "page", defaultValue = "1") final int page) {

        LOGGER.debug("Trying to access staff {} as {}", staffMemberId, roleType);
        final ModelAndView mav = new ModelAndView("principal/secondary/staffMemberProfile");
        final StaffMember member = staffService.getById(staffMemberId).orElseThrow(StaffNotFoundException::new);
        final RoleType normalizedRole = RoleType.valueOf(roleType.toUpperCase());
        final PageContainer<Media> media = staffService.
                getMediaByRoleType(member, page - 1, itemsPerPage, normalizedRole);
        mav.addObject("roleType", roleType);
        mav.addObject("member", member);
        mav.addObject("mediaContainer", media);

        LOGGER.info("Staff {} as {} accessed.", staffMemberId, roleType);
        return mav;
    }

}
