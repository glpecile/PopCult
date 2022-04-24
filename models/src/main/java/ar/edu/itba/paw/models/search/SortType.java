package ar.edu.itba.paw.models.search;

public enum SortType {
    //Pueden agregarse otro tipos de criterios de sort como cantidad de favoritos, forks en las listas, vistas
    DATE("Date", "releasedate", "creationdate"),
    TITLE("Title", "title", "listname"),
    POPULARITY("Popularity", "COUNT(DISTINCT favoritemedia.userid) DESC", "COUNT(DISTINCT favoritelists.userid) DESC") ;

    private final String name;
    private final String nameMedia;
    private final String nameMediaList;


    SortType(String name, String nameMedia, String nameMediaList) {
        this.name = name;
        this.nameMedia = nameMedia;
        this.nameMediaList = nameMediaList;
    }

    public String getName() {
        return name;
    }

    public String getNameMedia() {
        return nameMedia;
    }

    public String getNameMediaList() {
        return nameMediaList;
    }
}
