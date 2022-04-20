package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.RoleType;
import ar.edu.itba.paw.models.staff.StaffMember;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MediaInStaffDto {
    private int id;
    private String title;
    private LocalDate releaseDate;

    private String url;

    private String mediaUrl;
    private String imageUrl;

    public static MediaInStaffDto fromMedia(UriInfo url, StaffMember staffMember, RoleType roleType, Media media) {
        MediaInStaffDto mediaInListDto = new MediaInStaffDto();
        mediaInListDto.id = media.getMediaId();
        mediaInListDto.title = media.getTitle();
        mediaInListDto.releaseDate = media.getReleaseDate().toLocalDate();

        mediaInListDto.url = url.getBaseUriBuilder().path("staff").path(String.valueOf(staffMember.getStaffMemberId())).path("media").queryParam("role", roleType.getRoleType()).build().toString();
        mediaInListDto.mediaUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).build().toString();
        mediaInListDto.imageUrl = media.getImage();

        return mediaInListDto;
    }

    public static List<MediaInStaffDto> fromStaffList(UriInfo uriInfo, StaffMember staffMember, RoleType roleType,List<Media> mediaList) {
        return mediaList.stream().map(m -> MediaInStaffDto.fromMedia(uriInfo, staffMember, roleType, m)).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
