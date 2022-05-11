package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.staff.Studio;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.core.UriInfo;

public class StudioDto {
    private int id;
    private String name;

    private String url;

    private String imageUrl;
    private String mediaUrl;

    public static StudioDto fromStudio(UriInfo url, Studio studio){
        StudioDto studioDto = new StudioDto();
        studioDto.id = studio.getStudioId();
        studioDto.name = studio.getName();

        studioDto.url = url.getBaseUriBuilder().path("studios").path(String.valueOf(studio.getStudioId())).build().toString();

        studioDto.imageUrl = url.getBaseUriBuilder().path("studios").path(String.valueOf(studio.getStudioId())).path("image").build().toString();
        studioDto.mediaUrl = url.getBaseUriBuilder().path("studios").path(String.valueOf(studio.getStudioId())).path("media").build().toString();
        return studioDto;
    }
    public static List<StudioDto> fromStudioList(UriInfo uriInfo, List<Studio> studioList){
        return studioList.stream().map(s -> StudioDto.fromStudio(uriInfo,s)).collect(Collectors.toList());

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

