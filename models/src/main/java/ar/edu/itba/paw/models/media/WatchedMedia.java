package ar.edu.itba.paw.models.media;

import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Entity
@Table(name = "towatchmedia")
public class WatchedMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "towatchmedia_watchedmediaid_seq")
    @SequenceGenerator(sequenceName = "towatchmedia_watchedmediaid_seq", name = "towatchmedia_watchedmediaid_seq", allocationSize = 1)
    private Integer watchedMediaId;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mediaId")
    private Media media;

    @Column
    private LocalDateTime watchDate;

    /*default*/ WatchedMedia() {
        //hehe
    }

    public WatchedMedia(User user, Media media, LocalDateTime watchDate) {
        this.watchedMediaId = null;
        this.user = user;
        this.media = media;
        this.watchDate = watchDate;
    }

    public int getWatchedMediaId() {
        return watchedMediaId;
    }

    public void setWatchedMediaId(int mediaId) {
        this.watchedMediaId = mediaId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getWatchDate() {
        return watchDate.toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public void setWatchDate(LocalDateTime watchDate) {
        this.watchDate = watchDate;
    }

    public LocalDateTime getWatchDateTime() {
        return watchDate;
    }
}
