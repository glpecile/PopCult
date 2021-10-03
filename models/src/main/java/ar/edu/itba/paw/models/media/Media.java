package ar.edu.itba.paw.models.media;

import java.util.Date;
import java.util.Objects;


public class Media {
    private final int mediaId;
    private final int type;
    private final String title;
    private final String description;
    private final String image;
    private final int length;
    private final Date releaseDate;
    private final int seasons;
    private final String country;

    public Media(int mediaId, int type, String title, String description, String image, int length, Date releaseDate, int seasons, int country) {
        this.mediaId = mediaId;
        this.type = type;
        this.title = title;
        this.description = description;
        this.image = image;
        this.length = length;
        this.releaseDate = releaseDate;
        this.seasons = seasons;
        this.country = Country.values()[country].getCountryName();
    }

    public int getMediaId() {
        return mediaId;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getLength() {
        return length;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseYear(){
        return String.valueOf(releaseDate).substring(0, 4);
    }

    public int getSeasons() {
        return seasons;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return mediaId == media.mediaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaId);
    }
}
