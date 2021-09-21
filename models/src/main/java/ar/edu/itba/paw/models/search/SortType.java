package ar.edu.itba.paw.models.search;

import java.util.ArrayList;
import java.util.List;

public enum SortType {
    //Pueden agregarse otro tipos de criterios de sort como cantidad de favoritos, forks en las listas, vistas
    DATE("date","releasedate", "creationdate", 6, 5),
    TITLE("title","title", "name", 3, 3);

    public final String name;
    public final String nameMedia;
    public final String nameMediaList;
    public final int colNumberMedia;
    public final int colNumberMediaList;

    SortType(String name, String nameMedia, String nameMediaList, int colNumberMedia, int colNumberMediaList) {
        this.name = name;
        this.nameMedia = nameMedia;
        this.nameMediaList = nameMediaList;
        this.colNumberMedia = colNumberMedia;
        this.colNumberMediaList = colNumberMediaList;
    }

    public String getName() {
        return name;
    }
}
