package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.media.Genre;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class GenreDto {

    //Fields
    private int id;
    private Genre genre;

    //Entity url
    private String url;

    //Auxiliar urls
    private String mediaUrl;
    private String listsUrl;

    public static GenreDto fromGenre(UriInfo url, Genre genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.id = genre.getOrdinal();
        genreDto.genre = genre;

        genreDto.url = url.getBaseUriBuilder().path("genres").path(genreDto.genre.getGenre()).build().toString();
        genreDto.mediaUrl = url.getBaseUriBuilder().path("genres").path(genreDto.genre.getGenre()).path("media").build().toString();
        genreDto.listsUrl = url.getBaseUriBuilder().path("genres").path(genreDto.genre.getGenre()).path("lists").build().toString();
        return genreDto;
    }

    public static List<GenreDto> fromGenreList(UriInfo uriInfo, List<Genre> genreList) {
        return genreList.stream().map(g -> GenreDto.fromGenre(uriInfo, g)).collect(Collectors.toList());
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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

    public String getListsUrl() {
        return listsUrl;
    }

    public void setListsUrl(String listsUrl) {
        this.listsUrl = listsUrl;
    }
}
