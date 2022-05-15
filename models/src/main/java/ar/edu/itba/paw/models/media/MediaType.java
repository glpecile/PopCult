package ar.edu.itba.paw.models.media;

public enum MediaType {

    FILMS("Films"), SERIES("Series"), LIST("List");
    final private String type;

    MediaType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
