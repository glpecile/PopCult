package ar.edu.itba.paw.models.search;

public enum SortType {
    //Pueden agregarse otro tipos de criterios de sort como cantidad de favoritos, forks en las listas, vistas
    DATE("releasedate", "creationdate"),
    TITLE("title", "name");

    public final String nameMedia;
    public final String nameMediaList;

    SortType(String nameMedia, String nameMediaList) {
        this.nameMedia = nameMedia;
        this.nameMediaList = nameMediaList;
    }

}
