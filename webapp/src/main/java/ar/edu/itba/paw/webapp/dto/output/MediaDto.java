package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.media.Country;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MediaDto {

    // Fields
    private int id;
    private MediaType type;
    private String title;
    private String description;
    private int length;
    private LocalDate releaseDate;
    private int seasons;
    private Country country;
    private int likes;

    // Entity url
    private String url;

    // Auxiliar urls
    private String imageUrl;
    private String genreUrl;
    private String staffUrl;
    private String studiosUrl;
    private String listsContainUrl;
    private String commentsUrl;


    // Related to current user
    private String favoriteUrl;
    private String toWatchMediaUrl;
    private String watchedMediaUrl;

    public static MediaDto fromMedia(UriInfo url, Media media, User currentUser) {
        MediaDto mediaDto = new MediaDto();
        mediaDto.id = media.getMediaId();
        mediaDto.type = media.getType();
        mediaDto.title = media.getTitle();
        mediaDto.description = media.getDescription();
        mediaDto.length = media.getLength();
        mediaDto.releaseDate = media.getReleaseDate().toLocalDate();
        mediaDto.seasons = media.getSeasons();
        mediaDto.country = media.getCountry();
        mediaDto.likes = media.getLikes();

        mediaDto.url = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).build().toString();
        mediaDto.imageUrl = media.getImage();
        mediaDto.listsContainUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).path("lists").build().toString();
        mediaDto.genreUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).path("genres").build().toString();
        mediaDto.staffUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).path("staff").build().toString();
        mediaDto.studiosUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).path("studios").build().toString();
        mediaDto.commentsUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(media.getMediaId())).path("comments").build().toString();
        if(currentUser != null) {
            mediaDto.favoriteUrl = url.getBaseUriBuilder().path("users").path(currentUser.getUsername()).path("favorite-media").path(String.valueOf(media.getMediaId())).build().toString();
            mediaDto.toWatchMediaUrl = url.getBaseUriBuilder().path("users").path(currentUser.getUsername()).path("to-watch-media").path(String.valueOf(media.getMediaId())).build().toString();
            mediaDto.watchedMediaUrl = url.getBaseUriBuilder().path("users").path(currentUser.getUsername()).path("watched-media").path(String.valueOf(media.getMediaId())).build().toString();

        }

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
        mediaDto.setLikes(media.getLikes());
        mediaDto.setImageUrl(media.getImage());
    }

    public static List< MediaDto > fromMediaList(UriInfo uriInfo, List<Media> mediaList, User currentUser) {
        return mediaList.stream().map(m -> MediaDto.fromMedia(uriInfo, m, currentUser)).collect(Collectors.toList());
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
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

    public String getGenreUrl() {
        return genreUrl;
    }

    public void setGenreUrl(String genreUrl) {
        this.genreUrl = genreUrl;
    }

    public String getStaffUrl() {
        return staffUrl;
    }

    public void setStaffUrl(String staffUrl) {
        this.staffUrl = staffUrl;
    }

    public String getStudiosUrl() {
        return studiosUrl;
    }

    public void setStudiosUrl(String studiosUrl) {
        this.studiosUrl = studiosUrl;
    }

    public String getListsContainUrl() {
        return listsContainUrl;
    }

    public void setListsContainUrl(String listsContainUrl) {
        this.listsContainUrl = listsContainUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public String getFavoriteUrl() {
        return favoriteUrl;
    }

    public void setFavoriteUrl(String favoriteUrl) {
        this.favoriteUrl = favoriteUrl;
    }

    public String getToWatchMediaUrl() {
        return toWatchMediaUrl;
    }

    public void setToWatchMediaUrl(String toWatchMediaUrl) {
        this.toWatchMediaUrl = toWatchMediaUrl;
    }

    public String getWatchedMediaUrl() {
        return watchedMediaUrl;
    }

    public void setWatchedMediaUrl(String watchedMediaUrl) {
        this.watchedMediaUrl = watchedMediaUrl;
    }
}
