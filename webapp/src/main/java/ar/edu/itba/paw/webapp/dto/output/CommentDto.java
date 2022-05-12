package ar.edu.itba.paw.webapp.dto.output;


import java.time.LocalDateTime;

public abstract class CommentDto {

    private Integer id;
    private String commentBody;
    private LocalDateTime creationDate;
    private String user;

    private String userUrl;
    private String userImageUrl;

//    protected static void fillFromMediaComment(MediaCommentDto mediaCommentDto, UriInfo url, MediaComment mediaComment) {
//        mediaCommentDto.setId(mediaComment.getCommentId());
//        mediaCommentDto.setCommentBody(mediaComment.getCommentBody());
//        mediaCommentDto.setCreationDate(mediaComment.getCreationDate());
//        mediaCommentDto.setUser(mediaComment.getUser().getUsername());
//        mediaCommentDto.setUserUrl(url.getBaseUriBuilder().path("users").path(String.valueOf(mediaComment.getUser().getUsername())).build().toString());
//    } //TODO DELETE

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }
}
