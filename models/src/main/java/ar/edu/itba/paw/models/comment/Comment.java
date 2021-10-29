package ar.edu.itba.paw.models.comment;

import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class Comment {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "description", length = 1000, nullable = false)
    private String commentBody;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date creationDate;

    /* default */ Comment() {
        //Just for hibernate
    }

    public Comment(User user, String commentBody, Date creationDate) {
        this.user = user;
        this.commentBody = commentBody;
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
