package ar.edu.itba.paw.models.comment;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "listcomment")
public class ListComment extends Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listcomment_commentid_seq")
    @SequenceGenerator(sequenceName = "listcomment_commentid_seq", name = "listcomment_commentid_seq", allocationSize = 1)
    @Column(name = "commentid")
    private Integer commentId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "listid")
    private MediaList mediaList;

    /* default */ ListComment() {
        //Just for hibernate
    }

    public ListComment(Integer commentId, User user, String commentBody, LocalDateTime creationDate, MediaList mediaList) {
        super(user, commentBody, creationDate);
        this.commentId = commentId;
        this.mediaList = mediaList;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public MediaList getMediaList() {
        return mediaList;
    }

    public void setMediaList(MediaList mediaList) {
        this.mediaList = mediaList;
    }
}
