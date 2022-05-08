package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.lists.MediaList;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class UserCollaboratorDto {

    private boolean accepted;
    private String username;
    private String list;

    private String url;

    private String userUrl;
    private String listUrl;

    public static UserCollaboratorDto fromRequest(UriInfo url, Request request) {
        UserCollaboratorDto userCollaboratorDto = new UserCollaboratorDto();
        userCollaboratorDto.accepted = request.isAccepted();
        userCollaboratorDto.username = request.getCollaborator().getUsername();
        userCollaboratorDto.list = request.getMediaList().getListName();

        userCollaboratorDto.url = url.getBaseUriBuilder().path("lists").path(String.valueOf(request.getMediaList().getMediaListId())).path("collaborators").path(request.getCollaborator().getUsername()).build().toString();
        userCollaboratorDto.userUrl = url.getBaseUriBuilder().path("users").path(request.getCollaborator().getUsername()).build().toString();
        userCollaboratorDto.listUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(request.getMediaList().getMediaListId())).build().toString();

        return userCollaboratorDto;
    }

    public static List<UserCollaboratorDto> fromRequestList(UriInfo url, List<Request> requestList) {
        return requestList.stream().map(r -> UserCollaboratorDto.fromRequest(url, r)).collect(Collectors.toList());
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getListUrl() {
        return listUrl;
    }

    public void setListUrl(String listUrl) {
        this.listUrl = listUrl;
    }
}
