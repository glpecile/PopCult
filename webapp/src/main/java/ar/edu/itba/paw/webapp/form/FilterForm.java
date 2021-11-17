package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.search.SortType;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class FilterForm {

    @Pattern(regexp = "[^/><%]+")
    private String term;

    private List<String> genres;

    private List<String> mediaTypes;

    @Pattern(regexp = "TITLE|DATE")
    private String sortType;

    @Size(max = 4)
    @Pattern(regexp = "ALL|19[0-9]0|20[0-2]0")
    private String decade;

    public LocalDateTime getStartYear() {
        if(decade == null || decade.compareTo("ALL") == 0)
            return null;
        return LocalDateTime.of(Integer.parseInt(decade),1,1,0,0);
    }

    public String getDecade() {
        return decade;
    }

    public LocalDateTime getLastYear(){
        if(decade == null || decade.compareTo("ALL") == 0)
            return null;
        return LocalDateTime.of(Integer.parseInt(decade) + 9,12,31,0,0);
    }

    public void setDecade(String decade) {
        this.decade = decade;
    }

    public String getSortType() {
        if(sortType == null)
            return SortType.DATE.getName();
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public List<String> getGenres() {
        if(genres == null) {
            return Collections.emptyList();
        }
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getMediaTypes() {
        if(mediaTypes == null) {
            return Collections.emptyList();
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
