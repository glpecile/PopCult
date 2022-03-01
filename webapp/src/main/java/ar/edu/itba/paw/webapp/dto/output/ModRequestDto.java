package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.user.ModRequest;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ModRequestDto {

    private String username;
    private String name;
    private LocalDateTime date;

    private String url;

    private String userUrl;
    private String imageUrl;

    public static ModRequestDto fromModRequest(UriInfo url, ModRequest modRequest) {
        ModRequestDto modRequestDto = new ModRequestDto();
        modRequestDto.username = modRequest.getUser().getUsername();
        modRequestDto.name = modRequest.getUser().getName();
        modRequestDto.date = modRequest.getDate();

        modRequestDto.url = url.getBaseUriBuilder().path("mods-requests").path(String.valueOf(modRequest.getRequestId())).build().toString();
        modRequestDto.userUrl = url.getBaseUriBuilder().path("users").path(modRequest.getUser().getUsername()).build().toString();
        modRequestDto.imageUrl = url.getBaseUriBuilder().path("users").path(modRequest.getUser().getUsername()).path("image").build().toString();
        return modRequestDto;
    }

    public static List<ModRequestDto> fromModRequestList(UriInfo url, List<ModRequest> modRequestList) {
        return modRequestList.stream().map(m -> ModRequestDto.fromModRequest(url, m)).collect(Collectors.toList());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
