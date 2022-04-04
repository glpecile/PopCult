package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.UriInfo;

public class UserCollaboratorDto {

    private String username;

    private String url;

    private String userUrl;

    public static UserCollaboratorDto fromUser(UriInfo url, MediaList mediaList, User user) {
        UserCollaboratorDto userCollaboratorDto = new UserCollaboratorDto();
        userCollaboratorDto.username = user.getUsername();

        userCollaboratorDto.url = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaList.getMediaListId())).path("collaborators").path(user.getUsername()).build().toString();
        userCollaboratorDto.userUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).build().toString();

        return userCollaboratorDto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
