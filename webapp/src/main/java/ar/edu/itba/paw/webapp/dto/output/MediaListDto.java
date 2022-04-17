package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class MediaListDto {
    private int id;
    private String name;

    private String url;

    private String listUrl;

    public static MediaListDto fromList(UriInfo url, MediaList mediaList, Media media) {
        MediaListDto mediaListDto = new MediaListDto();
        mediaListDto.id = mediaList.getMediaListId();
        mediaListDto.name = mediaList.getListName();

        mediaListDto.url = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).path("lists").build().toString();
        mediaListDto.listUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaListDto.id)).build().toString();
        return mediaListDto;
    }

    public static List<MediaListDto> fromMediaListList(UriInfo uriInfo, List<MediaList> listList, Media media) {
        return listList.stream().map(l -> MediaListDto.fromList(uriInfo, l, media)).collect(Collectors.toList());
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

    public String getListUrl() {
        return listUrl;
    }

    public void setListUrl(String listUrl) {
        this.listUrl = listUrl;
    }
}
