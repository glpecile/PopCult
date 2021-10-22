package ar.edu.itba.paw.models.comment;

import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mediacomment")
public class MediaComment extends Comment {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "mediaid")
    private Media media;

    /* default */ MediaComment() {
        //Just for hibernate
    }

    public MediaComment(Integer commentId, User user, String commentBody, Date creationDate, Media media) {
        super(commentId, user, commentBody, creationDate);
        this.media = media;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
