package ar.edu.itba.paw.models.comment;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "listcomment")
public class ListComment extends Comment {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "listid")
    private MediaList mediaList;

    /* default */ ListComment() {
        //Just for hibernate
    }

    public ListComment(Integer commentId, User user, String commentBody, Date creationDate, MediaList mediaList) {
        super(commentId, user, commentBody, creationDate);
        this.mediaList = mediaList;
    }
}
