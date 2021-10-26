package ar.edu.itba.paw.models.comment;

import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commentid")
    protected Integer commentId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    protected User user;

    @Column(name = "description", length = 1000, nullable = false)
    private String commentBody;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date creationDate;

    /* default */ Comment() {
        //Just for hibernate
    }

    public Comment(Integer commentId, User user, String commentBody, Date creationDate) {
        this.commentId = commentId;
        this.user = user;
        this.commentBody = commentBody;
        this.creationDate = creationDate;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
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
