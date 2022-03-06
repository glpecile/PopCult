package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.comment.MediaComment;

import javax.ws.rs.core.UriInfo;

public class MediaCommentDto extends CommentDto{
    private String mediaTitle;

    private String commentMediaUrl;
    private String commentUrl;

    public static MediaCommentDto fromMediaCommentDto(UriInfo url, MediaComment mediaComment){
        MediaCommentDto mediaCommentDto = new MediaCommentDto();
        CommentDto.fillFromMediaComment(mediaCommentDto,url,mediaComment);
        mediaCommentDto.mediaTitle = mediaComment.getMedia().getTitle();
        mediaCommentDto.commentUrl = url.getBaseUriBuilder().path("media-comments").path(String.valueOf(mediaComment.getCommentId())).build().toString();
        mediaCommentDto.commentMediaUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(mediaComment.getMedia().getMediaId())).build().toString();

        return mediaCommentDto;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getCommentMediaUrl() {
        return commentMediaUrl;
    }

    public void setCommentMediaUrl(String commentMediaUrl) {
        this.commentMediaUrl = commentMediaUrl;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }
}
