package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.comment.ListComment;

import javax.ws.rs.core.UriInfo;

public class ListCommentDto extends CommentDto {

    private String listTitle;

    private String url;

    private String commentListUrl;

    public static ListCommentDto fromListComment(UriInfo url, ListComment listComment) {
        ListCommentDto listCommentDto = new ListCommentDto();
        listCommentDto.setId(listComment.getCommentId());
        listCommentDto.setCommentBody(listComment.getCommentBody());
        listCommentDto.setCreationDate(listComment.getCreationDate());
        listCommentDto.setUser(listComment.getUser().getUsername());
        listCommentDto.setUserUrl(url.getBaseUriBuilder().path("users").path(listComment.getUser().getUsername()).build().toString());
        listCommentDto.listTitle = listComment.getMediaList().getListName();

        listCommentDto.url = url.getBaseUriBuilder().path("lists-comments").path(String.valueOf(listComment.getCommentId())).build().toString();
        listCommentDto.commentListUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(listComment.getMediaList().getMediaListId())).build().toString();
        return listCommentDto;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommentListUrl() {
        return commentListUrl;
    }

    public void setCommentListUrl(String commentListUrl) {
        this.commentListUrl = commentListUrl;
    }
}
