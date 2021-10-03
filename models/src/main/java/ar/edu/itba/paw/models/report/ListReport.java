package ar.edu.itba.paw.models.report;

import java.util.Date;

public class ListReport extends Report {
    private final int mediaListId;
    private final int userId;
    private final String listName;
    private final String description;

    public ListReport(int reportId, int reporteeId, String report, Date date, int mediaListId, int userId, String listName, String description) {
        super(reportId, reporteeId, report, date);
        this.mediaListId = mediaListId;
        this.userId = userId;
        this.listName = listName;
        this.description = description;
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
}
