package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.staff.StaffMember;

import javax.ws.rs.core.UriInfo;

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

    public static StaffDto fromStaff(UriInfo url, StaffMember staffMember){
        StaffDto staffDto = new StaffDto();
        staffDto.id = staffMember.getStaffMemberId();
        staffDto.name = staffMember.getName();
        staffDto.description = staffMember.getDescription();

        staffDto.url = url.getBaseUriBuilder().path("staff").path(staffMember.getStaffMemberId().toString()).build().toString();
        staffDto.imageUrl = url.getBaseUriBuilder().path("staff").path(staffMember.getStaffMemberId().toString()).path("image").build().toString();
        staffDto.mediaUrl = url.getBaseUriBuilder().path("staff").path(staffMember.getStaffMemberId().toString()).build().toString();
        return staffDto;
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
}
