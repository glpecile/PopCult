package ar.edu.itba.paw.models.comment;

import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
public abstract class Comment {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "description", length = 1000, nullable = false)
    private String commentBody;

    @Column(name = "date", nullable = false)
    private LocalDateTime creationDate;

    /* default */ Comment() {
        //Just for hibernate
    }

    public Comment(User user, String commentBody, LocalDateTime creationDate) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
