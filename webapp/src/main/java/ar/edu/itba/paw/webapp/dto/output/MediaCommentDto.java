package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.comment.MediaComment;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.stream.Collectors;

@XmlType(name="")
public class MediaCommentDto extends CommentDto {

    private String mediaTitle;

    private String url;

    private String commentMediaUrl;

    public static MediaCommentDto fromMediaComment(UriInfo url, MediaComment mediaComment) {
        MediaCommentDto mediaCommentDto = new MediaCommentDto();
        mediaCommentDto.setId(mediaComment.getCommentId());
        mediaCommentDto.setCommentBody(mediaComment.getCommentBody());
        mediaCommentDto.setCreationDate(mediaComment.getCreationDate());
        mediaCommentDto.setUser(mediaComment.getUser().getUsername());
        mediaCommentDto.setUserUrl(url.getBaseUriBuilder().path("users").path(mediaComment.getUser().getUsername()).build().toString());
        mediaCommentDto.mediaTitle = mediaComment.getMedia().getTitle();

        mediaCommentDto.url = url.getBaseUriBuilder().path("media-comments").path(String.valueOf(mediaComment.getCommentId())).build().toString();
        mediaCommentDto.commentMediaUrl = url.getBaseUriBuilder().path("media").path(String.valueOf(mediaComment.getMedia().getMediaId())).build().toString();
        return mediaCommentDto;
    }

    public static List<MediaCommentDto> fromMediaCommentList(UriInfo url, List<MediaComment> mediaComments){
        return mediaComments.stream().map(c -> MediaCommentDto.fromMediaComment(url, c)).collect(Collectors.toList());
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
