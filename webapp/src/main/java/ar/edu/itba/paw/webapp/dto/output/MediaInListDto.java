package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MediaInListDto {

    private int id;
    private MediaType type;
    private String title;
    private LocalDate releaseDate;

    private String url;

    private String mediaUrl;
    private String imageUrl;

    public static MediaInListDto fromMedia(UriInfo url, MediaList mediaList, Media media) {
        MediaInListDto mediaInListDto = new MediaInListDto();
        mediaInListDto.id = media.getMediaId();
        mediaInListDto.type = media.getType();
        mediaInListDto.title = media.getTitle();
        mediaInListDto.releaseDate = media.getReleaseDate().toLocalDate();

        mediaInListDto.url = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaList.getMediaListId())).path("media").path(String.valueOf(media.getMediaId())).build().toString();
        mediaInListDto.mediaUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).build().toString();
        mediaInListDto.imageUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).path("image").build().toString();

        return mediaInListDto;
    }

    public static List<MediaInListDto> fromMediaList(UriInfo uriInfo, MediaList list, List<Media> mediaList) {
        return mediaList.stream().map(m -> MediaInListDto.fromMedia(uriInfo, list, m)).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
