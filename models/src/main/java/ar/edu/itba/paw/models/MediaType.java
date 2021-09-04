package ar.edu.itba.paw.models;

public enum MediaType {

    MOVIE("Movie"),
    SERIE("Serie");

    private final String type;

    MediaType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
