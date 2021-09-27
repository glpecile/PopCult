package ar.edu.itba.paw.models.report;

import java.util.Date;

public class ListReport extends Report {
    private final int mediaListId;
    private final int userId;
    private final String listName;
    private final String description;
    private final Date creationDate;

    public ListReport(String report, Date date, int mediaListId, int userId, String listName, String description, Date creationDate) {
        super(report, date);
        this.mediaListId = mediaListId;
        this.userId = userId;
        this.listName = listName;
        this.description = description;
        this.creationDate = creationDate;
    }

    public int getMediaListId() {
        return mediaListId;
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

    public Date getCreationDate() {
        return creationDate;
    }
}
