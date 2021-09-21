package ar.edu.itba.paw.models.image;

public class Image {
    private final int imageId;
    private final byte[] imageBlob;
    private final Integer imageContentLength;
    private final String imageContentType;

    public Image(int imageId, byte[] imageBlob, Integer imageContentLength, String imageContentType) {
        this.imageId = imageId;
        this.imageBlob = imageBlob;
        this.imageContentLength = imageContentLength;
        this.imageContentType = imageContentType;
    }

    public int getImageId() {
        return imageId;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public Integer getImageContentLength() {
        return imageContentLength;
    }

    public String getImageContentType() {
        return imageContentType;
    }
}
