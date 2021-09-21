package ar.edu.itba.paw.models.image;

public class Image {
    private final int imageId;
    private final byte[] photoBlob;
    private final Integer imageContentLength;
    private final String imageContentType;

    public Image(int imageId, byte[] photoBlob, Integer imageContentLength, String imageContentType) {
        this.imageId = imageId;
        this.photoBlob = photoBlob;
        this.imageContentLength = imageContentLength;
        this.imageContentType = imageContentType;
    }

    public int getImageId() {
        return imageId;
    }

    public byte[] getPhotoBlob() {
        return photoBlob;
    }

    public Integer getImageContentLength() {
        return imageContentLength;
    }

    public String getImageContentType() {
        return imageContentType;
    }
}
