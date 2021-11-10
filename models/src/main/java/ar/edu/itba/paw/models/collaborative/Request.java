package ar.edu.itba.paw.models.collaborative;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "collaborative")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collaborative_collabid_seq")
    @SequenceGenerator(sequenceName = "collaborative_collabid_seq", name="collaborative_collabid_seq", allocationSize = 1)
    private Integer collabId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "collaboratorId")
    private User collaborator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "listId")
    private MediaList mediaList;

    @Column
    private boolean accepted;

    /*default*/ Request() {
        //hibernate!!!!!
    }

    public Request(User collaborator, MediaList mediaList) {
        this.collabId = null;
        this.collaborator = collaborator;
        this.mediaList = mediaList;
        this.accepted = false;
    }

    public Request(User collaborator, MediaList mediaList, boolean accepted) {
        this.collabId = null;
        this.collaborator = collaborator;
        this.mediaList = mediaList;
        this.accepted = accepted;
    }

    public Integer getCollabId() {
        return collabId;
    }

    public void setCollabId(Integer collabId) {
        this.collabId = collabId;
    }

    public User getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(User collaborator) {
        this.collaborator = collaborator;
    }

    public MediaList getMediaList() {
        return mediaList;
    }

    public void setMediaList(MediaList mediaList) {
        this.mediaList = mediaList;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(collaborator, request.collaborator) && Objects.equals(mediaList, request.mediaList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collaborator, mediaList);
    }
}
