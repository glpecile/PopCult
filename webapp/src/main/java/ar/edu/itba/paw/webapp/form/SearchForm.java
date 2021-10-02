package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class SearchForm {
    @Size(min = 1)
    @Pattern(regexp = "[^/><%]+")
    private String term;
    private List<String> genres;
    private List<String> mediaTypes;
    @Pattern(regexp = "TITLE|DATE")
    private String sortType;

    public List<Integer> getDecades() {
        if(decades == null)
            return new ArrayList<>();
        return decades;
    }

    public void setDecades(List<Integer> decades) {
        this.decades = decades;
    }

    private List<Integer> decades;

    public String getSortType() {
        if(sortType == null)
            return "title";
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public List<String> getGenres() {
        if(genres == null) {
            return new ArrayList<>();
        }
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getMediaTypes() {
        if(mediaTypes == null) {
            return new ArrayList<>();
        }
        return mediaTypes;
    }

    public void setMediaTypes(List<String> mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
