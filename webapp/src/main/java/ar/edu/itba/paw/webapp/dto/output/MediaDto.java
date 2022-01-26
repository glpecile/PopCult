package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.media.Country;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MediaDto {

    private MediaType type;
    private String title;
    private String description;
    private int length;
    private LocalDate releaseDate;
    private int seasons;
    private Country country;

    private String url;

    private String imageUrl;

    public static MediaDto fromMedia(UriInfo url, Media media) {
        MediaDto mediaDto = new MediaDto();
        mediaDto.type = media.getType();
        mediaDto.title = media.getTitle();
        mediaDto.description = media.getDescription();
        mediaDto.length = media.getLength();
        mediaDto.releaseDate = media.getReleaseDate().toLocalDate();
        mediaDto.seasons = media.getSeasons();
        mediaDto.country = media.getCountry();

        mediaDto.url = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).build().toString();
        mediaDto.imageUrl = media.getImage();

        return mediaDto;
    }

    protected static void fillFromMedia(MediaDto mediaDto, UriInfo url, Media media) {
        mediaDto.setType(media.getType());
        mediaDto.setTitle(media.getTitle());
        mediaDto.setDescription(media.getDescription());
        mediaDto.setLength(media.getLength());
        mediaDto.setReleaseDate(media.getReleaseDate().toLocalDate());
        mediaDto.setSeasons(media.getSeasons());
        mediaDto.setCountry(media.getCountry());

        mediaDto.setImageUrl(media.getImage());
    }

    public static List<MediaDto> fromMediaList(UriInfo uriInfo, List<Media> mediaList) {
        return mediaList.stream().map(m -> MediaDto.fromMedia(uriInfo, m)).collect(Collectors.toList());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getSeasons() {
        return seasons;
    }

    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
