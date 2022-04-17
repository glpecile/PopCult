package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Role;
import ar.edu.itba.paw.models.staff.StaffMember;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class MediaStaffDto {

    //Fields
    private int id;

    private String name;

    //Entity url
    private String url;

    private String staffUrl;

    public static MediaStaffDto fromStaff(UriInfo url, Role staffMemberRole, Media media){
        MediaStaffDto staffDto = new MediaStaffDto();
        StaffMember staffMember = staffMemberRole.getStaffMember();
        staffDto.id = staffMember.getStaffMemberId();
        staffDto.name = staffMember.getName();

        staffDto.url = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).path("staff").build().toString();
        staffDto.staffUrl = url.getBaseUriBuilder().path("staff").path(String.valueOf(staffMember.getStaffMemberId())).build().toString();
        return staffDto;
    }

    public static List<MediaStaffDto> fromStaffList(UriInfo url, List<? extends Role> staffMembers, Media media){
        return staffMembers.stream().map(m -> MediaStaffDto.fromStaff(url, m, media)).collect(Collectors.toList());
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStaffUrl() {
        return staffUrl;
    }

    public void setStaffUrl(String staffUrl) {
        this.staffUrl = staffUrl;
    }
}
