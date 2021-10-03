package ar.edu.itba.paw.models.media;

public enum Genre {
    ALL("All"),
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
    KIDS("Kids"),
    MUSIC("Music"),
    MYSTERY("Mystery"),
    NEWS("News"),
    REALITY("Reality"),
    ROMANCE("Romance"),
    SCIENCEFICTION("Science Fiction"),
    SOAP("Soap"),
    TVMOVIE("TV Movie"),
    TALK("Talk"),
    THRILLER("Thriller"),
    WAR("War"),
    WESTERN("Western");

    private final String genre;

    Genre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public int getOrdinal(String name) {
        return Genre.valueOf(name).ordinal();
    }
}