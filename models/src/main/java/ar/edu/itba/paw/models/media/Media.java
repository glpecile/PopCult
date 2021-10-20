package ar.edu.itba.paw.models.media;

import ar.edu.itba.paw.models.staff.Studio;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "media_mediaid_seq")
    @SequenceGenerator(sequenceName = "media_mediaid_seq", name="media_mediaid_seq", allocationSize = 1)
    private Integer mediaId;//TODO cambiar a Long, hay que hacer un refactor a todo.

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private MediaType type;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100)
    private String description;

    @Column(length = 100)
    private String image;

    @Column(length = 100)
    private Integer length;

    @Column(nullable = false)
    private Date releaseDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Country country;

    @ManyToMany
    @JoinTable(name = "mediastudio",
            joinColumns = {@JoinColumn(name = "mediaid")},
            inverseJoinColumns = {@JoinColumn(name = "studioid")}
    )
    private List<Studio> studios;

    public Media(){

    }

    public Media(final Integer mediaId, final MediaType type, final String title, final String description, final String image, final Integer length, final Date releaseDate, final Country country) {
        this.mediaId = mediaId;
        this.type = type;
        this.title = title;
        this.description = description;
        this.image = image;
        this.length = length;
        this.releaseDate = releaseDate;
        this.country = country;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public MediaType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Integer getLength() {
        return length;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseYear() {
        return String.valueOf(releaseDate).substring(0, 4);
    }

    public Country getCountry() {
        return country;
    }

    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return mediaId == media.mediaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaId);
    }
}
