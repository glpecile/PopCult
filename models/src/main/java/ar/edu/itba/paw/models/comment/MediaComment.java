package ar.edu.itba.paw.models.comment;

import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mediacomment")
public class MediaComment extends Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mediacomment_commentid_seq")
    @SequenceGenerator(sequenceName = "mediacomment_commentid_seq", name = "mediacomment_commentid_seq", allocationSize = 1)
    @Column(name = "commentid")
    protected Integer commentId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "mediaid")
    private Media media;

    /* default */ MediaComment() {
        //Just for hibernate
    }

    public MediaComment(Integer commentId, User user, String commentBody, Date creationDate, Media media) {
        super(user, commentBody, creationDate);
        this.commentId = commentId;
        this.media = media;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
