package ar.edu.itba.paw.models.lists;

import java.util.Date;

public class MediaList {
    private final int mediaListId;
    private final int userId;
    private final String listName;
    private final String description;
    private final Date creationDate;
    private final boolean visible;
    private final boolean collaborative;


    public MediaList(int mediaListId, int userId, String listName, String description, Date creationDate, boolean visible, boolean collaborative) {
        this.mediaListId = mediaListId;
        this.userId = userId;
        this.listName = listName;
        this.description = description;
        this.creationDate = creationDate;
        this.visible = visible;
        this.collaborative = collaborative;
    }

    public int getUserId() {
        return userId;
    }

    public String getListName() {
        return listName;
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

    public boolean isVisible() {
        return visible;
    }

    public boolean isCollaborative() {
        return collaborative;
    }

}
