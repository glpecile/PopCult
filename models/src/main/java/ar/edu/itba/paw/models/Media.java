package ar.edu.itba.paw.models;

import java.util.Date;


public class Media {
    private String title;
    private String description;
    private String imageURL;
    private Integer length;
    private Date releaseDate;
    private Integer votesQty;
    private Integer votesAvg;

    public Media(String title, String description, String imageURL, Integer length, Date releaseDate, Integer votesQty, Integer votesAvg) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.length = length;
        this.releaseDate = releaseDate;
        this.votesQty = votesQty;
        this.votesAvg = votesAvg;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Integer getLength() {
        return length;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Integer getVotesQty() {
        return votesQty;
    }

    public Integer getVotesAvg() {
        return votesAvg;
    }
}
