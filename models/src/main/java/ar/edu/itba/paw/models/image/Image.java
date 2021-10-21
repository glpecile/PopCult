package ar.edu.itba.paw.models.image;

public class Image {
    private final int imageId;
    private final byte[] imageBlob;

    public Image(int imageId, byte[] imageBlob) {
        this.imageId = imageId;
        this.imageBlob = imageBlob;
    }

    public int getImageId() {
        return imageId;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }
}
