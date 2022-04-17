package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Studio;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class MediaStudioDto {
    private int id;
    private String name;

    private String url;

    private String studioUrl;

    public static MediaStudioDto fromStudio(UriInfo url, Studio studio, Media media){
        MediaStudioDto studioDto = new MediaStudioDto();
        studioDto.id = studio.getStudioId();
        studioDto.name = studio.getName();

        studioDto.url = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).path("studios").build().toString();

        studioDto.studioUrl = url.getBaseUriBuilder().path("studios").path(String.valueOf(studio.getStudioId())).build().toString();
        return studioDto;
    }
    public static List<MediaStudioDto> fromStudioList(UriInfo uriInfo, List<Studio> studioList, Media media){
        return studioList.stream().map(s -> MediaStudioDto.fromStudio(uriInfo,s, media)).collect(Collectors.toList());

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

    public String getStudioUrl() {
        return studioUrl;
    }

    public void setStudioUrl(String studioUrl) {
        this.studioUrl = studioUrl;
    }
}
