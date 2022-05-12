package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ListDto {

    private int id;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private boolean visibility;
    private boolean collaborative;
    private String owner;
    private int likes;
    private int forks;

    private String url;

    private String ownerUrl;
    private String mediaUrl;
    private String commentsUrl;
    private String collaboratorsUrl;
    private String forksUrl;
    private String requestsUrl; //Only for POST a request
    private String reportsUrl; //Only for POST a report

    private String favoriteUrl;
    private String isFavorite; //If user is logged returns true or false if this list is favorite

    private String forkedFrom;
    private String forkedFromUrl;

    public static ListDto fromList(UriInfo url, MediaList mediaList, User currentUser) {
        ListDto listDto = new ListDto();
        listDto.id = mediaList.getMediaListId();
        listDto.name = mediaList.getListName();
        listDto.description = mediaList.getDescription();
        listDto.creationDate = mediaList.getCreationDate();
        listDto.visibility = mediaList.getVisible();
        listDto.collaborative = mediaList.getCollaborative();
        listDto.owner = mediaList.getUser().getUsername();
        listDto.likes = mediaList.getLikes();
        listDto.forks = mediaList.getForks();

        listDto.url = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaList.getMediaListId())).build().toString();
        listDto.ownerUrl = url.getBaseUriBuilder().path("users").path(mediaList.getUser().getUsername()).build().toString();
        listDto.mediaUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaList.getMediaListId())).path("media").build().toString();
        listDto.commentsUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaList.getMediaListId())).path("comments").build().toString();
        listDto.collaboratorsUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaList.getMediaListId())).path("collaborators").build().toString();
        listDto.forksUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaList.getMediaListId())).path("forks").build().toString();
        listDto.requestsUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaList.getMediaListId())).path("requests").build().toString();
        listDto.reportsUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaList.getMediaListId())).path("reports").build().toString();

        if (currentUser != null) {
            //TODO SET isFavorite
            listDto.favoriteUrl = url.getBaseUriBuilder().path("users").path(currentUser.getUsername()).path("favorite-lists").path(String.valueOf(mediaList.getMediaListId())).build().toString();
        }

        if (mediaList.getForkedFrom() != null) {
            listDto.forkedFrom = mediaList.getForkedFrom().getListName();
            listDto.forkedFromUrl = url.getBaseUriBuilder().path("lists").path(String.valueOf(mediaList.getForkedFrom().getMediaListId())).build().toString();//TODO
        }

        return listDto;
    }

    public static List<ListDto> fromListList(UriInfo uriInfo, List<MediaList> listList, User currentUser) {
        return listList.stream().map(l -> ListDto.fromList(uriInfo, l, currentUser)).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOwnerUrl() {
        return ownerUrl;
    }

    public void setOwnerUrl(String ownerUrl) {
        this.ownerUrl = ownerUrl;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public String getCollaboratorsUrl() {
        return collaboratorsUrl;
    }

    public void setCollaboratorsUrl(String collaboratorsUrl) {
        this.collaboratorsUrl = collaboratorsUrl;
    }

    public String getForksUrl() {
        return forksUrl;
    }

    public void setForksUrl(String forksUrl) {
        this.forksUrl = forksUrl;
    }

    public String getRequestsUrl() {
        return requestsUrl;
    }

    public void setRequestsUrl(String requestsUrl) {
        this.requestsUrl = requestsUrl;
    }

    public String getReportsUrl() {
        return reportsUrl;
    }

    public void setReportsUrl(String reportsUrl) {
        this.reportsUrl = reportsUrl;
    }

    public String getFavoriteUrl() {
        return favoriteUrl;
    }

    public void setFavoriteUrl(String favoriteUrl) {
        this.favoriteUrl = favoriteUrl;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getForkedFrom() {
        return forkedFrom;
    }

    public void setForkedFrom(String forkedFrom) {
        this.forkedFrom = forkedFrom;
    }

    public String getForkedFromUrl() {
        return forkedFromUrl;
    }

    public void setForkedFromUrl(String forkedFromUrl) {
        this.forkedFromUrl = forkedFromUrl;
    }
}
