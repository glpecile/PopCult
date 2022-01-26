package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class MediaFavoriteDto extends MediaDto {

    private String mediaUrl;
    private String userUrl;

    public static MediaFavoriteDto fromMediaAndUser(UriInfo url, Media media, User user) {
        MediaFavoriteDto mediaFavoriteDto = new MediaFavoriteDto();
        MediaDto.fillFromMedia(mediaFavoriteDto, url, media);

        mediaFavoriteDto.setUrl(url.getBaseUriBuilder()
                .path("users")
                .path(String.valueOf(user.getUsername()))
                .path("favorite-media")
                .path(String.valueOf(media.getMediaId()))
                .build().toString());

        mediaFavoriteDto.mediaUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).build().toString();
        mediaFavoriteDto.userUrl = url.getBaseUriBuilder().path("user").path(String.valueOf(user.getUsername())).build().toString();

        return mediaFavoriteDto;
    }

    public static List<MediaFavoriteDto> fromMediaList(UriInfo uriInfo, List<Media> mediaList, User user) {
        return mediaList.stream().map(m -> MediaFavoriteDto.fromMediaAndUser(uriInfo, m, user)).collect(Collectors.toList());
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
