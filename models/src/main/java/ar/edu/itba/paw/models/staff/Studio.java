package ar.edu.itba.paw.models.staff;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "studio")
public class Studio {
    public static final String DEFAULT_IMAGE = "https://cdn.discordapp.com/attachments/851847371851956334/884854181586944030/clipart85790.png";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studio_studioid_seq")
    @SequenceGenerator(sequenceName = "studio_studioid_seq", name = "studio_studioid_seq", allocationSize = 1)
    private int studioId;

    @Column(length = 100, nullable = false) //preguntar si hacerlo unique, creeria que no.
    private String name;

    @Column(length = 100, nullable = false) //chequear el length
    private String image;


    public Studio() {

    }

    public Studio(int studioId, String name, String image) {
        this.studioId = studioId;
        this.name = name;
        this.image = image.equals("") ? DEFAULT_IMAGE : image;
    }

    public void setStudioId(int studioId) {
        this.studioId = studioId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStudioId() {
        return studioId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image.equals("") ? DEFAULT_IMAGE : image;
    }
}
