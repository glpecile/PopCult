package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class ListPeekDto {
    private int id;
    private String name;

    private String url;

    private String listUrl;

    public static ListPeekDto fromList(UriInfo url, MediaList mediaList, Media media) {
        ListPeekDto listPeekDto = new ListPeekDto();
        listPeekDto.id = mediaList.getMediaListId();
        listPeekDto.name = mediaList.getListName();

        listPeekDto.url = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).path("lists").build().toString();
        listPeekDto.listUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(listPeekDto.id)).build().toString();
        return listPeekDto;
    }

    public static List<ListPeekDto> fromMediaListList(UriInfo uriInfo, List<MediaList> listList, Media media) {
        return listList.stream().map(l -> ListPeekDto.fromList(uriInfo, l, media)).collect(Collectors.toList());
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
