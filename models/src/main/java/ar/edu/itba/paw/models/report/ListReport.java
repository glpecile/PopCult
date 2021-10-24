package ar.edu.itba.paw.models.report;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "listreport")
public class ListReport extends Report {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "listid")
    private MediaList mediaList;

    /* default */ ListReport() {

    }

    public ListReport(Integer reportId, User reportee, String report, Date date, MediaList mediaList) {
        super(reportId, reportee, report, date);
        this.mediaList = mediaList;
    }

    public MediaList getMediaList() {
        return mediaList;
    }

    public void setMediaList(MediaList mediaList) {
        this.mediaList = mediaList;
    }
}
