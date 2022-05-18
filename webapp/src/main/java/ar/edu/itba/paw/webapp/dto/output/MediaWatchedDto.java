package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.media.WatchedMedia;
import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MediaWatchedDto extends MediaDto {

    private LocalDate watchDate;

    private String mediaUrl;
    private String userUrl;

    public static MediaWatchedDto fromWatchedMediaAndUser(UriInfo url, WatchedMedia watchedMedia, User user) {
        MediaWatchedDto mediaWatchedDto = new MediaWatchedDto();
        MediaDto.fillFromMedia(mediaWatchedDto, url, watchedMedia.getMedia());
        mediaWatchedDto.watchDate = watchedMedia.getWatchDate();

        mediaWatchedDto.setUrl(url.getBaseUriBuilder()
                .path("users")
                .path(String.valueOf(user.getUsername()))
                .path("watched-media")
                .path(String.valueOf(watchedMedia.getMedia().getMediaId()))
                .build().toString());

        mediaWatchedDto.mediaUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(watchedMedia.getMedia().getMediaId())).build().toString();
        mediaWatchedDto.userUrl = url.getBaseUriBuilder().path("user").path(String.valueOf(user.getUsername())).build().toString();

        return mediaWatchedDto;
    }

    public static List<MediaWatchedDto> fromWatchedMediaList(UriInfo uriInfo, List<WatchedMedia> mediaList, User user) {
        return mediaList.stream().map(m -> MediaWatchedDto.fromWatchedMediaAndUser(uriInfo, m, user)).collect(Collectors.toList());
    }

    public LocalDate getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(LocalDate watchDate) {
        this.watchDate = watchDate;
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
