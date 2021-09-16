package ar.edu.itba.paw.models.search;

public enum SortType {
    //Pueden agregarse otro tipos de criterios de sort como cantidad de favoritos, forks en las listas, vistas
    DATE("releasedate", "creationdate", 6, 5),
    TITLE("title", "name", 3, 3);

    public final String nameMedia;
    public final String nameMediaList;
    public final int colNumberMedia;
    public final int colNumberMediaList;

    SortType(String nameMedia, String nameMediaList, int colNumberMedia, int colNumberMediaList) {
        this.nameMedia = nameMedia;
        this.nameMediaList = nameMediaList;
        this.colNumberMedia = colNumberMedia;
        this.colNumberMediaList = colNumberMediaList;
    }

}
