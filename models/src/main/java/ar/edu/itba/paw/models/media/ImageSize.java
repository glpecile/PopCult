package ar.edu.itba.paw.models.media;

public enum ImageSize {
    sm("w154"),
    md("w342"),
    lg("w500");

    final private String size;

    ImageSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
