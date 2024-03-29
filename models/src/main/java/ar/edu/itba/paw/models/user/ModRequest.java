package ar.edu.itba.paw.models.user;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "modrequests")
public class ModRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "modrequests_requestid_seq")
    @SequenceGenerator(sequenceName = "modrequests_requestid_seq", name = "modrequests_requestid_seq", allocationSize = 1)
    private Integer requestId;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private User user;

    @Column(nullable = false)
    private LocalDateTime date;

    /* default */ ModRequest() {
        //Just for hibernate, we love you!
    }

    public ModRequest(Integer requestId, User user, LocalDateTime date) {
        this.requestId = requestId;
        this.user = user;
        this.date = date;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
