package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MediaInGenreDto {

    private int id;
    private String title;
    private LocalDate releaseDate;

    private String url;

    private String mediaUrl;
    private String imageUrl;

    public static MediaInGenreDto fromMedia(UriInfo url, Genre genre, Media media){
        MediaInGenreDto mediaInGenreDto = new MediaInGenreDto();
        mediaInGenreDto.id = media.getMediaId();
        mediaInGenreDto.title = media.getTitle();
        mediaInGenreDto.releaseDate = media.getReleaseDate().toLocalDate();

        mediaInGenreDto.url = url.getBaseUriBuilder().path("genres").path(genre.getGenre()).path("media").build().toString();
        mediaInGenreDto.mediaUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).build().toString();
        mediaInGenreDto.imageUrl = media.getImage();
        return mediaInGenreDto;
    }

    public static List<MediaInGenreDto> fromMediaList(UriInfo uriInfo, Genre genre, List<Media> mediaList) {
        return mediaList.stream().map(m -> MediaInGenreDto.fromMedia(uriInfo, genre, m)).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
