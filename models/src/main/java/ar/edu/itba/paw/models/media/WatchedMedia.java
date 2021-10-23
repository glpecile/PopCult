package ar.edu.itba.paw.models.media;

import java.util.Date;

public class WatchedMedia {
    private final int mediaId;
    private final int type;
    private final String title;
    private final String description;
    private final String image;
    private final int length;
    private final Date releaseDate;
    private final int seasons;
    private final String country;
    private final Date lastWatched;

    public WatchedMedia(int mediaId, int type, String title, String description, String image, int length, Date releaseDate, int seasons, int country, Date lastWatched) {
        this.mediaId = mediaId;
        this.type = type;
        this.title = title;
        this.description = description;
        this.image = image;
        this.length = length;
        this.releaseDate = releaseDate;
        this.seasons = seasons;
        this.country = Country.values()[country].getCountryName();
        this.lastWatched = lastWatched;
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

    public String getReleaseYear() {
        return String.valueOf(releaseDate).substring(0, 4);
    }

    public Date getLastWatched() {
        return lastWatched;
    }
}
