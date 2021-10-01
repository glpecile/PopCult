package ar.edu.itba.paw.models.image;

public class Image {
    private final int imageId;
    private final byte[] imageBlob;
    private final long imageContentLength;
    private final String imageContentType;

    public Image(int imageId, byte[] imageBlob, long imageContentLength, String imageContentType) {
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

    public long getImageContentLength() {
        return imageContentLength;
    }

    public String getImageContentType() {
        return imageContentType;
    }
}
