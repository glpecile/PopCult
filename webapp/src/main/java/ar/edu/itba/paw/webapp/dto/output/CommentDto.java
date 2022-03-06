package ar.edu.itba.paw.webapp.dto.output;


import ar.edu.itba.paw.models.comment.MediaComment;


import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;

public abstract class CommentDto {
    private Integer id;
    private String commentBody;
    private LocalDate creationDate;
    private String user;

    private String userUrl;

    protected static void fillFromMediaComment(MediaCommentDto mediaCommentDto, UriInfo url, MediaComment mediaComment) {
        mediaCommentDto.setId(mediaComment.getCommentId());
        mediaCommentDto.setCommentBody(mediaComment.getCommentBody());
        mediaCommentDto.setCreationDate(mediaComment.getCreationDate().toLocalDate());
        mediaCommentDto.setUser(mediaComment.getUser().getUsername());
        mediaCommentDto.setUserUrl(url.getBaseUriBuilder().path("users").path(String.valueOf(mediaComment.getUser().getUserId())).build().toString());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }
}
