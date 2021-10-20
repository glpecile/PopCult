package ar.edu.itba.paw.models.lists;

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
    @Column(nullable = false, unique = true)
    private Integer userId;
    @Column(nullable = false, length = 1000)
    private String listName;
    @Column(nullable = false, length = 1000)
    private String description;
    @Column
    private Date creationDate;
    @Column
    private boolean visible;
    @Column
    private boolean collaborative;

    /* default */ MediaList() {
        //Just for Hibernate, we <3 u!
    }

    public MediaList(Integer userId, String listName, String description, boolean visible, boolean collaborative) {
        this.mediaListId = null;
        this.userId = userId;
        this.listName = listName;
        this.description = description;
        this.creationDate = new Date();
        this.visible = visible;
        this.collaborative = collaborative;
    }

    public MediaList(Integer mediaListId, Integer userId, String listName, String description, Date creationDate, boolean visible, boolean collaborative) {
        this.mediaListId = mediaListId;
        this.userId = userId;
        this.listName = listName;
        this.description = description;
        this.creationDate = creationDate;
        this.visible = visible;
        this.collaborative = collaborative;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getListName() {
        return listName;
    }

    public String getDescription() {
        return description;
    }

    public Integer getMediaListId() {
        return mediaListId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isCollaborative() {
        return collaborative;
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
