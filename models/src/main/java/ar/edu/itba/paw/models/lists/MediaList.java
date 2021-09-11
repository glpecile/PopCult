package ar.edu.itba.paw.models.lists;

import java.util.Date;

public class MediaList {
    private final int mediaListId;
    private final String name;
    private final String description;
    private final Date creationDate;

    public MediaList(int mediaListId, String name, String description, Date creationDate) {
        this.mediaListId = mediaListId;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMediaListId() {
        return mediaListId;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
