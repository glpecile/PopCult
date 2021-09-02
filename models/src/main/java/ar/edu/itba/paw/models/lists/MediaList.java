package ar.edu.itba.paw.models.lists;

public class MediaList {
    private int mediaListId;
    private String name;
    private String description;
    private String image;

    public MediaList(int mediaListId, String name, String description, String image) {
        this.mediaListId = mediaListId;
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
