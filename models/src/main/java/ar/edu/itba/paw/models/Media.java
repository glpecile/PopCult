package ar.edu.itba.paw.models;

import java.util.Date;


public class Media {
    private int mediaId;
    private int type;
    private String title;
    private String description;
    private String image;
    private int length;
    private Date releaseDate;
    private int seasons;
    private int country;

    public Media(int mediaId, int type, String title, String description, String image, int length, Date releaseDate, int seasons, int country) {
        this.mediaId = mediaId;
        this.type = type;
        this.title = title;
        this.description = description;
        this.image = image;
        this.length = length;
        this.releaseDate = releaseDate;
        this.seasons = seasons;
        this.country = country;
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

    public int getCountry() {
        return country;
    }
}
