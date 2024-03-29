package ar.edu.itba.paw.models.lists;

import ar.edu.itba.paw.models.user.User;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "medialist")
public class MediaList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medialist_medialistid_seq")
    @SequenceGenerator(sequenceName = "medialist_medialistid_seq", name = "medialist_medialistid_seq", allocationSize = 1)
    private Integer mediaListId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userid")
    private User user;

    @Column(nullable = false, length = 100)
    private String listName;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column
    private LocalDateTime creationDate;

    @Column(name = "visibility")
    private Boolean visible;

    @Column
    private Boolean collaborative;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "forkedlists",
            joinColumns = {@JoinColumn(name = "forkedlistid")},
            inverseJoinColumns = {@JoinColumn(name = "originalistid")}
    )
    private MediaList forkedFrom;

    @Formula("(SELECT COUNT(*) FROM favoritelists AS f WHERE f.medialistid = medialistid)")
    private int likes;

    @Formula("(SELECT COUNT(*) FROM forkedlists AS f WHERE f.originalistid = medialistid)")
    private int forks;

    /* default */ MediaList() {
        //Just for Hibernate, we <3 u!
    }

    public MediaList(User user, String listName, String description, boolean visible, boolean collaborative) {
        this.mediaListId = null;
        this.user = user;
        this.listName = listName;
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.visible = visible;
        this.collaborative = collaborative;
    }

    public MediaList(Integer mediaListId, User user, String listName, String description, LocalDateTime creationDate, boolean visible, boolean collaborative) {
        this.mediaListId = mediaListId;
        this.user = user;
        this.listName = listName;
        this.description = description;
        this.creationDate = creationDate;
        this.visible = visible;
        this.collaborative = collaborative;
    }

    public Integer getMediaListId() {
        return mediaListId;
    }

    public void setMediaListId(Integer mediaListId) {
        this.mediaListId = mediaListId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
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

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getCollaborative() {
        return collaborative;
    }

    public void setCollaborative(Boolean collaborative) {
        this.collaborative = collaborative;
    }

    public MediaList getForkedFrom() {
        return forkedFrom;
    }

    public void setForkedFrom(MediaList forkedFrom) {
        this.forkedFrom = forkedFrom;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaList mediaList = (MediaList) o;
        return mediaListId.equals(mediaList.mediaListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaListId);
    }
}
