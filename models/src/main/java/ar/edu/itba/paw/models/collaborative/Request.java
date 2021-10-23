package ar.edu.itba.paw.models.collaborative;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;

@Entity
@Table(name = "collaborative")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collaborative_collabId_seq")
    @SequenceGenerator(sequenceName = "collaborative_collabId_seq", name="collaborative_collabId_seq", allocationSize = 1)
    private Integer collabId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "collaboratorId")
    private User collaborator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "listId")
    private MediaList mediaList;

    @Column
    private boolean accepted;

    public Request(int collabId, User user, MediaList mediaList, boolean accepted) {
        this.collabId = collabId;
        this.collaborator = user;
        this.mediaList = mediaList;
        this.accepted = accepted;
    }

    public Request() {
        //hibernate!!!!!
    }

    public Request(User collaborator, MediaList mediaList) {
        this.collabId = null;
        this.collaborator = collaborator;
        this.mediaList = mediaList;
        this.accepted = false;
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
}
