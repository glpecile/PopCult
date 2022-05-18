package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.staff.Role;
import ar.edu.itba.paw.models.staff.StaffMember;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class StaffDto {

    //Fields
    private int id;

    private String name;

    private String description;


    //Entity url
    private String url;

    //Auxiliar urls
    private String imageUrl;
    private String mediaUrl;
    private String mediaDirectorUrl;
    private String mediaActorUrl;

    public static StaffDto fromStaff(UriInfo url, StaffMember staffMember) {
        StaffDto staffDto = new StaffDto();
        staffDto.id = staffMember.getStaffMemberId();
        staffDto.name = staffMember.getName();
        staffDto.description = staffMember.getDescription();

        staffDto.url = url.getBaseUriBuilder().path("staff").path(staffMember.getStaffMemberId().toString()).build().toString();
        staffDto.imageUrl = url.getBaseUriBuilder().path("staff").path(staffMember.getStaffMemberId().toString()).path("image").build().toString();
        staffDto.mediaUrl = url.getBaseUriBuilder().path("staff").path(staffMember.getStaffMemberId().toString()).path("media").build().toString();
        staffDto.mediaDirectorUrl = url.getBaseUriBuilder().path("staff").path(staffMember.getStaffMemberId().toString()).path("media").queryParam("role", "Director").build().toString();
        staffDto.mediaActorUrl = url.getBaseUriBuilder().path("staff").path(staffMember.getStaffMemberId().toString()).path("media").queryParam("role", "Actor").build().toString();
        return staffDto;
    }

    public static StaffDto fromRole(UriInfo url, Role staffMemberRole) {
        return fromStaff(url, staffMemberRole.getStaffMember());
    }

    public static List<StaffDto> fromStaffList(UriInfo url, List<StaffMember> staffMembers) {
        return staffMembers.stream().map(m -> StaffDto.fromStaff(url, m)).collect(Collectors.toList());
    }

    public static List<StaffDto> fromRoleList(UriInfo url, List<? extends Role> staffMemberRoles) {
        return fromStaffList(url, staffMemberRoles.stream().map(Role::getStaffMember).collect(Collectors.toList()));
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaDirectorUrl() {
        return mediaDirectorUrl;
    }

    public void setMediaDirectorUrl(String mediaDirectorUrl) {
        this.mediaDirectorUrl = mediaDirectorUrl;
    }

    public String getMediaActorUrl() {
        return mediaActorUrl;
    }

    public void setMediaActorUrl(String mediaActorUrl) {
        this.mediaActorUrl = mediaActorUrl;
    }
}
