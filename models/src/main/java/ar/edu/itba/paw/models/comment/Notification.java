package ar.edu.itba.paw.models.comment;

import javax.persistence.*;

@Entity
@Table(name = "commentnotifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentnotifications_notificationid_seq")
    @SequenceGenerator(sequenceName = "commentnotifications_notificationid_seq", name = "commentnotifications_notificationid_seq", allocationSize =  1)
    @Column(name = "notificationid")
    private Integer notificationId;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "commentid")
    private ListComment listComment;

    @Column(name = "opened", nullable = false)
    private boolean opened = false;

    /* default */ Notification() {
        //Just for hibernate
    }

    public Notification(Integer notificationId, ListComment listComment) {
        this.notificationId = notificationId;
        this.listComment = listComment;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public ListComment getListComment() {
        return listComment;
    }

    public void setListComment(ListComment listComment) {
        this.listComment = listComment;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
