package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.comment.ListComment;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.stream.Collectors;

// Remove type attribute added automatically by jersey when extending class
@XmlType(name="")
public class ListCommentDto extends CommentDto {

    private String listTitle;

    private String url;

    private String commentListUrl;
    private String reportsUrl;

    public static ListCommentDto fromListComment(UriInfo url, ListComment listComment) {
        ListCommentDto listCommentDto = new ListCommentDto();
        listCommentDto.setId(listComment.getCommentId());
        listCommentDto.setCommentBody(listComment.getCommentBody());
        listCommentDto.setCreationDate(listComment.getCreationDate());
        listCommentDto.setUser(listComment.getUser().getUsername());
        listCommentDto.setUserUrl(url.getBaseUriBuilder().path("users").path(listComment.getUser().getUsername()).build().toString());
        listCommentDto.setUserImageUrl(url.getBaseUriBuilder().path("users").path(listComment.getUser().getUsername()).path("image").build().toString());
        listCommentDto.listTitle = listComment.getMediaList().getListName();

        listCommentDto.url = url.getBaseUriBuilder().path("lists-comments").path(String.valueOf(listComment.getCommentId())).build().toString();
        listCommentDto.commentListUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(listComment.getMediaList().getMediaListId())).build().toString();
        listCommentDto.reportsUrl = url.getBaseUriBuilder().path("lists-comments").path(String.valueOf(listComment.getCommentId())).path("reports").build().toString();
        return listCommentDto;
    }

    public static List<ListCommentDto> fromListCommentList(UriInfo url, List<ListComment> listCommentList) {
        return listCommentList.stream().map(c -> ListCommentDto.fromListComment(url, c)).collect(Collectors.toList());
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

    public String getReportsUrl() {
        return reportsUrl;
    }

    public void setReportsUrl(String reportsUrl) {
        this.reportsUrl = reportsUrl;
    }
}
