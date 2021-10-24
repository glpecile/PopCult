package ar.edu.itba.paw.models.report;

import ar.edu.itba.paw.models.comment.ListComment;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "listcommentreport")
public class ListCommentReport extends Report {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "commentid")
    private ListComment comment;

//    private int mediaListId; Esto no hace falta

//    private int userId; Esto no se por que esta xd

//    private String commentBody;

    /* default */ ListCommentReport() {

    }

    public ListCommentReport(Integer reportId, User reportee, String report, Date date, ListComment comment) {
        super(reportId, reportee, report, date);
        this.comment = comment;
    }

    public ListComment getComment() {
        return comment;
    }

    public void setComment(ListComment comment) {
        this.comment = comment;
    }
}
