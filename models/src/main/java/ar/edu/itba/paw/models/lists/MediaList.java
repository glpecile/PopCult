package ar.edu.itba.paw.models.lists;

import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "medialist")
public class MediaList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medialist_listid_seq")
    @SequenceGenerator(sequenceName = "medialist_listid_seq", name = "medialist_listid_seq", allocationSize = 1)
    private Integer mediaListId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid")
    private User user;

    @Column(nullable = false, length = 100)
    private String listName;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column
    private Date creationDate;

    @Column(name = "visibility")
    private Boolean visible;

    @Column
    private Boolean collaborative;

    /* default */ MediaList() {
        //Just for Hibernate, we <3 u!
    }

    public MediaList(User user, String listName, String description, boolean visible, boolean collaborative) {
        this.mediaListId = null;
        this.user = user;
        this.listName = listName;
        this.description = description;
        this.creationDate = new Date();
        this.visible = visible;
        this.collaborative = collaborative;
    }

    public MediaList(Integer mediaListId, User user, String listName, String description, Date creationDate, boolean visible, boolean collaborative) {
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaList mediaList = (MediaList) o;
        return mediaListId == mediaList.mediaListId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaListId);
    }
}
