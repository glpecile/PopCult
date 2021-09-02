package ar.edu.itba.paw.models.media;

public enum Genre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    ANIMATION("Animation"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DOCUMENTARY("Documentary"),
    DRAMA("Drama"),
    FAMILY("Family"),
    FANTASY("Fantasy"),
    HISTORY("History"),
    HORROR("Horror"),
    MUSIC("Music"),
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    SCIENCEFICTION("Science Fiction"),
    THRILLER("Thriller"),
    TVMOVIE("TV Movie"),
    WAR("War"),
    WESTERN("Western");
    private final String genre;

    Genre(String genre) {
        this.genre = genre;
    }
    public String getGenre() {
        return genre;
    }
}