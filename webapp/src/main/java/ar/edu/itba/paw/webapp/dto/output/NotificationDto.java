package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.comment.Notification;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationDto {

    private String list;
    private String comment;
    private String listOwner;
    private String commentOwner;
    private boolean opened;

    private String url;

    private String listUrl;
    private String commentUrl;
    private String listOwnerUrl;
    private String commentOwnerUrl;

    public static NotificationDto fromNotification(UriInfo url, Notification notification) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.list = notification.getListComment().getMediaList().getListName();
        notificationDto.comment = notification.getListComment().getCommentBody();
        notificationDto.listOwner = notification.getListComment().getMediaList().getUser().getUsername();
        notificationDto.commentOwner = notification.getListComment().getUser().getUsername();
        notificationDto.opened = notification.isOpened();

        notificationDto.url = url.getBaseUriBuilder().path("notifications").path(String.valueOf(notification.getNotificationId())).build().toString();
        notificationDto.listUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(notification.getListComment().getMediaList().getMediaListId())).build().toString();
        notificationDto.commentUrl = url.getBaseUriBuilder().path("list-comments").path(String.valueOf(notification.getListComment().getCommentId())).build().toString();
        notificationDto.listOwnerUrl = url.getBaseUriBuilder().path("users").path(notification.getListComment().getMediaList().getUser().getUsername()).build().toString();
        notificationDto.commentOwnerUrl = url.getBaseUriBuilder().path("users").path(notification.getListComment().getUser().getUsername()).build().toString();
        return notificationDto;
    }

    public static List<NotificationDto> fromNotificationList(UriInfo url, List<Notification> notificationList) {
        return notificationList.stream().map(n -> NotificationDto.fromNotification(url, n)).collect(Collectors.toList());
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getListOwner() {
        return listOwner;
    }

    public void setListOwner(String listOwner) {
        this.listOwner = listOwner;
    }

    public String getCommentOwner() {
        return commentOwner;
    }

    public void setCommentOwner(String commentOwner) {
        this.commentOwner = commentOwner;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getListUrl() {
        return listUrl;
    }

    public void setListUrl(String listUrl) {
        this.listUrl = listUrl;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }

    public String getListOwnerUrl() {
        return listOwnerUrl;
    }

    public void setListOwnerUrl(String listOwnerUrl) {
        this.listOwnerUrl = listOwnerUrl;
    }

    public String getCommentOwnerUrl() {
        return commentOwnerUrl;
    }

    public void setCommentOwnerUrl(String commentOwnerUrl) {
        this.commentOwnerUrl = commentOwnerUrl;
    }
}
