package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.collaborative.CollabRequest;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class CollaboratorRequestDto {

    private boolean accepted;
    private String username;
    private String list;

    private String url;

    private String userUrl;
    private String listUrl;

    public static CollaboratorRequestDto fromRequest(UriInfo url, CollabRequest request) {
        CollaboratorRequestDto collaboratorRequestDto = new CollaboratorRequestDto();
        collaboratorRequestDto.accepted = request.isAccepted();
        collaboratorRequestDto.username = request.getCollaborator().getUsername();
        collaboratorRequestDto.list = request.getMediaList().getListName();

        collaboratorRequestDto.url = url.getBaseUriBuilder().path("collab-requests").path(String.valueOf(request.getCollabId())).build().toString();
        collaboratorRequestDto.userUrl = url.getBaseUriBuilder().path("users").path(request.getCollaborator().getUsername()).build().toString();
        collaboratorRequestDto.listUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(request.getMediaList().getMediaListId())).build().toString();

        return collaboratorRequestDto;
    }

    public static List<CollaboratorRequestDto> fromRequestList(UriInfo url, List<CollabRequest> requestList) {
        return requestList.stream().map(r -> CollaboratorRequestDto.fromRequest(url, r)).collect(Collectors.toList());
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
