package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class ListFavoriteDto {

    private int id;
    private String name;

    private String url;

    private String listUrl;
    private String mediaUrl;

    public static ListFavoriteDto fromList(UriInfo url, MediaList mediaList, User user) {
        ListFavoriteDto listFavoriteDto = new ListFavoriteDto();
        listFavoriteDto.id = mediaList.getMediaListId();
        listFavoriteDto.name = mediaList.getListName();

        listFavoriteDto.url = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("favorite-lists").path(String.valueOf(mediaList.getMediaListId())).build().toString();
        listFavoriteDto.listUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaList.getMediaListId())).build().toString();
        listFavoriteDto.mediaUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaList.getMediaListId())).path("media").build().toString();

        return listFavoriteDto;
    }

    public static List<ListFavoriteDto> fromListList(UriInfo uriInfo, List<MediaList> listList, User user) {
        return listList.stream().map(l -> ListFavoriteDto.fromList(uriInfo, l, user)).collect(Collectors.toList());
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

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}
