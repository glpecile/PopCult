package ar.edu.itba.paw.models.image;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_imageid_seq")
    @SequenceGenerator(sequenceName = "image_imageid_seq", name = "image_imageid_seq", allocationSize = 1)
    private Integer imageId;

    //TODO chequear constraints
    @Column(name = "photoblob")
    private byte[] imageBlob;

    /* default */ Image() {
        //Just for Hibernate, we love you!
    }

    public Image(Integer imageId, byte[] imageBlob) {
        this.imageId = imageId;
        this.imageBlob = imageBlob;
    }

    public Integer getImageId() {
        return imageId;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }
}
