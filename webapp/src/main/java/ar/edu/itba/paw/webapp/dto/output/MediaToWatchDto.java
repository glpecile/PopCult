package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class MediaToWatchDto extends MediaDto {
    private String mediaUrl;
    private String userUrl;

    public static MediaToWatchDto fromMediaAndUser(UriInfo url, Media media, User user) {
        MediaToWatchDto mediaToWatchDto = new MediaToWatchDto();
        MediaDto.fillFromMedia(mediaToWatchDto, url, media);

        mediaToWatchDto.setUrl(url.getBaseUriBuilder()
                .path("users")
                .path(String.valueOf(user.getUsername()))
                .path("to-watch-media")
                .path(String.valueOf(media.getMediaId()))
                .build().toString());

        mediaToWatchDto.mediaUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).build().toString();
        mediaToWatchDto.userUrl = url.getBaseUriBuilder().path("user").path(String.valueOf(user.getUsername())).build().toString();

        return mediaToWatchDto;
    }

    public static List<MediaToWatchDto> fromMediaList(UriInfo uriInfo, List<Media> mediaList, User user) {
        return mediaList.stream().map(m -> MediaToWatchDto.fromMediaAndUser(uriInfo, m, user)).collect(Collectors.toList());
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }
}
