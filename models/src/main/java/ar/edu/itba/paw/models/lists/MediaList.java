package ar.edu.itba.paw.models.lists;

import java.util.Date;

public class MediaList {
    private int mediaListId;
    private String name;
    private String description;
    private String image;
    private Date creationDate;

    public MediaList(int mediaListId, String name, String description, String image, Date creationDate) {
        this.mediaListId = mediaListId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getMediaListId() {
        return mediaListId;
    }
}
