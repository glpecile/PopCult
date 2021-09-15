package ar.edu.itba.paw.models.lists;

import java.util.Date;

public class MediaList {
    private final int mediaListId;
    private final int userId;
    private final String name;
    private final String description;
    private final Date creationDate;
    private final boolean visible;
    private final boolean collaborative;
    private boolean isFavorite;


    public MediaList(int mediaListId, int userId, String name, String description, Date creationDate, boolean visible, boolean collaborative) {
        this.mediaListId = mediaListId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.visible = visible;
        this.collaborative = collaborative;
        this.isFavorite = false;
    }

    public int getUserId() {
        return userId;
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

    public boolean isVisible() {
        return visible;
    }

    public boolean isCollaborative() {
        return collaborative;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
